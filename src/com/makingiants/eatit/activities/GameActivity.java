package com.makingiants.eatit.activities;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Toast;

import com.makingiants.eatit.R;
import com.makingiants.eatit.model.accelerometer.AccelerometerManager;
import com.makingiants.eatit.model.managers.ActivityManager;
import com.makingiants.eatit.views.GameView;

public class GameActivity extends Activity {
	
	private AccelerometerManager accManager;
	private MediaPlayer soundtrackPlayer;
	GameView gameView;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Share the activity methods over all project clases
		ActivityManager.getInstance().setActivity(this);
		
		gameView = new GameView(this);
		
		accManager = new AccelerometerManager(this);
		accManager.subscribe(gameView);
		accManager.startListen();
		
		setContentView(gameView);
		
		soundtrackPlayer = MediaPlayer.create(ActivityManager.getInstance().getActivity(),
		        R.raw.game_soundtrack);
		soundtrackPlayer.start();
		soundtrackPlayer.setLooping(true);
	}
	
	@Override
	protected void onPause() {
		
		soundtrackPlayer.stop();
		gameView.stopGameFlow();
		
		finish();
		
		super.onPause();
	}
	
	@Override
	protected void onResume() {
		soundtrackPlayer.start();
		
		super.onResume();
	}
	
	@Override
	protected void onDestroy() {
		
		Toast.makeText(ActivityManager.getInstance().getActivity(),
		        String.format(getString(R.string.game_lose)), Toast.LENGTH_SHORT).show();
		
		soundtrackPlayer.stop();
		accManager.stopListen();
		super.onDestroy();
		
	}
}