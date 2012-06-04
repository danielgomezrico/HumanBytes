package com.medialab.humanbytes.model.managers;

import java.util.ArrayList;
import java.util.Vector;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.medialab.humanbytes.R;
import com.medialab.humanbytes.model.gameObjects.FallingObject;
import com.medialab.humanbytes.model.gameObjects.Player;

public class ObjectManager {
	
	// ****************************************************************
	// Attributes
	// ****************************************************************
	
	private final static int DELAY_THREAD_UPDATE = 50;
	
	// ****************************************************************
	// Attributes
	// ****************************************************************
	
	private Vector<FallingObject> fallingObjects;
	private Player player;
	private int score = 0;
	
	private static ObjectManager instance;
	
	// ****************************************************************
	// Constructor
	// ****************************************************************
	
	private ObjectManager() {
		fallingObjects = new Vector<FallingObject>();
	}
	
	// ****************************************************************
	// Init methods
	// ****************************************************************
	
	public void initThreadNewFallingObjects() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				Activity activity = ActivityManager.getInstance().getActivity();
				
				int width = DisplayManager.getInstance().getWidth();
				
				Bitmap image = BitmapFactory.decodeResource(activity.getResources(), R.drawable.tuercabuena);
				int imageWidth = image.getWidth();
				
				while (true) {
					
					// Set the limits, left limit right limit
					float x = imageWidth / 2 + (float) (Math.random() * (width - imageWidth));
					
					addFallingObject(new FallingObject(x, 0, image));
					
					try {
						// Poner este tiempo como variable
						Thread.sleep(800);
					}
					catch (InterruptedException e) {
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
				
				boolean move = true;
				int height = DisplayManager.getInstance().getHeight();
				
				ArrayList<FallingObject> objectsToRemove = new ArrayList<FallingObject>();
				
				while (move) {
					
					synchronized (fallingObjects) {
						
						Rect playerRect = player.getRectangle();
						
						for (FallingObject fallingObject : fallingObjects) {
							fallingObject.move();
							
							// Check if is in the limit
							if (fallingObject.getPositionY() > height) {
								objectsToRemove.add(fallingObject);
							}
							// Check collitions
							else if (Rect.intersects(playerRect, fallingObject.getRectangle())) {
								// TODO: contar
								objectsToRemove.add(fallingObject);
								score++;
							}
							
						}
						
						// Remove objects to remove
						for (FallingObject fallingObject2 : objectsToRemove) {
							fallingObjects.remove(fallingObject2);
						}
						
					}
					
					// Cleaner the array of objects to remove
					objectsToRemove.clear();
					
					try {
						Thread.sleep(DELAY_THREAD_UPDATE);
					}
					catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
			}
			
		}).start();
	}
	
	// ****************************************************************
	// Accesor methods
	// ****************************************************************
	
	public static ObjectManager getInstance() {
		if (instance == null) {
			instance = new ObjectManager();
		}
		return instance;
	}
	
	// ****************************************************************
	// Player methods
	// ****************************************************************
	
	public void createPlayer(Player player) {
		this.player = player;
	}
	
	public void movePlayer(float offsetX) {
		player.moveX(offsetX);
	}
	
	// ****************************************************************
	// Falling objects methods
	// ****************************************************************
	
	public void addFallingObject(FallingObject obj) {
		synchronized (fallingObjects) {
			fallingObjects.add(obj);
		}
	}
	
	public void removeFallingObject(FallingObject obj) {
		synchronized (fallingObjects) {
			fallingObjects.remove(obj);
		}
	}
	
	// ****************************************************************
	// Draw methods
	// ****************************************************************
	
	public void drawObjects(Canvas canvas) {
		
		synchronized (fallingObjects) {
			
			for (FallingObject object : fallingObjects) {
				object.draw(canvas);
			}
		}
		
		player.draw(canvas);
	}
	
}
