package com.ypei.loanpicker.beans;
import java.util.List;

import com.google.api.client.util.Key;

public class LoanListing {

	@Key
	public String asOfDate;
	@Key
	public List<Loan> loans;
	
}
