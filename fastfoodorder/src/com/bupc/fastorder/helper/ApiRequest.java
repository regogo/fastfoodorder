package com.bupc.fastorder.helper;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.CancellationException;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;
import android.app.Activity;
import com.bupc.fastorder.helper.HttpTask.TaskCallback;
import com.bupc.fastorder.model.Common;
import com.bupc.fastorder.utils.Log;

public class ApiRequest {

	public static final String API_URL = "http://ncserver.ap01.aws.af.cm/ncserver/app/webroot/api/";

	public static final String API_GET_ALL_ITEMS = "get_all_items";
	public static final String API_PURCHASE_ITEM = "purchase_item";

			
	public static void post(final Activity activity, String apiName, final JSONObject jsonParams, boolean showProgress, final ResponseCallback responseCallback) {
		HashMap<String, Object> details = new HashMap<String, Object>();
		details.put("api", apiName);

		final HttpTask task = new HttpTask(activity, showProgress, new TaskCallback() {
			String response;
			@Override
			public boolean onTask(HashMap<String, Object> details) {
				
				try {
					HttpParams httpParams = new BasicHttpParams();
					HttpConnectionParams.setConnectionTimeout(httpParams, Common.HTTP_TIMEOUT);
					HttpConnectionParams.setSoTimeout(httpParams, Common.HTTP_TIMEOUT);
					HttpClient client = new DefaultHttpClient(httpParams);
					HttpPost request = new HttpPost(API_URL + details.get("api").toString());

					request.setEntity(new ByteArrayEntity(jsonParams.toString().getBytes("UTF8")));
					response = client.execute(request, new BasicResponseHandler());
					responseCallback.onResponse(response);
					return true;
				} catch (IOException e) {
					Log.errorStack(e);
				}
				return false;
			}

			@Override
			public void onFinish() {
				responseCallback.onFinish(response);
			}
		});

		try {
			task.execute(new HashMap[] { details });
		} catch (CancellationException e) {
			Log.errorStack(e);
		}
	}
	
	public interface ResponseCallback {
		void onResponse(String response);
		void onFinish(String response);
	}
}