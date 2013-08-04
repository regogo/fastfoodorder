package com.bupc.fastorder;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.bupc.fastfoodorder.R;

public class ActivityTop extends Activity implements OnClickListener {
	
	private TextView txtName, txtAddress, txtNumber;
	private Button btnEdit, btnOrder;
	private SharedPreferences prefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_top);
		initialize();
		showDetails();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_top, menu);
		return true;
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		showDetails();
	}
	
	
	private void initialize() {
		txtName = (TextView) findViewById(R.id.txtName);
		txtAddress = (TextView) findViewById(R.id.txtAddress);
		txtNumber = (TextView) findViewById(R.id.txtNumber);
		btnEdit = (Button) findViewById(R.id.btnEdit);
		btnOrder = (Button) findViewById(R.id.btnOrdr);
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		btnEdit.setOnClickListener(this);
		btnOrder.setOnClickListener(this);
	}
	
	private void showDetails() {
		txtName.setText("NAME: " + prefs.getString("name", "Costumer Name"));
		txtAddress.setText("ADDRESS: " + prefs.getString("address", "Costumer Address"));
		txtNumber.setText("NUMBER: " + prefs.getString("number", "Costumer Number"));
	}
	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnEdit:
			Intent pref = new Intent(this, ActivityPreference.class);
			startActivity(pref);
			break;

		case R.id.btnOrdr:
			Intent i = new Intent(this, ActivityOrderList.class);
			startActivity(i);
			break;
		}
	}
	
	

}
