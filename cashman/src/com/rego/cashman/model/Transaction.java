package com.rego.cashman.model;

public class Transaction {
	
	
	private boolean cashIn;
	private String date;
	private String description;
	private String category;
	private Cash cash;
	private double cashValue, transValue;
	
	public Transaction(Cash cash, double trans_value,boolean cash_in, String date, String description, String category) {
		this.cash = cash;
		this.cashIn = cash_in;
		this.date = date;
		this.description = description;
		this.category = category;
		this.transValue = trans_value;
		if(cashIn) this.cashValue = cash.getValue() + transValue;
		else this.cashValue = cash.getValue() - transValue;
		//execute(trans_value);
	}
	
	public Transaction(double cash_value, double trans_value,boolean cash_in, String date, String description, String category) {
		this.cashIn = cash_in;
		this.date = date;
		this.description = description;
		this.transValue = trans_value;
		this.cashValue = cash_value;
		this.category = category;
	}
	
	
	public Transaction() {};
	
	public void sync() {
		
	}
	public boolean isCashIn() {
		return cashIn;
	}
	
	public String getDate() {
		return date;
	}
	
	public String getDesc() {
		return description;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void cashOut() {
		this.cashIn = false;
	}
	
	public void cashIn() {
		this.cashIn = true;
	}
	
	public void setDate(String date) {
		this.date =  date;
	} 
	
	public void setDesc(String desc) {
		this.description = desc;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	public void setTransCashValue (double value) {
		this.cashValue = value;
	}
	
	public double getTransValue() {
		return transValue;
	}
	
	public double getCashValue() {
		return cashValue;
	}
	
	public void execute(double value) {
		if(cashIn) cash.setValue(cash.getValue() + value);
		else cash.setValue(cash.getValue() - value);
		cash.sync();
	}
	
	
}
