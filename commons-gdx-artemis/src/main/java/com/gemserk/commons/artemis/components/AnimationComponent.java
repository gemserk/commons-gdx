package com.gemserk.commons.artemis.components;

import com.artemis.Component;
import com.artemis.ComponentTypeManager;
import com.artemis.Entity;
import com.gemserk.animation4j.gdx.Animation;

public class AnimationComponent extends Component {

	public static final int type = ComponentTypeManager.getTypeFor(AnimationComponent.class).getId();

	public static AnimationComponent get(Entity e) {
		return (AnimationComponent) e.getComponent(type);
	}

	private Animation[] animations;

	private int currentAnimation;
	
	public int getAnimationCount() {
		return animations.length;
	}
	
	public Animation getAnimation(int index) {
		return animations[index];
	}
	
	public int getCurrentAnimationIndex() {
		return currentAnimation;
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
	
	public void setAnimations(Animation[] animations) {
		this.animations = animations;
	}

}