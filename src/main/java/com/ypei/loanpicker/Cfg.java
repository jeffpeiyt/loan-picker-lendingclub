package com.ypei.loanpicker;

import java.util.LinkedHashMap;
import java.util.Map;

public class Cfg {

	public static enum T {
		AUTHORIZATION_TOKEN, INVESTOR_ID, PORTFOLIO_ID,
		PURCHASE_LIMIT, IN_DEBUG, RUN_AS_SCHEDULER, CRON_EXPRESSION
	}
	
	public static final String CONTENT_TYPE = "application/json";

	public static final Integer REQUEST_AMOUNT_DEFAULT=25;
	
	public static String LC_URL_ACCOUNT_PREFIX = "https://api.lendingclub.com/api/investor/v1/accounts/";
	public static String LC_URL_LOAN_LIST = "https://api.lendingclub.com/api/investor/v1/loans/listing";

	public static String CONFIG_FILE_PATH = "application.conf";

	//hashmap to store all the configs
	public static final Map<String, String> m = new LinkedHashMap<String, String>();
	
}
