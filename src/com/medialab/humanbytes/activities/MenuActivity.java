package com.medialab.humanbytes.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
	
	public void newGameClick(View view) {
		newGame();
	}
	
	public void newGame() {
		Intent intent = new Intent(getApplicationContext(), GameActivity.class);
		startActivity(intent);
	}
	
	/**
	 * Add menu to the view
	 * */
	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		final MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_panel_principal, menu);
		return true;
	}
	
	/**
	 * Menu events
	 */
	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		
		// Handle item selection
		switch (item.getItemId()) {
		
			case R.id.itemAbout:
				
				// declared as final to be able to reference it in inner
				// class declartations of the handlers
				final AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle("About");
				builder.setMessage("Designed by: \n- Laura Canastero \n- Camila Diaz \n- Alejandra Escobar "
						+ "\n- Juanita Barrera \n\nDeveloped by: \n- Juan Alberto Uribe Otero \n- Daniel Gomez Rico");
				builder.setIcon(android.R.drawable.ic_dialog_alert);
				
				builder.show();
				
				return true;
		}
		
		return true;
	}
	
}
