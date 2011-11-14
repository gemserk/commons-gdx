package com.gemserk.animation4j;

/**
 * An implementation of the state of an animation based on frames (without knowing nothing about graphics).
 */
public class FrameAnimationImpl implements FrameAnimation {

	/**
	 * Represents each frame time
	 */
	private float[] framesTimes;
	
	private int currentFrame;
	private float currentTime;

	// add all play/stop/pause/etc?

	private boolean loop;

	public void setLoop(boolean loop) {
		this.loop = loop;
	}

	public FrameAnimationImpl(float f0, float... framesTimes) {
		this.currentFrame = 0;
		this.currentTime = 0;
		this.loop = false;
		set(f0, framesTimes);
	}

	public FrameAnimationImpl(boolean loop, float... framesTimes) {
		// we could use the pattern 1+
		assert (framesTimes.length > 0);
		this.currentFrame = 0;
		this.currentTime = 0;
		this.loop = loop;
		set(framesTimes);
	}

	private void set(float f0, float... frames) {
		this.framesTimes = new float[frames.length + 1];
		this.framesTimes[0] = f0;
		for (int i = 1; i < frames.length + 1; i++)
			this.framesTimes[i] = frames[i - 1];
	}

	private void set(float... frames) {
		this.framesTimes = new float[frames.length];
		for (int i = 0; i < frames.length; i++)
			this.framesTimes[i] = frames[i];
	}

	public int getFramesCount() {
		return framesTimes.length;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gemserk.games.taken.FrameAnimation#getCurrentFrame()
	 */
	@Override
	public int getCurrentFrame() {
		return currentFrame;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gemserk.games.taken.FrameAnimation#update(int)
	 */
	@Override
	public void update(float delta) {
		currentTime += delta;
		float currentFrameTime = getCurrentFrameTime();
		while (currentTime >= currentFrameTime && !isFinished()) {
			nextFrame();
			currentTime -= currentFrameTime;
			currentFrameTime = getCurrentFrameTime();
		}
	}

	private float getCurrentFrameTime() {
		return framesTimes[currentFrame];
	}

	private void nextFrame() {

		if (currentFrame < getFramesCount() - 1)
			currentFrame++;
		else if (loop)
			currentFrame = 0;

	}

	@Override
	public boolean isFinished() {
		if (loop)
			return false;
		if (currentFrame != getFramesCount() - 1)
			return false;
		return currentTime >= getCurrentFrameTime();
	}

	@Override
	public void restart() {
		currentFrame = 0;
		currentTime = 0;
	}
}
