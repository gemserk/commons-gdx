package com.gemserk.commons.gdx;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.gemserk.commons.gdx.GameStateTransitionImpl.FadeInTransitionEffect;
import com.gemserk.commons.gdx.GameStateTransitionImpl.FadeOutTransitionEffect;
import com.gemserk.commons.gdx.GameStateTransitionImpl.TransitionEffect;

/**
 * Implementation of ApplicationListener based different game states using the GameState class.
 */
public class ApplicationListenerGameStateBasedImpl implements ApplicationListener {

	protected GameState gameState;
	protected boolean transitioning;

	public GameState getGameState() {
		return gameState;
	}

	@Override
	public void dispose() {
		if (gameState == null)
			return;
		gameState.hide();
		gameState.pause();
		gameState.dispose();
	}

	@Override
	public void pause() {
		if (gameState != null)
			gameState.pause();
	}

	@Override
	public void resume() {
		if (gameState != null)
			gameState.resume();
	}

	@Override
	public void render() {
		if (gameState == null)
			return;
		// should set the global time

		float delta = Gdx.graphics.getDeltaTime();
		float alpha = 1f;

		GlobalTime.setDelta(delta);
		GlobalTime.setAlpha(alpha);

		gameState.setDelta(delta);
		gameState.setAlpha(alpha);

		gameState.update();
		gameState.render();
	}

	@Override
	public void resize(int width, int height) {
		if (gameState != null)
			gameState.resize(width, height);
	}

	public void setGameState(GameState gameState) {
		setGameState(gameState, false);
	}

	public void setGameState(GameState gameState, boolean disposeCurrent) {
		if (gameState == null)
			throw new IllegalArgumentException("Can't set null GameState");

		GameState currentGameState = getGameState();

		if (currentGameState == gameState)
			return;

		if (currentGameState != null) {
			currentGameState.pause();
			currentGameState.hide();
			if (disposeCurrent)
				currentGameState.dispose();
		}

		this.gameState = gameState;
		gameState.init();
		gameState.resume();
		gameState.show();
	}

	public void setGameStateAsync(final GameState gameState, final boolean disposeCurrent) {
		Gdx.app.postRunnable(new Runnable() {
			@Override
			public void run() {
				setGameState(gameState, disposeCurrent);
			}
		});
	}

	public GameStateTransitionBuilder transition(GameState next) {
		return new GameStateTransitionBuilder(this, gameState, next);
	}

	@Override
	public void create() {

	}

	public class GameStateTransitionBuilder {

		ApplicationListenerGameStateBasedImpl applicationListenerGameStateBasedImpl;

		GameState current;
		GameState next;

		boolean disposeCurrent = false;
		boolean restartNext = false;

		ArrayList<TransitionEffect> transitionEffects;

		public GameStateTransitionBuilder(ApplicationListenerGameStateBasedImpl applicationListenerGameStateBasedImpl, GameState current, GameState next) {
			this.applicationListenerGameStateBasedImpl = applicationListenerGameStateBasedImpl;
			this.current = current;
			this.next = next;
			this.transitionEffects = new ArrayList<TransitionEffect>();
			// we add by default the null transition effect.
			this.transitionEffects.add(new TransitionEffect(0f));
		}

		/**
		 * Adds a new fade out effect to the transition effects list using the specified duration.
		 * 
		 * @param duration
		 *            The duration of the fade out effect.
		 */
		public GameStateTransitionBuilder fadeOut(float duration) {
			this.transitionEffects.add(new FadeOutTransitionEffect(duration));
			return this;
		}

		/**
		 * Adds a new fade in effect to the transition effects list using the specified duration.
		 * 
		 * @param duration
		 *            The duration of the fade in effect.
		 */
		public GameStateTransitionBuilder fadeIn(float duration) {
			this.transitionEffects.add(new FadeInTransitionEffect(duration));
			return this;
		}

		public GameStateTransitionBuilder customEffect(TransitionEffect transitionEffect) {
			this.transitionEffects.add(transitionEffect);
			return this;
		}

		public GameStateTransitionBuilder disposeCurrent(boolean disposeCurrent) {
			this.disposeCurrent = disposeCurrent;
			return this;
		}

		public GameStateTransitionBuilder restartNext(boolean restartNext) {
			this.restartNext = restartNext;
			return this;
		}

		public void start() {
			if (transitioning)
				return;

			transitioning = true;

			if (restartNext)
				next.dispose();

			next.init();
			next.resume();
			next.show();

			final GameState transitionGameState = new GameStateTransitionImpl(current, next, transitionEffects) {
				@Override
				protected void onTransitionFinished() {
					current.pause();
					current.hide();

					if (disposeCurrent)
						current.dispose();

					setGameStateAsync(next, true);
					transitioning = false;
				}

			};

			Gdx.app.postRunnable(new Runnable() {
				@Override
				public void run() {
					gameState = transitionGameState;
					gameState.init();
					gameState.resume();
					gameState.show();
				}
			});

		}

	}

}