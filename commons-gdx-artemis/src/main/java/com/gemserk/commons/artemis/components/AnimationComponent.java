package com.gemserk.commons.artemis.components;

import com.artemis.Component;
import com.gemserk.animation4j.gdx.Animation;

public class AnimationComponent extends Component {

	private final Animation[] animations;

	private int currentAnimation;
	
	public int getAnimationCount() {
		return animations.length;
	}
	
	public Animation getAnimation(int index) {
		return animations[index];
	}

	public Animation getCurrentAnimation() {
		return animations[currentAnimation];
	}

	public void setCurrentAnimation(int currentAnimation) {
		this.currentAnimation = currentAnimation;
	}

	public AnimationComponent(Animation[] spriteSheets) {
		this.animations = spriteSheets;
	}

}