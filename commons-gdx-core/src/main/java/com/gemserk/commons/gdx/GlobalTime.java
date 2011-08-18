package com.gemserk.commons.gdx;

public class GlobalTime {
	
	private static float delta;
	
	/**
	 * Returns delta time in seconds.
	 */
	public static float getDelta() {
		return delta;
	}
	
	/**
	 * Returns delta in milliseconds.
	 */
	public static float getDeltaInMs() { 
		return delta * 1000f;
	}

	/**
	 * Sets delta time in seconds.
	 */
	public static void setDelta(float delta) {
		GlobalTime.delta = delta;
	}

}
