package com.medialab.humanbytes.model.gameObjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class FallingObject {
	
	// ****************************************************************
	// Attributes
	// ****************************************************************
	
	private float moveDistance;
	
	private Bitmap image;
	private float imageWidth, imageHeight;
	private float imageMiddleWidth, imageMiddleHeight;
	private float posX;
	private float posY;
	
	// ****************************************************************
	// Constructor
	// ****************************************************************
	
	public FallingObject(float posX, float posY, Bitmap image) {
		this.posX = posX;
		this.posY = posY;
		
		this.image = image;
		imageWidth = image.getWidth();
		imageHeight = image.getHeight();
		imageMiddleWidth = imageWidth / 2;
		imageMiddleHeight = imageHeight / 2;
		
		// TODO: Hacerla randomica
		moveDistance = 2;
	}
	
	// ****************************************************************
	// Accesor methods
	// ****************************************************************
	
	public Rect getRectangle() {
		return new Rect((int) (posX - imageMiddleWidth), (int) (posY - imageMiddleHeight),
				(int) (imageMiddleWidth + posX), (int) (imageMiddleHeight + posY));
	}
	
	public float getPositionY() {
		return posY;
	}
	
	public float getPositionX() {
		return posX;
	}
	
	public float getWith() {
		return imageWidth;
	}
	
	public float getHeight() {
		return imageHeight;
	}
	
	// ****************************************************************
	// Move methods
	// ****************************************************************
	
	public void move() {
		posY += moveDistance;
		
	}
	
	// ****************************************************************
	// Draw
	// ****************************************************************
	
	public void draw(Canvas canvas) {
		
		canvas.drawBitmap(image, posX - imageMiddleWidth, posY - imageMiddleHeight, null);
		
	}
	
}
