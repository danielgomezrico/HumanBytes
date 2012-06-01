package com.medialab.humanbytes.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Player {

	private final static float DISTANCE_MODIFIER = 0.6f;
	
	private Bitmap image;
	private float imageMiddleWidth;
	private float posX;
	private float posY;
	private float limitX;

	public Player(Bitmap image, float posX, float posY, float limitX) {
		
		this.image = image;
		this.posX = posX;
		this.posY = posY;
		this.limitX = limitX;
		
		imageMiddleWidth = image.getWidth()/2;
		
	}
	
	public void moveX(float offsetX){
		
		float realDistance = offsetX * DISTANCE_MODIFIER;
		
		if(posX + imageMiddleWidth + realDistance < limitX && posX - imageMiddleWidth + realDistance > 0){
			posX += realDistance;
		}
		
	}

	public void draw(Canvas canvas){

		canvas.drawBitmap(image, posX - imageMiddleWidth, posY - imageMiddleWidth, null);

	}


}
