package com.bupc.fastorder.helper;

import java.util.HashMap;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.bupc.fastfoodorder.R;

public class HttpTask extends AsyncTask<HashMap<String, Object>, Integer, String> {
	public static final int CONNECTION_TIMEOUT = 10000;

	protected boolean showProgress;
	protected Context context;
	protected TaskCallback taskCallback;
	protected ProgressDialog progressDialog;

	public HttpTask(Context context, boolean showProgressDialog, TaskCallback taskCallback) {
		this.context = context;
		showProgress = showProgressDialog;
		this.taskCallback = taskCallback;
	}

	@Override
	protected String doInBackground(HashMap<String, Object>... taskDetails) {
		for (HashMap<String, Object> taskDetail : taskDetails) {
			if (taskCallback != null) {
				if (!taskCallback.onTask(taskDetail)) {
					this.cancel(true);
				}
			}
		}
		return null;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		if (showProgress) {
			showProgressDialog();
		}
	}

	@Override
	protected void onProgressUpdate(Integer... index) {
		super.onProgressUpdate(index);
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		dismissProgressDialog();
		if (taskCallback != null) {
			taskCallback.onFinish();
		}
	}

	@Override
	protected void onCancelled() {
		super.onCancelled();
		dismissProgressDialog();
		if (taskCallback != null) {
			taskCallback.onFinish();
		}
	}

	private void showProgressDialog() {
		progressDialog = new ProgressDialog(context);
		progressDialog.setMessage(context.getResources().getString(R.string.progress_loading));
		progressDialog.setIndeterminate(true);
		progressDialog.show();
	}

	private void dismissProgressDialog() {
		if (showProgress) {
			progressDialog.dismiss();
		}
	}

	public interface TaskCallback {
		boolean onTask(HashMap<String, Object> taskDetail);
		void onFinish();
	}
}