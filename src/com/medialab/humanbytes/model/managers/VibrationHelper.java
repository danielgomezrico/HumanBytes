package com.medialab.humanbytes.model.managers;

import android.app.Activity;
import android.content.Context;
import android.os.Vibrator;

public class VibrationHelper {

	public static void vibrate(int miliseconds){
		Activity activity = ActivityManager.getInstance().getActivity();
		
		// Get instance of Vibrator from current Context
		Vibrator v = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);
		 
		// Vibrate for 300 milliseconds
		v.vibrate(miliseconds);

	}
}
