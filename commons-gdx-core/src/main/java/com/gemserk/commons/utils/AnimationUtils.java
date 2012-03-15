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

}
