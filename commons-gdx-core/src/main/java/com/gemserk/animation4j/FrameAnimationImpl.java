package com.gemserk.animation4j;

/**
 * An implementation of the state of an animation based on frames (without knowing nothing about graphics).
 */
public class FrameAnimationImpl implements FrameAnimation {

	/**
	 * Represents each frame time
	 */
	private int[] framesTimes;

	private int currentFrame;

	private int currentTime;

	// add all play/stop/pause/etc?

	private boolean loop;

	public void setLoop(boolean loop) {
		this.loop = loop;
	}

	public FrameAnimationImpl(int f0, int... framesTimes) {
		this.currentFrame = 0;
		this.currentTime = 0;
		this.loop = false;
		set(f0, framesTimes);
	}

	public FrameAnimationImpl(boolean loop, int... framesTimes) {
		// we could use the pattern 1+
		assert (framesTimes.length > 0);
		this.currentFrame = 0;
		this.currentTime = 0;
		this.loop = loop;
		set(framesTimes);
	}

	private void set(int f0, int... frames) {
		this.framesTimes = new int[frames.length + 1];
		this.framesTimes[0] = f0;
		for (int i = 1; i < frames.length + 1; i++)
			this.framesTimes[i] = frames[i - 1];
	}

	private void set(int... frames) {
		this.framesTimes = new int[frames.length];
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
	public void update(int delta) {
		currentTime += delta;
		int currentFrameTime = getCurrentFrameTime();
		if (currentTime >= currentFrameTime) {
			nextFrame();
			currentTime -= currentFrameTime;
		}
	}

	private int getCurrentFrameTime() {
		return framesTimes[getCurrentFrame()];
	}

	private void nextFrame() {

		if (currentFrame < getFramesCount() - 1)
			currentFrame++;
		else if (loop)
			currentFrame = 0;

	}
}
