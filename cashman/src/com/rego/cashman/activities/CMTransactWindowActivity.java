package com.rego.cashman.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.rego.cashman.R;
import com.rego.cashman.helper.DBHelper;
import com.rego.cashman.model.Cash;
import com.rego.cashman.model.Transaction;
import com.rego.cashman.model.User;
import com.rego.cashman.utils.Log;
import com.rego.cashman.utils.Utils;

public class CMTransactWindowActivity extends Activity implements OnClickListener{
	
	EditText editValue, editDesc;
	TextView txtStatus;
	Transaction transaction;
	Cash cash;
	User user;
	Boolean cashIn;
	private Typeface font;
	private Spinner spinner2;
	String category;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		cashIn = getIntent().getBooleanExtra("cashIn", false);
		setContentView(R.layout.activity_cmtransact_window);
		initialize();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.cmtransact_window, menu);
		return true;
	}
	
	public void initialize() {
		user = User.getCurrent(getApplicationContext());
		cash = Cash.getCurrent(getApplicationContext());
		findViewById(R.id.but_add).setOnClickListener(this);
		editValue = (EditText) findViewById(R.id.edit_amount);
		editDesc = (EditText) findViewById(R.id.edit_desc);
		TextView txtTrans = (TextView) findViewById(R.id.txt_transaction);
		txtTrans.setText(cashIn ? "New Income Record" : "New Expense Record");
		addItemsOnSpinner2();
	}
	
	 public void addItemsOnSpinner2() {

			spinner2 = (Spinner) findViewById(R.id.spinner2);
			List<String> list = new ArrayList<String>();
			if(cashIn) {
				list.add("Salary");
				list.add("Bonus");
				list.add("Raffle");
				list.add("Others");
			} else { 
				list.add("Transpo");
				list.add("Food");
				list.add("Misc");
				list.add("Loan");
			}
			ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);
			dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner2.setAdapter(dataAdapter);
			spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
						int pos, long id) {
					category = (String) parent.getItemAtPosition(pos);	
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {	
				}
			});
		  }
	
	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
			case R.id.but_add:
				if(editValue.length() > 0 ) {
					transaction = new Transaction(cash, Double.parseDouble(editValue.getText().toString()), cashIn, Utils.getTime(), editDesc.getText().toString(), category);
					DBHelper.getInstance(getApplicationContext()).addTransaction(transaction);
					if(cashIn) cash.setValue(cash.getValue() + Double.parseDouble(editValue.getText().toString()));
					else cash.setValue(cash.getValue() - Double.parseDouble(editValue.getText().toString()));
					if(cash.getValue() < 0) cash.setValue(0);
					cash.sync();
					finish();
					this.overridePendingTransition(R.anim.from_right, R.anim.to_left);
				} else {
					Toast toast = Toast.makeText(getApplicationContext(), "Empty fields", Toast.LENGTH_SHORT);
					toast.show();
				}
				break;
	
			}
		
	}

}
