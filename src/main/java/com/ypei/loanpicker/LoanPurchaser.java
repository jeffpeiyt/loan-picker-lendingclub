package com.ypei.loanpicker;

import java.util.ArrayList;
import java.util.List;
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
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.ypei.loanpicker.beans.Loan;
import com.ypei.loanpicker.beans.Order;
import com.ypei.loanpicker.beans.OrderConfirmation;
import com.ypei.loanpicker.beans.Purchase;
import com.ypei.loanpicker.beans.PurchaseResponse;

public class LoanPurchaser {

	
	public static final HttpTransport HTTP_TRANSPORT = new ApacheHttpTransport();
	public static final JsonFactory JSON_FACTORY = new JacksonFactory();

	private static Logger logger = Logger.getLogger(LoanLoader.class);

	public static List<OrderConfirmation> purchaseLoans(List<Loan> loansPicked, Set<Long> ownedLoanIds) {

		List<Order> orders = new ArrayList<Order>();
		List<OrderConfirmation> ocs = new ArrayList<OrderConfirmation>();
		
		try {

			/**
			 * Pre validate: avoid invest into duplicate loans
			 */
			
			int toIndex=Integer.parseInt(Cfg.m.get(Cfg.T.PURCHASE_LIMIT.name()));
			//check boundary
			toIndex= toIndex>loansPicked.size() ? loansPicked.size()  : toIndex ;
			logger.info("PURCHASE_LIMIT: Only consider first " + toIndex + " picked loans to order.");
			for(Loan l: loansPicked.subList(0, toIndex)){
				
				if(ownedLoanIds.contains(l.id)){
					logger.info("DUPLCIATED_LOANS: Note with Loan id "
							+ l.id+" was already purchased!. Skip.");
				}else{
					Order order = new Order(l.id, 
							Cfg.REQUEST_AMOUNT_DEFAULT,
							new Integer(Cfg.m.get(Cfg.T.PORTFOLIO_ID.name()))
							);
					orders.add(order);
				}
				
			}
			
			logger.info("-----------------------------------------------");
			logger.info("FINAL_LOAN_PURCHASE_COUNT: " + orders.size());
			if(orders.isEmpty()){
				logger.info("NO_LOANS_TO_PURCHASE. RETURN");
				return ocs;
			}
			
			Boolean inDebug = new Boolean(Cfg.m.get(Cfg.T.IN_DEBUG.name()));
			if(inDebug){
				logger.info("DEBUG_MODE. NO_LOANS_TO_PURCHASE. RETURN");
				return ocs;
			}
			
			Integer investorId = new Integer(Cfg.m.get(Cfg.T.INVESTOR_ID.name()));
			Purchase purchase = new Purchase(investorId, orders);
			
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
			GenericUrl url = new GenericUrl(Cfg.LC_URL_ACCOUNT_PREFIX
					+ Cfg.m.get(Cfg.T.INVESTOR_ID.name()) + "/orders");
			logger.info("-----------------------------------------------");
			logger.info("SUBMIT_LC_LIST_ORDERS....");
			JsonHttpContent content = new JsonHttpContent(new JacksonFactory(), purchase);
			HttpRequest request = requestFactory.buildPostRequest(url, content);
			
			PurchaseResponse pr = request.execute().parseAs(PurchaseResponse.class);
			if (pr.orderConfirmations.isEmpty()) {
				System.out.println("No loans found.");
			} else {
				for (OrderConfirmation oc : pr.orderConfirmations) {
					logger.info("");
					logger.info("-----------------------------------------------");
					logger.info(oc.toString());
					ocs.add(oc);
					
				}
				logger.info("-----------------------------------------------");
				logger.info("ORDER_CONFIRMATION_COUNT: " + pr.orderConfirmations.size());
			}
		} catch (HttpResponseException e) {

			logger.error("If 401 Unauthorized: check application.conf: if input the correct token.", e);
		
		} catch (Throwable e) {

			logger.error("fail", e);

		}
		
		return ocs;

	}// end func
	
}
