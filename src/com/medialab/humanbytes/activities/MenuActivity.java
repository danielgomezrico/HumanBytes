package com.medialab.humanbytes.activities;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.medialab.humanbytes.R;

public class MenuActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setContentView(R.layout.menu);

	    
	}
	
	public void newGameClick(View view){
		newGame();
	}
	
	public void newGame(){
		Intent intent = new Intent(getApplicationContext(), GameActivity.class);
		startActivity(intent);
	}
	
	
	
}
