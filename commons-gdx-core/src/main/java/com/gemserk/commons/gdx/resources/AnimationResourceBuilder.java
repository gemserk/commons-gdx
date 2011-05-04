package com.gemserk.commons.gdx.resources;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.gemserk.animation4j.FrameAnimationImpl;
import com.gemserk.animation4j.gdx.Animation;

public class AnimationResourceBuilder implements ResourceBuilder<Animation> {

	private int x;

	private int y;

	private int width;

	private int height;

	private int framesCount;

	private Texture spriteSheet;

	private boolean loop = false;

	private int[] times;

	public AnimationResourceBuilder loop(boolean loop) {
		this.loop = loop;
		return this;
	}

	public AnimationResourceBuilder times(int... times) {
		int lastTime = 0;
		for (int i = 0; i < this.times.length; i++) {
			if (i < times.length) {
				this.times[i] = times[i];
				lastTime = times[i];
			} else {
				this.times[i] = lastTime;
			}
		}
		return this;
	}

	public AnimationResourceBuilder(Texture spriteSheet, int x, int y, int frameWidth, int frameHeight, int framesCount) {
		this.spriteSheet = spriteSheet;
		this.x = x;
		this.y = y;
		this.width = frameWidth;
		this.height = frameHeight;
		this.framesCount = framesCount;
		this.times = new int[framesCount];
	}

	@Override
	public Animation build() {

		Sprite[] frames = new Sprite[framesCount];
		for (int i = 0; i < frames.length; i++) {
			frames[i] = new Sprite(spriteSheet, x + i * width, y, width, height);
		}

		// int[] newTimes = new int[framesCount - 1];
		// int lastTime = time;
		//
		// for (int i = 0; i < framesCount - 1; i++) {
		// if (i < times.length) {
		// newTimes[i] = times[i];
		// lastTime = times[i];
		// } else
		// newTimes[i] = lastTime;
		// }

		return new Animation(frames, new FrameAnimationImpl(loop, times));

	}

}