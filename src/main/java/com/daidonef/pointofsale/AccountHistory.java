package com.daidonef.pointofsale;

public class AccountHistory {
	
	private int historyID;
	private int accountID;
	private double grandTotal;
	private String paymentType;
	private double cashPayment;
	private double change;
	private long creditCardNumber;
	private int securityCode;
	private int checkNumber;
	
	public AccountHistory() {
		
	}

	public int getHistoryID() {
		return historyID;
	}

	public int getAccountID() {
		return accountID;
	}

	public double getPrice() {
		return grandTotal;
	}

	public String getPaymentType() {
		return paymentType;
	}
	
	public double getCashPayment() {
		return cashPayment;
	}
	
	public double getChange() {
		return change;
	}
	
	public long getCreditCardNumber() {
		return creditCardNumber;
	}
	
	public int getSecurityCode() {
		return securityCode;
	}
	
	public int checkNumber() {
		return checkNumber;
	}

	public void setHistoryID(int historyID) {
		this.historyID = historyID;
	}

	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}

	public void setPrice(double grandTotal) {
		this.grandTotal = grandTotal;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	
	public void setCashPayment(double cashPayment) {
		this.cashPayment = cashPayment;
	}
	
	public void setChange(double change) {
		this.change = change;
	}
	
	public void setCreditCardNumber(long creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}
	
	public void setSecurityCode(int securityCode) {
		this.securityCode = securityCode;
	}
	
	public void setCheckNumber(int checkNumber) {
		this.checkNumber = checkNumber;
	}

}
