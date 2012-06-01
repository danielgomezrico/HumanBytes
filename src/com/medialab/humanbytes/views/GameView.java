package com.medialab.humanbytes.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import com.medialab.humanbytes.R;
import com.medialab.humanbytes.model.FallingObject;
import com.medialab.humanbytes.model.ObjectManager;
import com.medialab.humanbytes.model.Player;
import com.medialab.humanbytes.model.accelerometer.AccelerometerManager;
import com.medialab.humanbytes.model.accelerometer.IAccelerometerObserver;

public class GameView extends SurfaceView implements SurfaceHolder.Callback,
		IAccelerometerObserver {

	private final int DISPLAY_WIDTH;
	private final int DISPLAY_HEIGHT;

	private SurfaceUpdateThread updateThread;
	private int backgroundColor;

	private ObjectManager objManager;

	private AccelerometerManager accManager;

	public GameView(Context context) {
		super(context);
		getHolder().addCallback(this);
		updateThread = new SurfaceUpdateThread(this);

		Display display = ((WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		DISPLAY_WIDTH = display.getWidth();
		DISPLAY_HEIGHT = display.getHeight();

		backgroundColor = getResources().getColor(
				android.R.color.background_dark);

		setFocusable(true);

		accManager = new AccelerometerManager(context);
		accManager.subscribe(this);
		accManager.startListen();

		initWorld();
	}

	public void initWorld() {

		objManager = ObjectManager.getInstance();

		// Init the player
		Bitmap playerImage = BitmapFactory.decodeResource(getResources(),
				R.drawable.robot);
		Player player = new Player(playerImage, DISPLAY_WIDTH / 2,
				DISPLAY_HEIGHT - playerImage.getHeight() / 2, DISPLAY_WIDTH);
		objManager.createPlayer(player);

		// Init the rest of the world
		initThreadNewFallingObjects();

	}

	public void initThreadNewFallingObjects() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					Bitmap image = BitmapFactory.decodeResource(getResources(),
							R.drawable.tuercabuena);

					// Set the limits, left limit  right limit
					float x = image.getWidth()/2 + (float) (Math.random() * (DISPLAY_WIDTH - image.getWidth()));
					
					objManager.addFallingObject(new FallingObject(x, 0,
							DISPLAY_HEIGHT, image));

					try {
						// Poner este tiempo como variable
						Thread.sleep(800);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}

		}).start();
	}

	@Override
	public void changeAcceleromether(float x, float y) {
		objManager.movePlayer(-x);
	}

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
			} catch (InterruptedException e) {
				// we will try it again and again...
			}
		}

	}

}
