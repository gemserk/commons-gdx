package com.gemserk.animation4j.animations.events;

import com.gemserk.animation4j.animations.AnimationManager;

public class AutoRemoveAnimationHandler extends AnimationEventHandler {

	private final AnimationManager animationManager;

	public AutoRemoveAnimationHandler(AnimationManager animationManager) {
		this.animationManager = animationManager;
	}

	@Override
	public void onAnimationFinished(AnimationEvent e) {
		animationManager.remove(e.getAnimation());
	}
	
}