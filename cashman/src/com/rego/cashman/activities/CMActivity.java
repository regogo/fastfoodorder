package com.rego.cashman.activities;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.rego.cashman.R;
import com.rego.cashman.model.Cash;
import com.rego.cashman.model.Transaction;
import com.rego.cashman.model.TransactionList;
import com.rego.cashman.model.User;
import com.rego.cashman.utils.Log;
import com.rego.cashman.utils.Utils;

public class CMActivity extends Activity implements OnClickListener {
	
	private TextView textStatus;
	private User user;
	private Cash cash;
	SharedPreferences prefs;
	Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cm_main);
		initialize();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.cm, menu);
		return true;
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		textStatus.setText(message());
		smileys();
		
	}
	
	public void initialize() {
		context = getApplicationContext();
		user = User.getCurrent(context);
		cash = Cash.getCurrent(context);
		user.setCash(cash);
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		findViewById(R.id.but_add).setOnClickListener(this);
		findViewById(R.id.but_expense).setOnClickListener(this);
		findViewById(R.id.but_info).setOnClickListener(this);
		findViewById(R.id.but_setting).setOnClickListener(this);
		textStatus = (TextView) findViewById(R.id.txt_message);
		textStatus.setText(message());
		smileys();
	}
	
	private String message() { 
		//TODO
		String currency = prefs.getString("currency", "PHP");
		cash.setCurrency(currency);
		String userName = prefs.getString("user", "MOJOJOJO");
		user.setName(userName);
		String newline = System.getProperty("line.separator");
		String status = "Date: " + Utils.getTime() + newline + "Account Name: " + String.valueOf(user.getName()) + newline
						+ "Current Cash: " + currency + String.valueOf(cash.getValue() > 0 ? cash.getValue() : 0) + newline
						+ "Total Income: " + currency + TransactionList.getSum(true, context) + newline
						+ "Total Expenses: " + currency + TransactionList.getSum(false, context);
		return status;
	} 
	
	private void smileys() {
		ImageView emo = (ImageView) findViewById(R.id.img_emo);
		double ieRatio;
		try {
			ieRatio = TransactionList.getSum(false, context) / TransactionList.getSum(true, context);
		} catch (ArithmeticException e) { 
			e.printStackTrace();
			ieRatio = 0;
		};
		if(ieRatio > 1) emo.setImageResource(R.drawable.emo_sad);
		if(ieRatio <= 1 & ieRatio > 0.5) emo.setImageResource(R.drawable.emo_nya);
		if(ieRatio < 0.5) emo.setImageResource(R.drawable.emo_happy);
		Log.debug("josh", "IE "+ String.valueOf(ieRatio));
	}
	

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.but_add:
			Intent i = new Intent(getApplicationContext(), CMTransactWindowActivity.class);
			i.putExtra("cashIn", true);
			startActivity(i);
			this.overridePendingTransition(R.anim.from_left, R.anim.to_right);
			break;
		case R.id.but_expense:
			Intent j = new Intent(getApplicationContext(), CMTransactWindowActivity.class);
			j.putExtra("cashIn", false);
			startActivity(j);
			this.overridePendingTransition(R.anim.from_left, R.anim.to_right);
			break;
		case R.id.but_info:
			Intent k = new Intent(getApplicationContext(), CMTransactListActivity.class);
			startActivity(k);
			this.overridePendingTransition(R.anim.from_right, R.anim.to_left);
			break;
		case R.id.but_setting:
			Intent pref = new Intent(this,CMPreferenceActivity.class);
    		startActivity(pref);
			break;
		}
	}

}
