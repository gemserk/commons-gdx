package com.gemserk.commons.gdx;

public class GlobalTime {

	private static long frame = 0;
	private static float delta;
	private static float alpha;

	/**
	 * Sets the current frame of the game.
	 */
	public static void setFrame(long frame) {
		GlobalTime.frame = frame;
	}

	/**
	 * Returns the current frame of the game.
	 */
	public static long getFrame() {
		return frame;
	}

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

	/**
	 * Sets the alpha value, between 0 and 1, to be used to interpolate states when rendering.
	 */
	public static void setAlpha(float alpha) {
		GlobalTime.alpha = alpha;
	}

	/**
	 * Returns alpha, a value between 0 and 1.
	 */
	public static float getAlpha() {
		return alpha;
	}
}
