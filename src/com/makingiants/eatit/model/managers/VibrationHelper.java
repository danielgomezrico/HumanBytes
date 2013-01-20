package com.makingiants.eatit.model.managers;

import android.app.Activity;
import android.content.Context;
import android.os.Vibrator;

public class VibrationHelper {
	
	public static void vibrate(final int miliseconds) {
		//TODO: Allow vibration 
		final Activity activity = ActivityManager.getInstance().getActivity();
		
		// Get instance of Vibrator from current Context
		final Vibrator v = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);
		
		// Vibrate for 300 milliseconds
		v.vibrate(miliseconds);
		
	}
}
