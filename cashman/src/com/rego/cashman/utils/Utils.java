package com.rego.cashman.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class Utils {
	
	private static int MIN_SEC = 60;
	private static long SEC_MILLISEC = 1000;
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static int getRandomInt(int max) {
		return new Random().nextInt(max);
	}

	public static int getRandomInt(int min, int max) {
		return new Random().nextInt(max - min) + max;
	}

	public static String getTime() {
		return formatTime(Calendar.getInstance().getTime());
	}
	
	public static String formatTime(Date time) {
		return DATE_FORMAT.format(time);
	}
	
	public static int milliToSeconds(long milliSeconds) {
		return (int) (milliSeconds / SEC_MILLISEC);
	}
	
	public static String formatSecToMinSec(int seconds) {
		return (seconds / MIN_SEC) + ":" + prefixNumWithZeros((seconds % MIN_SEC), 2);
	}
	
	public static String prefixNumWithZeros(int num, int minDecPlaces) {
		String numStr = String.valueOf(num);
		while(numStr.length() < minDecPlaces) {
			numStr = "0" + numStr;
		}
		return numStr;
	}
	
	
 }
