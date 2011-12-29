package com.gemserk.commons.gdx;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;

/**
 * Implementation of ApplicationListener based different game states using the GameState class.
 */
public class ApplicationListenerGameStateBasedImpl implements ApplicationListener {

	protected GameState gameState;

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

	@Override
	public void create() {

	}

}