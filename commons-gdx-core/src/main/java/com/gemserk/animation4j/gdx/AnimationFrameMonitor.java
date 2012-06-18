package com.gemserk.animation4j.gdx;

import com.gemserk.commons.utils.AnimationUtils;

/**
 * Monitors the animation to know when the animation shown an specific frame, to be used to trigger stuff.
 */
public class AnimationFrameMonitor {

	int currentIteration;
	float timeToMonitor;

	boolean triggered, alreadyTriggeredForIteration;
	Animation animation;

	public void setFrameToMonitor(int frameToMonitor) {
		this.timeToMonitor = AnimationUtils.framesToSeconds(frameToMonitor, 30);
		reset();
	}

	public AnimationFrameMonitor(Animation animation, float timeToMonitor) {
		this.animation = animation;
		this.timeToMonitor = timeToMonitor;
		this.triggered = animation.getCurrentTime() >= timeToMonitor;
		this.alreadyTriggeredForIteration = false;
		this.currentIteration = animation.getIteration();
	}

	public void update() {

		if (isIterationChanged()) {
			this.currentIteration = animation.getIteration();
			if (!alreadyTriggeredForIteration) {
				triggered = true;
				this.alreadyTriggeredForIteration = false;
				return;
			}
			this.alreadyTriggeredForIteration = false;
		}

		if (alreadyTriggeredForIteration) {
			triggered = false;
			return;
		}

		float currentTime = animation.getCurrentTime();
		triggered = currentTime >= timeToMonitor;
		alreadyTriggeredForIteration = triggered;
	}

	private boolean isIterationChanged() {
		return this.currentIteration != animation.getIteration();
	}

	public boolean isTriggered() {
		return triggered;
	}

	public void reset() {
		this.triggered = animation.getCurrentTime() >= timeToMonitor;
		this.alreadyTriggeredForIteration = false;
		this.currentIteration = animation.getIteration();
	}

}