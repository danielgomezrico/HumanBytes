package com.medialab.humanbytes.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.medialab.humanbytes.model.accelerometer.AccelerometerManager;
import com.medialab.humanbytes.model.managers.ActivityManager;
import com.medialab.humanbytes.views.GameView;

public class GameActivity extends Activity {
	
	private AccelerometerManager accManager;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		// Share the activity methods over all project clases
		ActivityManager.getInstance().setActivity(this);
		
		GameView gameView = new GameView(this.getBaseContext());
		
		accManager = new AccelerometerManager(this);
		accManager.subscribe(gameView);
		accManager.startListen();
		
		setContentView(gameView);
	}
	
	@Override
	protected void onDestroy() {
		
		Toast.makeText(ActivityManager.getInstance().getActivity(), String.format("ÁPerdiste!"), Toast.LENGTH_SHORT)
				.show();
		
		accManager.stopListen();
		super.onDestroy();
		
	}
	
}