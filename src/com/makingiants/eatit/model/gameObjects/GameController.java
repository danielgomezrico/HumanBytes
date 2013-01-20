package com.makingiants.eatit.model.gameObjects;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.media.MediaPlayer;

import com.makingiants.eatit.R;
import com.makingiants.eatit.model.managers.ActivityManager;
import com.makingiants.eatit.model.managers.DisplayManager;

public class GameController {
	
	// ****************************************************************
	// Attributes
	// ****************************************************************
	
	private final static int DELAY_THREAD_UPDATE = 50;
	private final static int DELAY_THREAD_NEW_FALLING_OBJECT = 2000;
	private final static int DELAY_THREAD_NEW_FALLING_OBJECT_MIN = 300;
	
	// ****************************************************************
	// Attributes
	// ****************************************************************
	
	private final Vector<FallingObject> fallingObjects;
	private Player player;
	private int score;
	
	private boolean threadsCanRun = true;
	
	private MediaPlayer hitPlayerSound;
	private MediaPlayer pickPlayerSound;
	
	// ****************************************************************
	// Constructor
	// ****************************************************************
	
	public GameController(Player player) {
		
		fallingObjects = new Vector<FallingObject>();
		
		this.score = 0;
		
		this.player = player;
		
	}
	
	// ****************************************************************
	// Init methods
	// ****************************************************************
	
	public void initThreadNewFallingObjects() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				final Activity activity = ActivityManager.getInstance().getActivity();
				
				final int width = DisplayManager.getInstance().getWidth();
				
				Bitmap[] imagesGood = {
				        BitmapFactory.decodeResource(activity.getResources(),
				                R.drawable.object_good_apple),
				        BitmapFactory.decodeResource(activity.getResources(),
				                R.drawable.object_good_banana),
				        BitmapFactory.decodeResource(activity.getResources(),
				                R.drawable.object_good_carrot),
				        BitmapFactory.decodeResource(activity.getResources(),
				                R.drawable.object_good_tomato),
				        BitmapFactory.decodeResource(activity.getResources(),
				                R.drawable.object_good_watermelon), };
				
				Bitmap[] imagesBad = { BitmapFactory.decodeResource(activity.getResources(),
				        R.drawable.object_bad_rat) };
				
				final Random random = new Random();
				int delayTemp = DELAY_THREAD_NEW_FALLING_OBJECT;
				int imageWidth = 0;
				float x = 0;
				
				while (threadsCanRun) {
					
					// Set the limits, left limit right limit
					if (random.nextInt(4) == 1) { // it is a bad object 
						Bitmap imageBad = imagesBad[random.nextInt(imagesBad.length)];
						imageWidth = imageBad.getWidth();
						x = imageWidth / 2 + (float) (Math.random() * (width - imageWidth));
						
						addFallingObject(new FallingObject(x, 0, imageBad, false));
					} else {
						Bitmap imageGood = imagesGood[random.nextInt(imagesGood.length)];
						imageWidth = imageGood.getWidth();
						x = imageWidth / 2 + (float) (Math.random() * (width - imageWidth));
						
						addFallingObject(new FallingObject(x, 0, imageGood, true));
					}
					
					try {
						// Poner este tiempo como variable
						if (delayTemp > DELAY_THREAD_NEW_FALLING_OBJECT_MIN) {
							delayTemp = DELAY_THREAD_NEW_FALLING_OBJECT - score * 20;
						}
						
						Thread.sleep(delayTemp);
					} catch (final InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
			
		}).start();
	}
	
	public void initThreadUpdateObjects() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				final int height = DisplayManager.getInstance().getHeight();
				
				final ArrayList<FallingObject> objectsToRemove = new ArrayList<FallingObject>();
				
				while (threadsCanRun) {
					
					synchronized (fallingObjects) {
						
						final Rect playerRect = player.getRectangle();
						
						for (final FallingObject fallingObject : fallingObjects) {
							fallingObject.move();
							
							// Check if is in the limit
							if (fallingObject.getPositionY() > height) {
								objectsToRemove.add(fallingObject);
							}
							// Check collitions
							else if (Rect.intersects(playerRect, fallingObject.getRectangle())) {
								
								objectsToRemove.add(fallingObject);
								
								if (fallingObject.isGood()) {
									
									score++;
									
									player.plusScore();
									
									pickPlayerSound = MediaPlayer.create(ActivityManager.getInstance()
									        .getActivity(), R.raw.object_pick);
									pickPlayerSound.start();
									
								} else {
									
									player.restLife();
									
									hitPlayerSound = MediaPlayer.create(ActivityManager.getInstance()
									        .getActivity(), R.raw.hit);
									hitPlayerSound.start();
									//VibrationHelper.vibrate(300);
									
								}
								
								if (player.getLifes() == 0) {
									manageDead();
								}
							}
							
						}
						
						// Remove objects to remove
						for (final FallingObject fallingObject2 : objectsToRemove) {
							fallingObjects.remove(fallingObject2);
						}
						
					}
					
					// Cleaner the array of objects to remove
					objectsToRemove.clear();
					
					try {
						Thread.sleep(DELAY_THREAD_UPDATE);
					} catch (final InterruptedException e) {
						e.printStackTrace();
					}
				}
				
			}
			
		}).start();
	}
	
	public void stopThreads(){
		threadsCanRun = false;
	}
	
	// ****************************************************************
	// Player methods
	// ****************************************************************
	
	public void movePlayer(final float offsetX) {
		player.moveX(offsetX);
	}
	
	private void manageDead() {
		
		threadsCanRun = false;
		
		MediaPlayer.create(ActivityManager.getInstance().getActivity(), R.raw.die).start();
		
		ActivityManager.getInstance().finish();
		
	}
	
	// ****************************************************************
	// Falling objects methods
	// ****************************************************************
	
	public void addFallingObject(final FallingObject obj) {
		synchronized (fallingObjects) {
			fallingObjects.add(obj);
		}
	}
	
	public void removeFallingObject(final FallingObject obj) {
		synchronized (fallingObjects) {
			fallingObjects.remove(obj);
		}
	}
	
	// ****************************************************************
	// Accesor methods
	// ****************************************************************
	
	public int getScore() {
		return score;
	}
	
	public int getLifes() {
		return player.getLifes();
	}
	
	// ****************************************************************
	// Draw methods
	// ****************************************************************
	public void drawObjects(final Canvas canvas) {
		
		player.draw(canvas);
		
		synchronized (fallingObjects) {
			
			for (final FallingObject object : fallingObjects) {
				object.draw(canvas);
			}
		}
		
	}
	
}
