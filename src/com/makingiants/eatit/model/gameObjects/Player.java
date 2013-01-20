package com.makingiants.eatit.model.gameObjects;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;

import com.makingiants.eatit.R;
import com.makingiants.eatit.model.managers.ActivityManager;

public class Player {
	
	// ****************************************************************
	// Constants
	// ****************************************************************
	
	private final static float DISTANCE_MODIFIER = 1.5f;
	private final static int INITIAL_LIFES = 5;
	private final static int BAD_IMAGE_TIMER = 1000;//miliseconds
	private final static int GOOD_IMAGE_TIMER = 1000;//miliseconds
	// ****************************************************************
	// Attributes
	// ****************************************************************
	
	private Bitmap image_good, image_normal, image_bad, image;
	private final float imageWidth, imageHeight;
	private final float imageMiddleWidth, imageMiddleHeight;
	private float posX;
	private final float posY;
	private final float limitX;
	
	private int lifes;
	
	private Handler handlerPutNormalImage;
	private Runnable runnablePutNormalImage = new Runnable() {
		
		@Override
		public void run() {
			image = image_normal;
		}
	};
	
	// ****************************************************************
	// Constructor
	// ****************************************************************
	
	public Player(final float screenWidth, final float screenHeight) {
		final Activity activity = ActivityManager.getInstance().getActivity();
		
		// Setup image and size
		this.image_normal = BitmapFactory.decodeResource(activity.getResources(),
		        R.drawable.object_player);
		this.image_bad = BitmapFactory.decodeResource(activity.getResources(),
		        R.drawable.object_player_bad);
		this.image_good = BitmapFactory.decodeResource(activity.getResources(),
		        R.drawable.object_player_good);
		
		this.image = image_normal;
		
		this.imageWidth = image_normal.getWidth();
		this.imageHeight = image_normal.getHeight();
		this.imageMiddleWidth = imageWidth / 2;
		this.imageMiddleHeight = imageHeight / 2;
		
		this.lifes = INITIAL_LIFES;
		this.posX = screenWidth / 2;
		this.posY = screenHeight - this.image_normal.getHeight() / 2;
		this.limitX = screenWidth;
		
		handlerPutNormalImage = new Handler();
	}
	
	// ****************************************************************
	// Accessor methods
	// ****************************************************************
	
	public Rect getRectangle() {
		return new Rect((int) (posX - imageMiddleWidth), (int) (posY - imageMiddleHeight),
		        (int) (imageMiddleWidth + posX), (int) (imageMiddleHeight + posY));
		
	}
	
	public float getPositionX() {
		return posX;
	}
	
	public float getPositionY() {
		return posY;
	}
	
	public float getWidth() {
		return imageWidth;
	}
	
	public float getHeight() {
		return imageHeight;
	}
	
	public int getLifes() {
		return lifes;
	}
	
	public void restLife() {
		lifes--;
		if (lifes < 0) {
			lifes = 0;
		}
		
		// Show bad image 
		image = image_bad;
		
		handlerPutNormalImage.removeCallbacks(runnablePutNormalImage);//Remove previous updates
		handlerPutNormalImage.postDelayed(runnablePutNormalImage, BAD_IMAGE_TIMER);
		
	}
	
	public void plusScore() {
		
		// Show bad image 
		image = image_good;
		
		handlerPutNormalImage.removeCallbacks(runnablePutNormalImage);//Remove previous updates
		handlerPutNormalImage.postDelayed(runnablePutNormalImage, GOOD_IMAGE_TIMER);
	}
	
	// ****************************************************************
	// Move player position methods
	// ****************************************************************
	
	public void moveX(final float offsetX) {
		
		final float realDistance = offsetX * DISTANCE_MODIFIER;
		
		if (posX + imageMiddleWidth + realDistance < limitX
		        && posX - imageMiddleWidth + realDistance > 0) {
			posX += realDistance;
		}
		
	}
	
	// ****************************************************************
	// Draw methods
	// ****************************************************************
	
	public void draw(final Canvas canvas) {
		canvas.drawBitmap(image, posX - imageMiddleWidth, posY - imageMiddleHeight, null);
	}
	
}
