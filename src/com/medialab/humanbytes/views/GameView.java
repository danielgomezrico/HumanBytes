package com.medialab.humanbytes.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.medialab.humanbytes.R;
import com.medialab.humanbytes.model.accelerometer.IAccelerometerObserver;
import com.medialab.humanbytes.model.gameObjects.GameController;
import com.medialab.humanbytes.model.gameObjects.Player;
import com.medialab.humanbytes.model.managers.ActivityManager;
import com.medialab.humanbytes.model.managers.DisplayManager;

public class GameView extends SurfaceView implements SurfaceHolder.Callback, IAccelerometerObserver {
	
	// ****************************************************************
	// Constants
	// ****************************************************************
	
	private final int TEXT_SIZE = 20;
	
	// ****************************************************************
	// Attributes
	// ****************************************************************
	
	private SurfaceUpdateThread updateThread;
	private int backgroundColor;
	private Paint paintText;
	
	private GameController objManager;
	
	// ****************************************************************
	// Constructor
	// ****************************************************************
	
	public GameView(Context context) {
		super(context);
		getHolder().addCallback(this);
		setKeepScreenOn(true);
		setFocusable(true);
		
		paintText = new Paint();
		paintText.setColor(Color.BLUE);
		paintText.setStyle(Style.FILL);
		paintText.setTextSize(TEXT_SIZE);
		
		initWorld();
		
		backgroundColor = getResources().getColor(android.R.color.background_dark);
		updateThread = new SurfaceUpdateThread(this);

	}
	
	// ****************************************************************
	// Initialize methods
	// ****************************************************************
	
	public void initWorld() {
		
		objManager = new GameController();

		Activity activity = ActivityManager.getInstance().getActivity();
		int width = DisplayManager.getInstance().getWidth();
		int height = DisplayManager.getInstance().getHeight();
		
		// Init the player
		Bitmap playerImage = BitmapFactory.decodeResource(activity.getResources(), R.drawable.robot);
		Player player = new Player(playerImage, width / 2, height - playerImage.getHeight() / 2, width);
		objManager.createPlayer(player);
		
		// Init the rest of the world
		objManager.initThreadNewFallingObjects();
		objManager.initThreadUpdateObjects();
		
	}
	
	// ****************************************************************
	// Accelerometer methods
	// ****************************************************************
	
	@Override
	public void changeAcceleromether(float x, float y) {
		objManager.movePlayer(-x);
	}
	
	// ****************************************************************
	// Draw methods
	// ****************************************************************
	
	@Override
	public void onDraw(Canvas canvas) {
		canvas.drawColor(backgroundColor);
		
		if (objManager != null) {
			objManager.drawObjects(canvas);
			
			canvas.drawText(String.format("Score: %d", objManager.getScore()), 0, TEXT_SIZE, paintText);
			canvas.drawText(String.format("Lifes: %d", objManager.getLifes()), 0, TEXT_SIZE * 2, paintText);
		}
		
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		updateThread.setRunning(true);
		updateThread.start();
	}
	
	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		
		// we have to tell thread to shut down & wait for it to finish, or else
		// it might touch the Surface after we return and explode
		boolean retry = true;
		updateThread.setRunning(false);
		
		while (retry) {
			try {
				updateThread.join();
				retry = false;
			}
			catch (InterruptedException e) {
				// we will try it again and again...
			}
		}
		
	}
	
}
