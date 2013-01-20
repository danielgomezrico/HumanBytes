package com.makingiants.eatit.model.accelerometer;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Abstract the use of the acceleromether on a android device allowing an object
 * to recieve accel changes implementing @see(AccelerometerListener) object
 * 
 */
public class AccelerometerManager implements SensorEventListener {
	
	// ****************************************************************
	// Constants
	// ****************************************************************
	
	private static final int DELAY_ACCELEROMETHER = SensorManager.SENSOR_DELAY_GAME;
	
	// ****************************************************************
	// Attributes
	// ****************************************************************
	
	private final SensorManager mSensorManager;
	private final Sensor mAccelerometer;
	private IAccelerometerObserver listener;
	private boolean isRegistered;// If the AccelerometherManager has already
	                             // been registered to listen
	
	// ****************************************************************
	// Constructor
	// ****************************************************************
	
	/**
	 * 
	 * @param activity
	 *            must implement AccelerometerListener to listen
	 * @throws Exception
	 *             if activity does not implement AccelerometerListener
	 */
	public AccelerometerManager(final Context context) {
		
		mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		
		isRegistered = false;
	}
	
	// ****************************************************************
	// Subscriber-Publisher pattern methods
	// ****************************************************************
	
	public void subscribe(final IAccelerometerObserver observer) {
		listener = observer;
	}
	
	// ****************************************************************
	// SensorEventListener overrides
	// ****************************************************************
	
	@Override
	public void onAccuracyChanged(final Sensor arg0, final int arg1) {
		
	}
	
	@Override
	public void onSensorChanged(final SensorEvent arg0) {
		listener.changeAcceleromether(arg0.values[0], arg0.values[1]);
	}
	
	// ****************************************************************
	// Listen manager methods
	// ****************************************************************
	
	/**
	 * Start to listen the acceleromether events
	 */
	public void startListen() {
		if (!isRegistered) {
			mSensorManager.registerListener(this, mAccelerometer, DELAY_ACCELEROMETHER);
			isRegistered = true;
		}
	}
	
	/**
	 * Make the manager to stops listen the acceleromether changes
	 */
	public void stopListen() {
		mSensorManager.unregisterListener(this);
		isRegistered = false;
	}
	
}
