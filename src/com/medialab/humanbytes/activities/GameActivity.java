package com.medialab.humanbytes.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.medialab.humanbytes.R;
import com.medialab.humanbytes.model.managers.ActivityManager;
import com.medialab.humanbytes.views.GameView;

public class GameActivity extends Activity {
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setTitle("Game Control");
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		// Share the activity methods over all project clases
		ActivityManager.getInstance().setActivity(this);
		
		setContentView(R.layout.main);
		setContentView(new GameView(this.getBaseContext()));
		
	}
}