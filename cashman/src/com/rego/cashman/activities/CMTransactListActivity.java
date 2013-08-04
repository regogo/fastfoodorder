package com.rego.cashman.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.TextView;

import com.rego.cashman.R;
import com.rego.cashman.model.Transaction;
import com.rego.cashman.model.TransactionList;


public class CMTransactListActivity extends Activity implements TextWatcher{
	
	private TransactionList transList;
	private Context context;
	private ListView listView;
	private TransactionListAdapter transAdapter;
	private EditText editSearch;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transact_list);
		initialize();
		setList();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.cm, menu);
		return true;
	}
	
	
	private void initialize() {
		context = getApplicationContext();
		listView = (ListView) findViewById(R.id.transactionListView);
	}
	
	private void setList() {
		transList = new TransactionList(context);
		ArrayList<Transaction> transactions = null;
		transactions = (ArrayList<Transaction>) transList.getTransactions();
		transAdapter = new TransactionListAdapter(getApplicationContext(), R.layout.transactlist_item, transactions); //1 = layout
		listView.setAdapter(transAdapter);
		listView.setOnItemClickListener(transAdapter);
		listView.setTextFilterEnabled(true);
		editSearch = (EditText) findViewById(R.id.edit_search);
		editSearch.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
		editSearch.addTextChangedListener((TextWatcher) this);	
	}
	
	
	
	private class TransactionListAdapter extends ArrayAdapter<Transaction> implements OnItemClickListener, Filterable {

		private int layoutId;
		ArrayList<Transaction> originalTransList = null;
		ArrayList<Transaction> filteredTransList = null;
		TransFilter filter;

		public TransactionListAdapter(Context context, int layoutId, ArrayList<Transaction> items) {
			super(context, layoutId, items);
			this.layoutId = layoutId;
			this.originalTransList = new ArrayList<Transaction>(items);
			this.filteredTransList = new ArrayList<Transaction>(items);
		}

		@Override
		public Filter getFilter() {
			if (filter == null) filter = new TransFilter();
			return filter;
		}

		@Override
		public int getCount() {
			return filteredTransList.size();
		}

		@Override
		public Transaction getItem(int position) {
			return filteredTransList.get(position);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder itemView = null;
			String newline = System.getProperty("line.separator");
			if (convertView == null) {
				convertView = getLayoutInflater().inflate(layoutId, null);
				itemView = new ViewHolder();
								itemView.amount = (TextView) convertView.findViewById(R.id.amt);
				itemView.date =  (TextView) convertView.findViewById(R.id.date);
				itemView.description =  (TextView) convertView.findViewById(R.id.desc);
				itemView.cashValue  = (TextView) convertView.findViewById(R.id.cash_value);
				convertView.setTag(itemView);
				
			} else itemView = (ViewHolder) convertView.getTag();
			Transaction trans = getItem(position);

			itemView.amount.setTextColor(Color.parseColor(trans.isCashIn() ? "#00ff00" : "#ff0000"));

			itemView.amount.setText(String.valueOf("( " + trans.getTransValue()) + " )");
			itemView.cashValue.setText(String.valueOf(trans.getCashValue()));
			itemView.date.setText(trans.getDate());
			itemView.description.setText(trans.getDesc() + newline + trans.getCategory());

		
			return convertView;
		}

		private class ViewHolder {
			public TextView date, amount, cashValue, description;

		}

		

		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
		}

		private class TransFilter extends Filter {
			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				FilterResults result = new FilterResults();
				if (constraint.length() == 0) {
					result.values = originalTransList;
					result.count = originalTransList.size();
				} else {
					ArrayList<Transaction> filteredResults = new ArrayList<Transaction>();
					ArrayList<Transaction> unFilteredResults = new ArrayList<Transaction>();
					synchronized (this) {
						unFilteredResults.addAll(originalTransList);
					}
					for (Transaction p : unFilteredResults) {
						if (p.getCategory().toUpperCase().startsWith(constraint.toString().toUpperCase()) || p.getDate().toUpperCase().startsWith(constraint.toString().toUpperCase()) || p.getDesc().toUpperCase().startsWith(constraint.toString().toUpperCase())) {
							filteredResults.add(p);
						}
					}
					result.values = filteredResults;
					result.count = filteredResults.size();
				}
				return result;
			}

			@SuppressWarnings("unchecked")
			@Override
			protected void publishResults(CharSequence constraint, FilterResults results) {
				filteredTransList = (ArrayList<Transaction>) results.values;
				if (results.count > 0) {
					notifyDataSetChanged();
				} else {
					notifyDataSetInvalidated();
				}
			}

		}
	}



	@Override
	public void afterTextChanged(Editable s) {

	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		transAdapter.getFilter().filter(s.toString());
		transAdapter.notifyDataSetChanged();
		
	}
	
	
}
