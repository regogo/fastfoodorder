package com.bupc.fastorder.model;

import java.text.DecimalFormat;

public class Item {
	
	DecimalFormat f = new DecimalFormat("##.00");
	private String desc, title;
	private int id;
	private double price;
	
	public Item(int id, String title, String desc, double price) {
		this.id = id;
		this.desc = desc;
		this.price = price;
		this.title = title;
	}
	
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setPrice(double price) {
		this.price = Double.parseDouble(f.format(price));;
	}
	
	public int getId() {
		return id;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public String getTitle() {
		return title;
	}
	
	public double getPrice() {
		return Double.parseDouble(f.format(price));
	}
}
