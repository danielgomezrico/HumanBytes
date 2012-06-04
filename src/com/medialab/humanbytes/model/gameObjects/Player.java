package com.medialab.humanbytes.model.gameObjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Player {
	
	// ****************************************************************
	// Constants
	// ****************************************************************
	
	private final static float DISTANCE_MODIFIER = 0.6f;
	// ****************************************************************
	// Attributes
	// ****************************************************************
	
	private Bitmap image;
	private float imageWidth, imageHeight;
	private float imageMiddleWidth, imageMiddleHeight;
	private float posX;
	private float posY;
	private float limitX;
	
	// ****************************************************************
	// Constructor
	// ****************************************************************
	
	public Player(Bitmap image, float posX, float posY, float limitX) {
		
		this.image = image;
		imageWidth = image.getWidth();
		imageHeight = image.getHeight();
		imageMiddleWidth = imageWidth / 2;
		imageMiddleHeight = imageHeight / 2;
		
		this.posX = posX;
		this.posY = posY;
		this.limitX = limitX;
		
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
	
	// ****************************************************************
	// Move player position methods
	// ****************************************************************
	
	public void moveX(float offsetX) {
		
		float realDistance = offsetX * DISTANCE_MODIFIER;
		
		if (posX + imageMiddleWidth + realDistance < limitX && posX - imageMiddleWidth + realDistance > 0) {
			posX += realDistance;
		}
		
	}
	
	// ****************************************************************
	// Draw methods
	// ****************************************************************
	
	public void draw(Canvas canvas) {
		
		canvas.drawBitmap(image, posX - imageMiddleWidth, posY - imageMiddleHeight, null);
		
	}
	
}
