package com.gemserk.commons.gdx;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
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

		final Color transparentColor = new Color(0f, 0f, 0f, 0f);
		final Color blackColor = new Color(0f, 0f, 0f, 1f);

		ApplicationListenerGameStateBasedImpl applicationListenerGameStateBasedImpl;
		GameState current;
		GameState next;

		float leaveTime = 1f;
		float enterTime = 1f;

		boolean disposeCurrent = false;
		boolean restartNext = false;

		public GameStateTransitionBuilder(ApplicationListenerGameStateBasedImpl applicationListenerGameStateBasedImpl, GameState current, GameState next) {
			this.applicationListenerGameStateBasedImpl = applicationListenerGameStateBasedImpl;
			this.current = current;
			this.next = next;
		}

		public GameStateTransitionBuilder leaveTime(float leaveTime) {
			this.leaveTime = leaveTime;
			return this;
		}

		public GameStateTransitionBuilder enterTime(float enterTime) {
			this.enterTime = enterTime;
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

			// FadeOutInTransitionEffect transitionEffect = new FadeOutInTransitionEffect();

			if (restartNext)
				next.dispose();

			next.init();
			next.resume();
			next.show();

			TransitionEffect fadeOutTransitionEffect = new FadeOutTransitionEffect(leaveTime);
			TransitionEffect fadeInTransitionEffect = new FadeInTransitionEffect(enterTime);

			final GameState transitionGameState = new GameStateTransitionImpl(current, next, fadeOutTransitionEffect, fadeInTransitionEffect) {
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