package com.rego.cashman.model;

import java.text.DecimalFormat;

import android.content.Context;

import com.rego.cashman.helper.DBHelper;

public class Cash {

	private String currency;
	private double value;
	private int id;
	DecimalFormat f = new DecimalFormat("##.00");
	private static Cash currentCash;
	private static Context context;
	public static String CUR_PESO = "PHP";
	public static String CUR_DOLLAR = "USD";
	public static String CUR_YEN = "JPY";
	
	public Cash(String currency, double value) {
		this.currency = currency;
		this.value = value;
	}
	
	public Cash(int id, String currency, double value) {
		this.currency = currency;
		this.value = value;
		this.id = id;
	}
	
	public static Cash getCurrent(Context context) {
		if (currentCash == null) {
			Cash.context = context;
			currentCash = DBHelper.getInstance(context).getCash();
		}
		return currentCash;
	}
	
	public void sync() {
		DBHelper.getInstance(context).updateCash(this);
	}
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public double getValue() {
		return Double.parseDouble(f.format(value));
	}
	
	public void setValue(double value) {
		this.value = Double.parseDouble(f.format(value));
	}
	
	public String getCurrency() {
		return currency;
	}
	
	public void setCurrency(String currency) {
		this.currency = currency;
	}
}
