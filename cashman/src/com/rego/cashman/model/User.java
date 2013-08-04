package com.rego.cashman.model;

import com.rego.cashman.helper.DBHelper;

import android.content.Context;

public class User {
	
	private static User currentUser;
	private static Context context;
	private String name;
	private Cash cash;
	private int id;
	
	public User(int id, String name, Cash cash) {
		this.cash = cash;
		this.name = name;
		this.id = id;
	};
	
	public static User getCurrent(Context context) {
		if (currentUser == null) {
			User.context = context;
			currentUser = DBHelper.getInstance(context).getUser();
		}
		return currentUser;
	}
	
	public void setCash(Cash cash) {
		this.cash = cash;
	}
	
	public Cash getCash() {
		return cash;
	}
	
	public String getName(){
		return name;
	}
	
	public int getCashId() {
		return cash.getId();
	}
	
	public int getId() {
		return id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
}
