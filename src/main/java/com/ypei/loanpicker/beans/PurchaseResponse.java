package com.ypei.loanpicker.beans;

import java.util.List;

import com.google.api.client.util.Key;

public class PurchaseResponse {
	@Key
	public Integer orderInstructId;
	@Key
	public List<OrderConfirmation> orderConfirmations;
	
	public PurchaseResponse(Integer orderInstructId,
			List<OrderConfirmation> orderConfirmations) {
		super();
		this.orderInstructId = orderInstructId;
		this.orderConfirmations = orderConfirmations;
	}

	public PurchaseResponse() {
		super();
	}
	
	
	
}
