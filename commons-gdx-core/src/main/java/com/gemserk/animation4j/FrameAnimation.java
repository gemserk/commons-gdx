package com.gemserk.animation4j;

/**
 * Provides an abstraction of the state of an animation based on frames (without knowing nothing about graphics).
 */
public interface FrameAnimation {

	int getCurrentFrame();

	void update(float delta);

}