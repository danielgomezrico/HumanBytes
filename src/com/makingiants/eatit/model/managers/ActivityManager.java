package com.makingiants.eatit.model.managers;

import android.app.Activity;

public class ActivityManager {
	
	private static ActivityManager instance;
	
	private Activity activity;
	
	private ActivityManager() {
		
	}
	
	public static ActivityManager getInstance() {
		
		if (instance == null) {
			instance = new ActivityManager();
		}
		return instance;
	}
	
	public void setActivity(final Activity activity) {
		this.activity = activity;
	}
	
	public Activity getActivity() {
		return activity;
	}
	
	public void finish() {
		if (activity != null) {
			activity.finish();
		}
	}
	
}
