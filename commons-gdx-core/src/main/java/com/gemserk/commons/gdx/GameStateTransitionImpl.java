package com.gemserk.commons.gdx;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.utils.Disposable;
import com.gemserk.animation4j.transitions.TimeTransition;
import com.gemserk.commons.gdx.graphics.ImmediateModeRendererUtils;

/**
 * Implements a transition between game states given a list of effects to be used.
 * 
 * @author acoppes
 * 
 */
public class GameStateTransitionImpl extends GameStateImpl {

	public static class TransitionEffect implements Disposable {

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

		@Override
		public void dispose() {

		}

	}

	public static class FadeOutTransitionEffect extends TransitionEffect {

		Color color = new Color();

		public FadeOutTransitionEffect(float duration) {
			super(duration);
		}

		public FadeOutTransitionEffect(float duration, Color color) {
			this(duration);
			this.color.set(color);
		}

		@Override
		public void render(GameState current, GameState next) {
			current.render();
			color.a = getAlpha();

			Gdx.gl.glEnable(GL10.GL_BLEND);
			ImmediateModeRendererUtils.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			ImmediateModeRendererUtils.fillRectangle(0f, 0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), color);
			Gdx.gl.glDisable(GL10.GL_BLEND);
		}

	}

	public static class FadeInTransitionEffect extends TransitionEffect {

		Color color = new Color();

		public FadeInTransitionEffect(float duration) {
			super(duration);
		}

		public FadeInTransitionEffect(float duration, Color color) {
			this(duration);
			this.color.set(color);
		}

		@Override
		public void render(GameState current, GameState next) {
			next.render();
			color.a = 1f - getAlpha();

			Gdx.gl.glEnable(GL10.GL_BLEND);
			ImmediateModeRendererUtils.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			ImmediateModeRendererUtils.fillRectangle(0f, 0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), color);
			Gdx.gl.glDisable(GL10.GL_BLEND);
		}

	}
	
	public static class RenderNextTransitionEffect extends TransitionEffect {

		
		public RenderNextTransitionEffect(float duration) {
			super(duration);
		}

		@Override
		public void render(GameState current, GameState next) {
			next.render();
		}
	}

	GameState current;
	GameState next;

	int currentTransitionEffectIndex;
	ArrayList<TransitionEffect> transitionEffects;

	public GameStateTransitionImpl(GameState current, GameState next, ArrayList<TransitionEffect> transitionEffects) {
		this.current = current;
		this.next = next;
		this.transitionEffects = transitionEffects;
		if(transitionEffects.isEmpty()){
			transitionEffects.add(new RenderNextTransitionEffect(0));
		}
		this.currentTransitionEffectIndex = 0;
	}

	@Override
	public void init() {

	}

	@Override
	public void update() {

		TransitionEffect currentTransitionEffect = transitionEffects.get(currentTransitionEffectIndex);

		currentTransitionEffect.update(getDelta());
		if (currentTransitionEffect.isFinished()) {
			currentTransitionEffect.dispose();
			currentTransitionEffectIndex++;
		}

		if (currentTransitionEffectIndex >= transitionEffects.size()) {
			onTransitionFinished();
			return;
		}
	}

	protected void onTransitionFinished() {

	}

	@Override
	public void render() {
		int effectToRenderIndex = currentTransitionEffectIndex;
		if (currentTransitionEffectIndex >= transitionEffects.size())
			effectToRenderIndex = transitionEffects.size() - 1;
		transitionEffects.get(effectToRenderIndex).render(current, next);
	}

	@Override
	public void dispose() {
		for (int i = 0; i < transitionEffects.size(); i++) {
			transitionEffects.get(i).dispose();
		}
	}

}
