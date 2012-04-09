package com.gemserk.animation4j.gdx;

/**
 * Monitors the animation to know when the animation shown an specific frame, to be used to trigger stuff.
 */
public class AnimationFrameMonitor {

	int frameToMonitor, currentIteration;

	boolean triggered, alreadyTriggeredForIteration;
	Animation animation;

	public AnimationFrameMonitor(Animation animation, int frame) {
		this.animation = animation;
		this.frameToMonitor = frame;
		this.triggered = animation.getCurrentFrameIndex() >= frame;
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

		triggered = animation.getCurrentFrameIndex() >= frameToMonitor;
		alreadyTriggeredForIteration = triggered;
	}

	private boolean isIterationChanged() {
		return this.currentIteration != animation.getIteration();
	}

	public boolean isTriggered() {
		return triggered;
	}

}