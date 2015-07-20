package com.ypei.loanpicker;

import java.util.ArrayList;
import java.util.List;

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
import com.ypei.loanpicker.beans.Loan;
import com.ypei.loanpicker.beans.LoanListing;


/**
 * load loans
 * @author ypei
 *
 */
public class LoanLoader {

	public static final HttpTransport HTTP_TRANSPORT = new ApacheHttpTransport();
	public static final JsonFactory JSON_FACTORY = new JacksonFactory();

	private static Logger logger = Logger.getLogger(LoanLoader.class);

	public static List<Loan> loadLoans() {

		List<Loan> loansLoaded = new ArrayList<Loan>();
		
		try {

			HttpRequestFactory requestFactory = HTTP_TRANSPORT
					.createRequestFactory(new HttpRequestInitializer() {
						public void initialize(HttpRequest request) {
							request.setParser(new JsonObjectParser(JSON_FACTORY));
							
							HttpHeaders hh = new HttpHeaders();
							// must has name!
							hh.setAuthorization(Cfg.m.get(Cfg.T.AUTHORIZATION_TOKEN.name()));
							hh.setContentType(Cfg.CONTENT_TYPE);
							request.setHeaders(hh);
						}
					});
			GenericUrl url = new GenericUrl(
					Cfg.LC_URL_LOAN_LIST);
			logger.info("-----------------------------------------------");
			logger.info("REQEST_LC_LIST_RECENT_LOANS....");
			HttpRequest request = requestFactory.buildGetRequest(url);
			LoanListing ll = request.execute().parseAs(LoanListing.class);
			if (ll.loans.isEmpty()) {
				System.out.println("No loans found.");
			} else {
				logger.info("-----------------------------------------------");
				logger.info("RECENT_LOANS_FOUND: " + ll.loans.size());
				for (Loan l : ll.loans) {
					logger.debug("");
					logger.debug("-----------------------------------------------");
					logger.debug(l.toString());
					loansLoaded.add(l);
				}
			}
		} catch (HttpResponseException e) {

			logger.error("If 401 Unauthorized: check application.conf: if input the correct token.", e);
		
		} catch (Throwable e) {

			logger.error("fail", e);

		}
		
		return loansLoaded;

	}// end func

}// end class
