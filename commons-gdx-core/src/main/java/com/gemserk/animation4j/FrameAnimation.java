package com.gemserk.animation4j;

/**
 * Provides an abstraction of the state of an animation based on frames (without knowing nothing about graphics).
 */
public interface FrameAnimation {

	/**
	 * Returns the current frame number.
	 */
	int getCurrentFrame();

	/**
	 * Internally updates the animation, changing frames.
	 * 
	 * @param delta
	 *            The time to update in seconds.
	 */
	void update(float delta);

	/**
	 * Restarts the animation.
	 */
	void restart();

	/**
	 * Returns true if the animation is finished.
	 */
	boolean isFinished();

}