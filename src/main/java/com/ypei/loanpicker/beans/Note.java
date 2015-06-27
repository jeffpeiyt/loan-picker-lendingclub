package com.ypei.loanpicker.beans;

import com.google.api.client.util.Key;

/**
 * 
 * @author ypei
 *
 */
public class Note {

	@Key
	public Long loanId;
	@Key
	public Long noteId;
	@Key
	public Integer orderId;
	@Key
	public Double interestRate;
	@Key
	public Integer loanLength;
	@Key
	public String loanStatus;
	@Key
	public String grade;
	@Key
	public String subGrade;
	@Key
	public Integer loanAmount;
	@Key
	public Integer noteAmount;
	@Key
	public Double paymentsReceived;
	@Key
	public String issueDate;
	@Key
	public String orderDate;
	@Key
	public String loanStatusDate;
	@Override
	public String toString() {
		return "Note [loanId=" + loanId + ", noteId=" + noteId + ", orderId="
				+ orderId + ", interestRate=" + interestRate + ", loanLength="
				+ loanLength + ", loanStatus=" + loanStatus + ", grade="
				+ grade + ", subGrade=" + subGrade + ", loanAmount="
				+ loanAmount + ", noteAmount=" + noteAmount
				+ ", paymentsReceived=" + paymentsReceived + ", issueDate="
				+ issueDate + ", orderDate=" + orderDate + ", loanStatusDate="
				+ loanStatusDate + "]";
	}
	
	
	
}
