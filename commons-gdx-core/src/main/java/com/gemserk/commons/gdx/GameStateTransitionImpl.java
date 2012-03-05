package com.gemserk.commons.gdx;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.gemserk.animation4j.transitions.TimeTransition;
import com.gemserk.commons.gdx.graphics.ImmediateModeRendererUtils;

/**
 * Implements a transition between game states given a list of effects to be used.
 * 
 * @author acoppes
 * 
 */
public class GameStateTransitionImpl extends GameStateImpl {

	public static class TransitionEffect {

		private TimeTransition timeTransition;

		protected float getAlpha() {
			return timeTransition.get();
		}

		public TransitionEffect(float duration) {
			timeTransition = new TimeTransition();
			timeTransition.start(duration);
		}

		public void update(float delta) {
			timeTransition.update(delta);
		}

		/**
		 * @param current
		 *            The current game state.
		 * @param next
		 *            The next game state.
		 */
		public void render(GameState current, GameState next) {

		}

		public boolean isFinished() {
			return timeTransition.isFinished();
		}

	}

	public static class FadeOutTransitionEffect extends TransitionEffect {

		Color color = new Color();

		public FadeOutTransitionEffect(float duration) {
			super(duration);
		}

		@Override
		public void render(GameState current, GameState next) {
			current.render();
			color.set(0f, 0f, 0f, getAlpha());

			Gdx.gl10.glEnable(GL10.GL_BLEND);
			ImmediateModeRendererUtils.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			ImmediateModeRendererUtils.fillRectangle(0f, 0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), color);
			Gdx.gl10.glDisable(GL10.GL_BLEND);
		}

	}

	public static class FadeInTransitionEffect extends TransitionEffect {

		Color color = new Color();

		public FadeInTransitionEffect(float duration) {
			super(duration);
		}

		@Override
		public void render(GameState current, GameState next) {
			next.render();
			color.set(0f, 0f, 0f, 1f - getAlpha());

			Gdx.gl10.glEnable(GL10.GL_BLEND);
			ImmediateModeRendererUtils.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			ImmediateModeRendererUtils.fillRectangle(0f, 0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), color);
			Gdx.gl10.glDisable(GL10.GL_BLEND);
		}

	}

	GameState current;
	GameState next;

	int currentTransitionEffect;
	ArrayList<TransitionEffect> transitionEffects;

	public GameStateTransitionImpl(GameState current, GameState next, ArrayList<TransitionEffect> transitionEffects) {
		this.current = current;
		this.next = next;
		this.transitionEffects = transitionEffects;
		this.currentTransitionEffect = 0;
	}

	@Override
	public void init() {

	}

	@Override
	public void update() {

		if (currentTransitionEffect >= transitionEffects.size()) {
			onTransitionFinished();
			return;
		}

		transitionEffects.get(currentTransitionEffect).update(getDelta());
		if (transitionEffects.get(currentTransitionEffect).isFinished())
			currentTransitionEffect++;

	}

	protected void onTransitionFinished() {

	}

	@Override
	public void render() {
		if (currentTransitionEffect >= transitionEffects.size())
			return;
		transitionEffects.get(currentTransitionEffect).render(current, next);
	}

	@Override
	public void dispose() {

	}

}
