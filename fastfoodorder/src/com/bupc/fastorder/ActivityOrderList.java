package com.bupc.fastorder;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bupc.fastorder.helper.ApiRequest;
import com.bupc.fastorder.helper.ApiRequest.ResponseCallback;
import com.bupc.fastorder.model.Item;


//TODO

/*
 * layouts for this activity
 * ask norbert about the categories
 * minor fixing
 * 80% done
 * 
 * */
public class ActivityOrderList extends Activity implements OnClickListener{
	
	Context context;
	private PostServerCallback postCallback;
	protected JSONArray jsonArray;
	private List<Item> orderList;
	private String CAT_SIDES = "Side Extra";
	private String CAT_DRINKS = "Drinks";
	private String CAT_UPS = "Upgrade";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initialize();
		
	}
	
	@Override
	protected void onStart() {
		requestServer(this, ApiRequest.API_GET_ALL_ITEMS, null, CAT_SIDES);
		super.onStart();
		
	}
	
	private void initialize() {
		context = getApplicationContext();
		setCallBack();
	}
	
	private void setList(JSONArray json) throws JSONException {
		Log.d("josh","isnull" +  String.valueOf(jsonArray));
		List<Item> items = getOrder(jsonArray);
		Log.d("josh", "laki"  +String.valueOf(items.size()));
		//Log.d("josh", String.valueOf(items));
		//gridView = ((GridView) findViewById(R.id.grid_view));
		//orderAdapter = new OrderAdapter(this, layoutId, items)
		//gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		//gridView.setAdapter(beatAdapter);

	}
	//TODO specify format for the items
	private List<Item> getOrder(JSONArray json) throws JSONException {
		
		if (orderList == null) {
			orderList = new ArrayList<Item>();
			if (json != null) {
				for (int i = 0; i < json.length(); i++) {
					JSONObject jsonItem= json.getJSONObject(i);
					String desc = jsonItem.getString("Description");
					String title = jsonItem.getString("Title");
					int id = Integer.parseInt(jsonItem.getString("Id"));
					double price = Double.parseDouble(jsonItem.getString("Price"));
					Item item = new Item(id,title, desc, price);
					Log.d("josh", "DESC " + item.getPrice());
					orderList.add(item);
				}
			}
		}

		return orderList;
	}
	
	public void requestServer(final Activity activity, final String request, final OrderAdapter.ViewHolder viewHolder, final String category) {
		JSONObject jsonParams = new JSONObject();
		try {
			jsonParams.put("category", "");
			jsonParams.put("title", "");
			jsonParams.put("description","");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		ApiRequest.post(this,ApiRequest.API_GET_ALL_ITEMS, jsonParams, true, new ResponseCallback() {
			JSONObject jsonResponse;
			@Override
			public void onResponse(String response) {
				
			}
			
			@Override
			public void onFinish(String response) {
				Log.d("josh", response);
				try {
					jsonResponse = new JSONObject(response);
					if (jsonResponse.getBoolean("success")) {
						jsonArray = jsonResponse.getJSONObject("data").getJSONArray(category);
						Log.d("josh", "eto " + jsonArray.toString());
						onFinished(jsonArray);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		} );
	}
	
	
	public class OrderAdapter extends ArrayAdapter<Item> {
		private int layoutId;
		private Activity activity;
		
		public OrderAdapter(Activity activity, int layoutId, List<Item> items) {
			super(context, layoutId, items);
			this.activity = activity;
			this.layoutId = layoutId;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder itemView;
			if(convertView == null) {
				convertView = getLayoutInflater().inflate(layoutId, null);
				itemView = new ViewHolder();
				//itemView.itemName  = 
				//itemView.itemDesc = 
				//itemView.itemPrice = 
			} else itemView = (ViewHolder) convertView.getTag();
			return convertView;
		}
		public class ViewHolder implements OnClickListener {
			
			private TextView itemName, itemPrice, itemDesc;
			
			@Override
			public void onClick(View v) {
				
			}
			
		}
		
	}
	
	private void setCallBack() {
		this.postCallback = new PostServerCallback() {
			@Override
			public void finish(JSONArray json) {
				//jsonArray = json;
				try {
					setList(json);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
	}
	
	public interface PostServerCallback {
		void finish(JSONArray json);

	}
	
	private void onFinished(JSONArray json) {
		if (postCallback != null) postCallback.finish(json);
	}

	@Override
	public void onClick(View v) {
		
	}

}
