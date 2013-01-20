package com.makingiants.eatit.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.makingiants.eatit.R;
import com.makingiants.eatit.model.accelerometer.IAccelerometerObserver;
import com.makingiants.eatit.model.gameObjects.GameController;
import com.makingiants.eatit.model.gameObjects.Player;
import com.makingiants.eatit.model.managers.DisplayManager;

public class GameView extends SurfaceView implements SurfaceHolder.Callback, IAccelerometerObserver {
	
	// ****************************************************************
	// Constants
	// ****************************************************************
	
	private final int TEXT_SIZE = 100;
	
	// ****************************************************************
	// Attributes
	// ****************************************************************
	
	private final SurfaceUpdateThread updateThread;
	
	private final Paint paintText;
	private Bitmap background;
	
	private GameController objManager;
	
	// ****************************************************************
	// Constructor
	// ****************************************************************
	
	public GameView(final Context context) {
		super(context);
		getHolder().addCallback(this);
		setKeepScreenOn(true);
		setFocusable(true);
		//setBackgroundResource(R.drawable.game_background);
		
		paintText = new Paint();
		paintText.setColor(Color.WHITE);
		paintText.setStyle(Style.FILL);
		paintText.setTextSize(TEXT_SIZE);
		
		final int width = DisplayManager.getInstance().getWidth();
		final int height = DisplayManager.getInstance().getHeight();
		
		background = BitmapFactory.decodeResource(getResources(), R.drawable.game_background);
		// For Android, not BB
		/*float scale = (float) temp.getHeight() / (float) getHeight();
		int newWidth = Math.round(temp.getWidth() / scale);
		int newHeight = Math.round(temp.getHeight() / scale);
		background = Bitmap.createScaledBitmap(temp, newWidth, newHeight, true);
		*/
		
		// Init the player
		final Player player = new Player(width, height);
		objManager = new GameController(player);
		
		// Init the rest of the world
		objManager.initThreadNewFallingObjects();
		objManager.initThreadUpdateObjects();
		
		updateThread = new SurfaceUpdateThread(this);
		
	}
	
	// ****************************************************************
	// Game 
	// ****************************************************************
	public void stopGameFlow() {
		objManager.stopThreads();
	}
	
	// ****************************************************************
	// Accelerometer methods
	// ****************************************************************
	
	@Override
	public void changeAcceleromether(final float x, final float y) {
		objManager.movePlayer(-x);
	}
	
	// ****************************************************************
	// Draw methods
	// ****************************************************************
	
	@Override
	public void onDraw(final Canvas canvas) {
		//canvas.drawColor(backgroundColor);
		canvas.drawBitmap(background, 0, 0, null);
		
		if (objManager != null) {
			objManager.drawObjects(canvas);
			
			canvas.drawText(String.format("Score: %d", objManager.getScore()), 0, TEXT_SIZE, paintText);
			canvas.drawText(String.format("Lifes: %d", objManager.getLifes()), 0, TEXT_SIZE * 2,
			        paintText);
		}
		
	}
	
	@Override
	public void surfaceChanged(final SurfaceHolder arg0, final int arg1, final int arg2, final int arg3) {
	}
	
	@Override
	public void surfaceCreated(final SurfaceHolder arg0) {
		updateThread.setRunning(true);
		updateThread.start();
	}
	
	@Override
	public void surfaceDestroyed(final SurfaceHolder arg0) {
		
		// we have to tell thread to shut down & wait for it to finish, or else
		// it might touch the Surface after we return and explode
		boolean retry = true;
		updateThread.setRunning(false);
		
		while (retry) {
			try {
				updateThread.join();
				retry = false;
			} catch (final InterruptedException e) {
				// we will try it again and again...
			}
		}
		
	}
	
}
