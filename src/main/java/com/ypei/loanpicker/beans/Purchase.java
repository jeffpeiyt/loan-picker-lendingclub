package com.ypei.loanpicker.beans;

import java.util.List;

import com.google.api.client.util.Key;

public class Purchase {
	@Key
	public Integer aid;
	@Key
	public List<Order> orders;
	
	public Purchase(Integer aid, List<Order> orders) {
		super();
		this.aid = aid;
		this.orders = orders;
	}
	
	
}
