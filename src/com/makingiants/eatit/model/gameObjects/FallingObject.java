package com.makingiants.eatit.model.gameObjects;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class FallingObject {
	
	// ****************************************************************
	// Attributes
	// ****************************************************************
	
	private float moveDistance;
	
	private final Bitmap image;
	private final float imageWidth, imageHeight;
	private final float imageMiddleWidth, imageMiddleHeight;
	private final float posX;
	private float posY;
	
	private final boolean good;
	
	// ****************************************************************
	// Constructor
	// ****************************************************************
	
	public FallingObject(final float posX, final float posY, final Bitmap image, final boolean good) {
		this.posX = posX;
		this.posY = posY;
		
		this.image = image;
		imageWidth = image.getWidth();
		imageHeight = image.getHeight();
		imageMiddleWidth = imageWidth / 2;
		imageMiddleHeight = imageHeight / 2;
		
		// TODO: Hacerla randomica
		final Random random = new Random();
		moveDistance = random.nextInt(10);
		if (moveDistance < 5) {
			moveDistance = 5;
		}
		
		this.good = good;
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
	
	public boolean isGood() {
		return good;
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
	
	public void draw(final Canvas canvas) {
		
		canvas.drawBitmap(image, posX - imageMiddleWidth, posY - imageMiddleHeight, null);
		
	}
	
}
