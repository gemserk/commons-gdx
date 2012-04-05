package com.gemserk.animation4j;

/**
 * An implementation of the state of an animation based on frames (without knowing nothing about graphics).
 */
public class FrameAnimationImpl implements FrameAnimation {

	/**
	 * Represents each frame time
	 */
	float[] framesTimes;

	int currentFrame;
	float currentTime;

	// add all play/stop/pause/etc?

	boolean loop;

	public void setLoop(boolean loop) {
		this.loop = loop;
	}

	public FrameAnimationImpl(FrameAnimationImpl frameAnimationImpl) {
		this.framesTimes = new float[frameAnimationImpl.framesTimes.length];
		System.arraycopy(frameAnimationImpl.framesTimes, 0, this.framesTimes, 0, frameAnimationImpl.framesTimes.length);
		this.currentFrame = frameAnimationImpl.currentFrame;
		this.currentTime = frameAnimationImpl.currentTime;
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

	public FrameAnimationImpl clone() {
		return new FrameAnimationImpl(this);
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

	@Override
	public void setFrame(int frame) {
		if (currentFrame < 0 || currentFrame >= framesTimes.length)
			throw new IllegalStateException("Frame animation with index " + frame + " not found.");
		currentFrame = frame;
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
