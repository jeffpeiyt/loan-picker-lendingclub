package com.ypei.loanpicker.beans;

import com.google.api.client.util.Key;

public class Order {

	@Key
	public Long loanId;
	@Key
	public Integer requestedAmount;
	@Key
	public Integer portfolioId;
	
	public Order(Long loanId, Integer requestedAmount, Integer portfolioId) {
		super();
		this.loanId = loanId;
		this.requestedAmount = requestedAmount;
		this.portfolioId = portfolioId;
	}
	
	
	
	
}
