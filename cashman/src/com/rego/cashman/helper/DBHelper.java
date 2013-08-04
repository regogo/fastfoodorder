package com.rego.cashman.helper;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.rego.cashman.model.Cash;
import com.rego.cashman.model.Constant;
import com.rego.cashman.model.Transaction;
import com.rego.cashman.model.User;
import com.rego.cashman.utils.Log;


public class DBHelper extends SQLiteOpenHelper{
	
	private static final int DATABASE_VERSION = 1;
	private static String DATABASE_NAME = "cashman";
	
	private static final String TABLE_USERS = "users";
	private static final String TABLE_CASH = "cash";
	public static final String TABLE_TRANSACTION = "list";
	
	public static String KEY_ID = "id";
	private static String KEY_USERNAME = "name";
	private static String KEY_USERCASHID = "cash_id";
	
	private static String KEY_CASHCURRENCY = "currency";
	private static String KEY_CASHVALUE = "value";
	
	public static String KEY_TRANSDATE = "date";
	public static String KEY_TRANSDESC = "desc";
	public static String KEY_TRANSCAT = "cat";
	public static String KEY_TRANSFLOW = "flow";
	public static String KEY_TRANSCASHVALUE = "value_cash";
	public static String KEY_TRANSVALUE = "value_trans";
	
	private static DBHelper instance;
	private Context context;
	
	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
	}

	public static DBHelper getInstance(Context context) {
		if (instance == null) instance = new DBHelper(context);
		return instance;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String query = "CREATE TABLE " + TABLE_USERS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_USERNAME + " TEXT," + KEY_USERCASHID + " INTEGER" + ")";
		db.execSQL(query);

		query = "CREATE TABLE " + TABLE_CASH + "(" + KEY_ID + " INTEGER PRIMARY KEY,"  + KEY_CASHCURRENCY + " TEXT," + KEY_CASHVALUE + " INTEGER" + ")";
		db.execSQL(query);

		query = "CREATE TABLE " + TABLE_TRANSACTION + "(" + KEY_ID + " INTEGER PRIMARY KEY,"  + KEY_TRANSDATE + " TEXT," + KEY_TRANSCAT + " TEXT," + KEY_TRANSFLOW + " TEXT," + KEY_TRANSCASHVALUE + " TEXT," + KEY_TRANSVALUE + " TEXT," +  KEY_TRANSDESC + " TEXT" + ")";
		db.execSQL(query);

		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
	
	
	public long addUser() {
		SQLiteDatabase db = getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_USERNAME, "User");
		return db.insert(TABLE_USERS, null, values);
	}
	
	
	
	public void updateUser(User user) {
		SQLiteDatabase db = getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_USERNAME, user.getName());
		values.put(KEY_USERCASHID, user.getCashId());
		String selection = KEY_ID + "=?";
		String[] selectionArgs = { String.valueOf(user.getId()) };
		db.update(TABLE_USERS, values, selection, selectionArgs);
	}
	
		
	public User getUser() {
		SharedPreferences prefs = context.getSharedPreferences(Constant.PREF_KEY, Context.MODE_PRIVATE);
		int userId = prefs.getInt(Constant.PREF_USER, -1);

		if (userId == -1) userId = (int) addUser();
		if (userId == -1) Log.error(this, "Error retrieving a User Profile.");
		else {
			String[] projection = new String[] { KEY_ID, KEY_USERNAME, KEY_USERCASHID};
			SQLiteDatabase db = getWritableDatabase();
			Cursor cursor = db.query(TABLE_USERS, projection, KEY_ID + " = " + userId, null, null, null, null, null);
			if (cursor != null && cursor.moveToFirst()) {
				SharedPreferences.Editor editor = prefs.edit();
				editor.putInt(Constant.PREF_USER, userId);
				editor.commit();

				int id = cursor.getInt(cursor.getColumnIndex(KEY_ID));
				int cashId = cursor.getInt(cursor.getColumnIndex(KEY_USERCASHID));
				String name = cursor.getString(cursor.getColumnIndex(KEY_USERNAME));
				
				return new User(id, name, DBHelper.getInstance(context).getCash());
			}
			cursor.close();
			db.close();
		}
		return null;
	}
	
	public long addCash() {
		Log.debug("josh", "cash added");
		SQLiteDatabase db = getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_CASHVALUE, 0);
		values.put(KEY_CASHCURRENCY, "Php");
		return db.insert(TABLE_CASH, null, values);
	}
	
	public long addTransaction(Transaction trans) {
		SQLiteDatabase db = getWritableDatabase();
		ContentValues values = new ContentValues();
		Log.debug("josh", "add "+trans.isCashIn());
		values.put(KEY_TRANSFLOW, trans.isCashIn());
		values.put(KEY_TRANSDATE, trans.getDate());
		values.put(KEY_TRANSDESC, trans.getDesc());
		values.put(KEY_TRANSCAT, trans.getCategory());
		values.put(KEY_TRANSCASHVALUE, trans.getCashValue());
		values.put(KEY_TRANSVALUE, trans.getTransValue());
		return db.insert(TABLE_TRANSACTION, null, values);
	}
	
	public void updateCash(Cash cash) {
		SQLiteDatabase db = getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_CASHCURRENCY, cash.getCurrency());
		values.put(KEY_CASHVALUE, cash.getValue());
		String selection = KEY_ID + "=?";
		String[] selectionArgs = { String.valueOf(cash.getId()) };
		db.update(TABLE_CASH, values, selection, selectionArgs);
	}
	
	public Cash getCash() {

		SharedPreferences prefs = context.getSharedPreferences(Constant.PREF_KEY, Context.MODE_PRIVATE);
		int cashId = prefs.getInt(Constant.PREF_CASH, -1);

		if (cashId == -1) cashId = (int) addCash();
		if (cashId == -1) Log.error(this, "Error retrieving a Cash Profile.");
		else {
			String[] projection = new String[] { KEY_ID, KEY_CASHVALUE, KEY_CASHCURRENCY};
			SQLiteDatabase db = getWritableDatabase();
			Cursor cursor = db.query(TABLE_CASH, projection, KEY_ID + " = " + cashId, null, null, null, null, null);
			if (cursor != null && cursor.moveToFirst()) {
				SharedPreferences.Editor editor = prefs.edit();
				editor.putInt(Constant.PREF_CASH, cashId);
				editor.commit();

				int id = cursor.getInt(cursor.getColumnIndex(KEY_ID));
				double value = cursor.getInt(cursor.getColumnIndex(KEY_CASHVALUE));
				String currency = cursor.getString(cursor.getColumnIndex(KEY_CASHCURRENCY));
				
				return new Cash(id, currency, value);
			}
			cursor.close();
			db.close();
		}
		return null;
		
	}
	
	
	
	
}
