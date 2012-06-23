package com.gemserk.commons.utils;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.gemserk.animation4j.gdx.Animation;

public class AnimationUtils {

	private final static int defaultPrecision = 1000;

	public static void scaleAnimation(Animation animation, float scale) {
		for (int i = 0; i < animation.getFramesCount(); i++) {
			Sprite frame = animation.getFrame(i);
			float width = frame.getWidth();
			float height = frame.getHeight();
			frame.setSize(width * scale, height * scale);
		}
	}

	public static float framesToSeconds(int frame, int fps) {
		return ((float) frame / fps);
	}

	public static float framesToSeconds(int frame) {
		return framesToSeconds(frame, 30);
	}

	public static int secondsToFrame(float seconds, int fps) {
		return (int) Math.floor(seconds * fps);
	}

	public static float truncate(float number) {
		return truncate(number, defaultPrecision);
	}
	
	public static float truncate(float number, int precision) {
		return (float) (Math.floor(number * precision + .5) / precision);
	}

}
