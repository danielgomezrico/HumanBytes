package com.makingiants.eatit.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.makingiants.eatit.R;

public class MenuActivity extends Activity {
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		
	}
	
	public void newGameClick(final View view) {
		newGame();
	}
	
	public void newGame() {
		final Intent intent = new Intent(getApplicationContext(), GameActivity.class);
		startActivity(intent);
	}
	
}
