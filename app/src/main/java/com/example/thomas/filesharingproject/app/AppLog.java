package com.example.thomas.filesharingproject.app;

import android.util.Log;

public class AppLog {
	private static final String APP_TAG = "aws";
	
	public static int logString(String message){
		return Log.i(APP_TAG,message);
	}
}
