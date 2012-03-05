package com.gemserk.commons.gdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.gemserk.animation4j.transitions.TimeTransition;
import com.gemserk.commons.gdx.graphics.ImmediateModeRendererUtils;

public class GameStateTransitionImpl extends GameStateImpl {

	public static interface TransitionEffect {

		/**
		 * @param current
		 *            The current game state.
		 * @param next
		 *            The next game state.
		 * @param alpha
		 *            A value from 0 to 1 representing the interpolation between one state and the other.
		 */
		void render(GameState current, GameState next, float alpha);

	}

	public static class FadeOutInTransitionEffect implements TransitionEffect {

		Color color = new Color();

		@Override
		public void render(GameState current, GameState next, float alpha) {

			if (alpha < 0.5f) {
				current.render();
				color.set(0f, 0f, 0f, alpha * 2f);
			} else {
				next.render();
				color.set(0f, 0f, 0f, (1f - alpha) * 2f);
			}

			if (Gdx.graphics.getGL10() != null) {
				Gdx.gl10.glEnable(GL10.GL_BLEND);
				ImmediateModeRendererUtils.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
				ImmediateModeRendererUtils.fillRectangle(0f, 0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), color);
				Gdx.gl10.glDisable(GL10.GL_BLEND);
			}
			
		}

	}

	GameState current;
	GameState next;

	TransitionEffect transitionEffect;

	TimeTransition timeTransition;

	public GameStateTransitionImpl(GameState current, GameState next, float duration, TransitionEffect transitionEffect) {
		this.current = current;
		this.next = next;
		this.transitionEffect = transitionEffect;
		this.timeTransition = new TimeTransition();
		this.timeTransition.start(duration);
	}

	@Override
	public void init() {

	}

	@Override
	public void update() {
		timeTransition.update(getDelta());
		if (timeTransition.isFinished())
			onTransitionFinished();
	}

	protected void onTransitionFinished() {

	}

	@Override
	public void render() {
		transitionEffect.render(current, next, timeTransition.get());
	}

	@Override
	public void dispose() {

	}

}
