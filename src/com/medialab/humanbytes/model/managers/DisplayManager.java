package com.medialab.humanbytes.model.managers;

import android.app.Activity;
import android.content.Context;
import android.view.Display;
import android.view.WindowManager;

public class DisplayManager {
	
	private final int DISPLAY_WIDTH;
	private final int DISPLAY_HEIGHT;
	
	private static DisplayManager instance;
	
	private DisplayManager() {
		
		Activity activity = ActivityManager.getInstance().getActivity();
		
		Display display = ((WindowManager) activity.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		DISPLAY_WIDTH = display.getWidth();
		DISPLAY_HEIGHT = display.getHeight();
		
	}
	
	public static DisplayManager getInstance() {
		
		if (instance == null) {
			instance = new DisplayManager();
		}
		
		return instance;
	}
	
	public int getWidth() {
		return DISPLAY_WIDTH;
	}
	
	public int getHeight() {
		return DISPLAY_HEIGHT;
	}
	
}
