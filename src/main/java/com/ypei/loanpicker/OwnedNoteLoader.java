package com.ypei.loanpicker;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.ypei.loanpicker.beans.Note;
import com.ypei.loanpicker.beans.NoteList;

public class OwnedNoteLoader {

	public static final HttpTransport HTTP_TRANSPORT = new ApacheHttpTransport();
	public static final JsonFactory JSON_FACTORY = new JacksonFactory();

	private static Logger logger = Logger.getLogger(OwnedNoteLoader.class);

	public static Set<Long> loadOwnedNotes() {

		Set<Long> ownedLoanIds = new HashSet<Long>();

		try {

			HttpRequestFactory requestFactory = HTTP_TRANSPORT
					.createRequestFactory(new HttpRequestInitializer() {
						public void initialize(HttpRequest request) {
							request.setParser(new JsonObjectParser(JSON_FACTORY));

							HttpHeaders hh = new HttpHeaders();
							// must has name!
							hh.setAuthorization(Cfg.m
									.get(Cfg.T.AUTHORIZATION_TOKEN.name()));
							hh.setContentType(Cfg.CONTENT_TYPE);
							request.setHeaders(hh);
						}
					});
			GenericUrl url = new GenericUrl(Cfg.LC_URL_ACCOUNT_PREFIX
					+ Cfg.m.get(Cfg.T.INVESTOR_ID.name()) + "/notes");
			logger.info("-----------------------------------------------");
			logger.info("REQEST_LC_OWNED_NOTES....");
			HttpRequest request = requestFactory.buildGetRequest(url);
			NoteList nl = request.execute().parseAs(NoteList.class);
			if (nl.myNotes.isEmpty()) {
				System.out.println("No notes found for you.");
			} else {
				logger.info("-----------------------------------------------");
				logger.info("OWNED_NOTES_FOUND: " + nl.myNotes.size());
				for (Note n : nl.myNotes) {
					logger.debug("");
					logger.debug("-----------------------------------------------");
					logger.debug(n.toString());
					ownedLoanIds.add(n.loanId);
				}
			}
		} catch (HttpResponseException e) {

			logger.error(
					"If 401 Unauthorized: check application.conf: if input the correct token.",
					e);

		} catch (Throwable e) {

			logger.error("fail", e);

		}

		return ownedLoanIds;

	}// end func

}
