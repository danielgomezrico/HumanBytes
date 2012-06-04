package com.medialab.humanbytes.model.managers;

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
	
	public void setActivity(Activity activity) {
		this.activity = activity;
	}
	
	public Activity getActivity() {
		return activity;
	}
	
}
