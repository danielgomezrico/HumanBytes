package com.medialab.humanbytes.activities;

import com.medialab.humanbytes.views.GameView;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;


public class GameActivity extends Activity {
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
        setTitle("Game Control");
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        

     /*   try {
			accelManager = new AccelerometerManager(this);
		} catch (Exception e) {	}*/
        
        setContentView(new GameView(this.getBaseContext()));
    }
}