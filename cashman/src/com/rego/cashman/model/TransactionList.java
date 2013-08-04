package com.rego.cashman.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.MediaStore;

import com.rego.cashman.helper.DBHelper;
import com.rego.cashman.utils.Log;

public class TransactionList {

	private Context context;
	private List<Transaction> transactions;

	public TransactionList(Context context) {
		this.context = context;
	}


	public List<Transaction> getTransactions() throws NullPointerException {
		DBHelper dbHelper = DBHelper.getInstance(context);
		if (transactions == null) {
			transactions = new ArrayList<Transaction>();
			
			String[] projection = { DBHelper.KEY_ID, DBHelper.KEY_TRANSCAT, DBHelper.KEY_TRANSCASHVALUE, DBHelper.KEY_TRANSVALUE, DBHelper.KEY_TRANSDATE, DBHelper.KEY_TRANSDESC, DBHelper.KEY_TRANSFLOW};
			String selection = "";
			SQLiteDatabase db = DBHelper.getInstance(context).getWritableDatabase();
			Cursor cursor = db.query(DBHelper.TABLE_TRANSACTION, projection,null, null, null, null, null, null);
			if(cursor != null) {
				while (cursor.moveToNext()) {
					int transId = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_ID));
					String transCat = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_TRANSCAT));
					String transDate = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_TRANSDATE));
					String transDesc = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_TRANSDESC));
					String transFlow = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_TRANSFLOW));
					String transCashValue = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_TRANSCASHVALUE));
					String transValue = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_TRANSVALUE));
					if(Integer.parseInt(transFlow) == 1) transFlow = "true";
					else transFlow = "false";
					Transaction trans = new Transaction(Double.parseDouble(transCashValue), Double.parseDouble(transValue), Boolean.parseBoolean(transFlow), dateConvert(transDate), transDesc, transCat);
					if(trans != null) {
						transactions.add(trans);
					}
				}
			}
			cursor.close();
			
		}
		Log.debug("josh", "pogi " + String.valueOf(transactions.isEmpty()));
		
		return transactions;
	}
	
	public static int getSum(boolean isIncome, Context context) throws NullPointerException {
		int sum = 0;
		if (sum == 0) {

			String[] projection = { DBHelper.KEY_ID, DBHelper.KEY_TRANSCASHVALUE, DBHelper.KEY_TRANSVALUE, DBHelper.KEY_TRANSFLOW};
			String selection = "";
			SQLiteDatabase db = DBHelper.getInstance(context).getWritableDatabase();
			Cursor cursor = db.query(DBHelper.TABLE_TRANSACTION, projection,null, null, null, null, null, null);
			if(cursor != null) {
				while (cursor.moveToNext()) {
					String transFlow = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_TRANSFLOW));
					String transCashValue = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_TRANSCASHVALUE));
					String transValue = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_TRANSVALUE));
					if(Integer.parseInt(transFlow) == 1) transFlow = "true";
					else transFlow = "false";
					Boolean income = Boolean.parseBoolean(transFlow);
					
					if(isIncome) {
						if(income) {
							sum += Double.parseDouble(transValue);
						}
					} else {
						if(!income)
							sum += Double.parseDouble(transValue);
					}
				}
			}
			cursor.close();
			
		}
		Log.debug("josh", "pogi " + String.valueOf(sum));
		
		return sum;
	}
	
	@SuppressLint("SimpleDateFormat")
	private String dateConvert(String date) {
		String newline = System.getProperty("line.separator");
		String dateStr = date;

		SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd");
		Date dateObj = null;
		try {
			dateObj = curFormater.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		SimpleDateFormat postFormater = new SimpleDateFormat("MMMM dd" + newline + "yyyy");

		String newDateStr = postFormater.format(dateObj);

		return newDateStr;
	}
	
	public ArrayList<Transaction> getAllElements() {
        ArrayList<Transaction> list = new ArrayList<Transaction>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + DBHelper.TABLE_TRANSACTION;
        DBHelper dbHelper = DBHelper.getInstance(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {

            Cursor cursor = db.rawQuery(selectQuery, null);
            try {

                // looping through all rows and adding to list
                if (cursor.moveToFirst()) {
                    do {
                        Transaction obj = new Transaction();
                        //only one column
                        obj.setDate(cursor.getString(0));

                        //you could add additional columns here..

                        list.add(obj);
                    } while (cursor.moveToNext());
                }

            } finally {
                try { cursor.close(); } catch (Exception ignore) {}
            }

        } finally {
             try { db.close(); } catch (Exception ignore) {}
        }

        // return  list
        return list;
    }



}
