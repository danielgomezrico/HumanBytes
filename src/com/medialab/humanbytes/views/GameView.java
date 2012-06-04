package com.medialab.humanbytes.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.medialab.humanbytes.R;
import com.medialab.humanbytes.model.accelerometer.AccelerometerManager;
import com.medialab.humanbytes.model.accelerometer.IAccelerometerObserver;
import com.medialab.humanbytes.model.gameObjects.Player;
import com.medialab.humanbytes.model.managers.DisplayManager;
import com.medialab.humanbytes.model.managers.ObjectManager;

public class GameView extends SurfaceView implements SurfaceHolder.Callback, IAccelerometerObserver {
	
	// ****************************************************************
	// Attributes
	// ****************************************************************
	
	
	private SurfaceUpdateThread updateThread;
	private int backgroundColor;
	
	private ObjectManager objManager;
	
	private AccelerometerManager accManager;
	
	// ****************************************************************
	// Constructor
	// ****************************************************************
	
	public GameView(Context context) {
		super(context);
		getHolder().addCallback(this);
		updateThread = new SurfaceUpdateThread(this);
		
		backgroundColor = getResources().getColor(android.R.color.background_dark);
		
		setFocusable(true);
		
		accManager = new AccelerometerManager(context);
		accManager.subscribe(this);
		accManager.startListen();
		
		initWorld();
	}
	
	// ****************************************************************
	// Initialize methods
	// ****************************************************************
	
	public void initWorld() {
		
		int width = DisplayManager.getInstance().getWidth();
		int height = DisplayManager.getInstance().getHeight();
		
		objManager = ObjectManager.getInstance();

		// Init the player
		Bitmap playerImage = BitmapFactory.decodeResource(getResources(), R.drawable.robot);
		Player player = new Player(playerImage, width / 2, height - playerImage.getHeight() / 2,
				width);
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
		
		objManager.drawObjects(canvas);
		
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
