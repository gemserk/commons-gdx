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
	 * @return Returns the total number of frames. 
	 */
	int getFramesCount();
	
	/**
	 * Returns the iteration number of the animation (how mane times it was restarted).
	 */
	int getIteration();
	
	/**
	 * Returns the current time of the animation.
	 */
	float getCurrentTime();
	
	/**
	 * Returns the duration of the animation.
	 */
	float getDuration();

	/**
	 * Sets the current frame.
	 */
	void setFrame(int frame);

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