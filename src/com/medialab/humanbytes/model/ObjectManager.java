package com.medialab.humanbytes.model;

import java.util.Vector;

import android.graphics.Canvas;

public class ObjectManager {

	private Vector<FallingObject> fallingObjects;
	private Player player;

	private static ObjectManager instance;

	private ObjectManager(){
		fallingObjects = new Vector<FallingObject>();
	}

	public static ObjectManager getInstance(){
		if(instance == null){
			instance = new ObjectManager();
		}
		return instance;
	}

	public void createPlayer(Player player){
		this.player = player;
	}
	
	public void movePlayer(float offsetX){
		player.moveX(offsetX);
	}

	public void addFallingObject( FallingObject obj){
		synchronized (fallingObjects) {
			fallingObjects.add(obj);
		}
	}

	public void removeFallingObject( FallingObject obj){
		synchronized (fallingObjects) {
			fallingObjects.remove(obj);
		}
	}

	public void drawObjects(Canvas canvas){
		synchronized (fallingObjects) {
			for (FallingObject object : fallingObjects) {
				object.draw(canvas);
			}
		}
		
		player.draw(canvas);
	}
	
	


}
