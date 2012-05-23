package com.gemserk.commons.utils;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.gemserk.animation4j.gdx.Animation;

public class AnimationUtils {
	
	public static void scaleAnimation(Animation animation, float scale) {
		for (int i = 0; i < animation.getFramesCount(); i++) {
			Sprite frame = animation.getFrame(i);
			float width = frame.getWidth();
			float height = frame.getHeight();
			frame.setSize(width * scale, height * scale);
		}
	}
	
	public static float framesToSeconds(int frame, int fps) {
		return (float) ((frame - 1) * fps) * 0.001f;
	}

	public static float framesToSeconds(int frame) {
		return framesToSeconds(frame, 30);
	}

	public static int secondsToFrame(float seconds, int fps) {
		return (int) (seconds * 1000) / fps;
	}

}
