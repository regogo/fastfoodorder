package com.bupc.fastorder.utils;

public class Log {
	
	private static final String TAG = "beatsanity";
    private static final boolean ISDEBUG = true;

    public static void verbose(Object obj, String str) {
    	if(ISDEBUG) {
    		android.util.Log.v(TAG, obj.getClass().getSimpleName() + " " + str);
    	}
    }

    public static void debug(Object obj, String str) {
    	if(ISDEBUG) {
    		android.util.Log.d(TAG, obj.getClass().getSimpleName() + " " + str);
    	}
    }
    
    public static void info(Object obj, String str) {
    	if(ISDEBUG) {
    		android.util.Log.i(TAG, obj.getClass().getSimpleName() + " " + str);
    	}
    }
    
    public static void warn(Object obj, String str) {
    	if(ISDEBUG) {
    		android.util.Log.w(TAG, obj.getClass().getSimpleName() + " " + str);
    	}
    }
    
    public static void error(Object obj, String str) {
    	if(ISDEBUG) {
    		android.util.Log.e(TAG, obj.getClass().getSimpleName() + " " + str);
    	}
    }

    public static void errorStack(Exception e) {
    	if(ISDEBUG) {
    		android.util.Log.e(TAG, "" + e);
//            StackTraceElement[] stack = e.getStackTrace();
//            for(int i=0; i<stack.length; i++) { 
//            	android.util.Log.e(TAG, ": " + stack[i]);
//            }
        }
    }

}
