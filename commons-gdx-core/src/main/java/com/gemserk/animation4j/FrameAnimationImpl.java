package com.gemserk.animation4j;


/**
 * An implementation of the state of an animation based on frames (without knowing nothing about graphics).
 */
public class FrameAnimationImpl implements FrameAnimation {

	private final int[] framesTimes;

	private int currentFrame;

	private int currentTime;

	// add all play/stop/pause/etc?

	private boolean loop;

	public FrameAnimationImpl(int[] framesTimes) {
		this(framesTimes, false);
	}
	
	public FrameAnimationImpl(int[] framesTimes, boolean loop) {
		this.framesTimes = framesTimes;
		this.currentFrame = 0;
		this.currentTime = 0;
		this.loop = loop;
	}

	public FrameAnimationImpl(int frameTime, int frameCount) {
		this(frameTime, frameCount, false);
	}

	public FrameAnimationImpl(int frameTime, int frameCount, boolean loop) {
		framesTimes = new int[frameCount];
		for (int i = 0; i < framesTimes.length; i++) {
			framesTimes[i] = frameTime;
		}
		this.currentFrame = 0;
		this.currentTime = 0;
		this.loop = loop;
	}

	public int getFramesCount() {
		return framesTimes.length;
	}

	/* (non-Javadoc)
	 * @see com.gemserk.games.taken.FrameAnimation#getCurrentFrame()
	 */
	@Override
	public int getCurrentFrame() {
		return currentFrame;
	}

	/* (non-Javadoc)
	 * @see com.gemserk.games.taken.FrameAnimation#update(int)
	 */
	@Override
	public void update(int delta) {
		currentTime += delta;
		int currentFrameTime = getCurrentFrameTime();
		if (currentTime > currentFrameTime) {
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
