package com.gemserk.animation4j;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.gemserk.animation4j.gdx.Animation;
import com.gemserk.animation4j.gdx.AnimationFrameMonitor;

public class AnimationMonitorTest {

	@Test
	public void shouldNotBeShownWhenStarted() {
		Animation animation = new Animation(new Sprite[] {}, new FrameAnimationImpl(true, 50f, 25f, 30f));
		AnimationFrameMonitor animationMonitor = new AnimationFrameMonitor(animation, 50f);

		animation.update(1f);
		animationMonitor.update();

		assertFalse(animationMonitor.isTriggered());
	}

	@Test
	public void testWasShowWhenItShouldBe() {
		Animation animation = new Animation(new Sprite[] {}, new FrameAnimationImpl(true, 50f, 25f, 30f));
		AnimationFrameMonitor animationMonitor = new AnimationFrameMonitor(animation, 50f);

		animation.update(51f);
		animationMonitor.update();

		assertTrue(animationMonitor.isTriggered());
	}

	@Test
	public void shouldNotReturnTriggerTwice() {
		Animation animation = new Animation(new Sprite[] {}, new FrameAnimationImpl(true, 50f, 25f, 30f));
		AnimationFrameMonitor animationMonitor = new AnimationFrameMonitor(animation, 50f);

		animation.update(51f);
		animationMonitor.update();
		animationMonitor.update();

		assertFalse(animationMonitor.isTriggered());
	}

	@Test
	public void shouldTriggerAgainOnDifferentAnimationIteration() {
		Animation animation = new Animation(new Sprite[] {}, new FrameAnimationImpl(true, 50f, 25f, 30f));
		AnimationFrameMonitor animationMonitor = new AnimationFrameMonitor(animation, 50f);

		animation.update(51f);
		animationMonitor.update();
		animationMonitor.update();

		animation.update(25f + 30f + 50f);
		animationMonitor.update();

		assertTrue(animationMonitor.isTriggered());
	}

	@Test
	public void shouldNotTriggerAgainOnDifferentAnimationIterationButUpdatedTwice() {
		Animation animation = new Animation(new Sprite[] {}, new FrameAnimationImpl(true, 50f, 25f, 30f));
		AnimationFrameMonitor animationMonitor = new AnimationFrameMonitor(animation, 50f);

		animation.update(51f);
		animationMonitor.update();
		animationMonitor.update();

		animation.update(25f + 30f + 50f);
		animationMonitor.update();
		animationMonitor.update();

		assertFalse(animationMonitor.isTriggered());
	}

	@Test
	public void shouldNotTriggerIfNotFrameOnSecondIteration() {
		Animation animation = new Animation(new Sprite[] {}, new FrameAnimationImpl(true, 50f, 25f, 30f));
		AnimationFrameMonitor animationMonitor = new AnimationFrameMonitor(animation, 50f);

		animation.update(51f);
		animationMonitor.update();
		animationMonitor.update();

		animation.update(25f + 30f + 25f);
		animationMonitor.update();

		assertFalse(animationMonitor.isTriggered());
	}

	@Test
	public void shouldTriggerIfDirecltyChangedIteration() {
		Animation animation = new Animation(new Sprite[] {}, new FrameAnimationImpl(true, 50f, 25f, 30f));
		AnimationFrameMonitor animationMonitor = new AnimationFrameMonitor(animation, 50f);

		animation.update(50f + 25f + 31f);
		animationMonitor.update();

		assertTrue(animationMonitor.isTriggered());
	}

	@Test
	public void shouldHadBeenShownIfGreaterFrame() {
		Animation animation = new Animation(new Sprite[] {}, new FrameAnimationImpl(true, 50f, 25f, 30f));
		AnimationFrameMonitor animationMonitor = new AnimationFrameMonitor(animation, 50f);

		animation.update(76f);
		animationMonitor.update();

		assertTrue(animationMonitor.isTriggered());
	}

}
