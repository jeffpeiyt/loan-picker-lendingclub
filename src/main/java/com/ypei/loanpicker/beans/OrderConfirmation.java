package com.ypei.loanpicker.beans;

import java.util.List;

import com.google.api.client.util.Key;

public class OrderConfirmation {

	@Key
	public Long loanId;
	@Key
	public Integer requestedAmount;
	@Key
	public Integer investedAmount;
	@Key
	public List<String> executionStatus;
	
	@Override
	public String toString() {
		return "OrderConfirmation [loanId=" + loanId + ", requestedAmount="
				+ requestedAmount + ", investedAmount=" + investedAmount
				+ ", executionStatus=" + executionStatus + "]";
	}
	
	
}
