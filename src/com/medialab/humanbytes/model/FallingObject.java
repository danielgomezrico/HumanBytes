package com.medialab.humanbytes.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;


public class FallingObject  implements Runnable{

	private final static int TIEMPO = 50;

	private float distancia;


	private Bitmap image;
	private float posX;
	private float posY;
	private float limitY;

	public FallingObject(float posX, float posY, float limitY, Bitmap image){
		this.posX = posX;
		this.posY = posY;
		this.image = image;
		this.limitY = limitY;

		//TODO: Hacerla randomica
		distancia = 2;

		new Thread(this).start();
	}

	public void draw(Canvas canvas){

		canvas.drawBitmap(image, posX - (image.getWidth() / 2), posY - (image.getHeight() / 2), null);

	}

	@Override
	public void run() {
		boolean onScreen = true;

		while(onScreen){

			posY += distancia;

			try {
				Thread.sleep(TIEMPO);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if(posY > limitY){
				ObjectManager.getInstance().removeFallingObject(this);
				onScreen = false;
			}
		}

	}


}
