package com.bupc.fastorder.model;

import android.content.Context;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

public class Common {

	public static final int HTTP_TIMEOUT = 60000;

	private static String filesDir;
	private static Typeface typePetita, typeVisitor;
	private static ConnectivityManager connectivityManager;
	private static NetworkInfo activeNetworkInfo;
	private static Animation inFromTop;

	public static String getFilesDirectory(Context context) {
		if (filesDir == null) filesDir = context.getExternalFilesDir(null).getAbsolutePath();
		return filesDir; 
	}
	
	public static Typeface getTypePrime(Context context) {
		if (typePetita == null) typePetita = Typeface.createFromAsset(context.getAssets(), "fonts/Prime.otf");
		return typePetita;
	}

	public static Typeface getTypeVisitor(Context context) {
		if (typeVisitor == null) typeVisitor = Typeface.createFromAsset(context.getAssets(), "fonts/Visitor.ttf");
		return typeVisitor;
	}
	
	public static Animation inFromTopAnimation() {
		if(inFromTop == null) {
			inFromTop = new TranslateAnimation(
			Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,  0.0f,
			Animation.RELATIVE_TO_PARENT,  -1.0f, Animation.RELATIVE_TO_PARENT,   0.0f
			);
			inFromTop.setDuration(1000);
			inFromTop.setInterpolator(new AccelerateInterpolator());
			}
		return inFromTop;
		} 


	public static boolean isNetworkAvailable(Context context) {
	    if(connectivityManager == null & activeNetworkInfo == null) {
	    	connectivityManager   = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    	activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    }
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
}