package com.gemserk.commons.gdx;

import com.gemserk.componentsengine.utils.Parameters;

/**
 * Provides a delegate implementation of GameState with a fixed timestep, based on <a href="http://gafferongames.com/game-physics/fix-your-timestep/">fix your timestep</a>.
 * 
 */
public class GameStateDelegateFixedTimestepImpl implements GameState {

	float delta;

	float dt;
	float maxFrameTime;

	float accumulator;

	GameState gameState;
	
	public GameStateDelegateFixedTimestepImpl(GameState gameState) {
		this(gameState, 0.01f, 0.25f);
	}

	public GameStateDelegateFixedTimestepImpl(GameState gameState, float dt, float maxFrameTime) {
		this.gameState = gameState;
		this.dt = dt;
		this.maxFrameTime = maxFrameTime;
	}

	public void init() {
		gameState.init();
	}

	public void dispose() {
		gameState.dispose();
	}

	public void resume() {
		gameState.resume();
	}

	public void pause() {
		gameState.pause();
	}

	public void show() {
		gameState.show();
	}

	public void hide() {
		gameState.hide();
	}

	public void update() {
		// float t = 0f;
		float frameTime = delta;

		// note: max frame time to avoid spiral of death
		if (frameTime > maxFrameTime)
			frameTime = maxFrameTime;

		accumulator += frameTime;

		while (accumulator >= dt) {
			GlobalTime.setDelta(dt);

			gameState.setDelta(dt);
			gameState.update();

			accumulator -= dt;
		}

		float alpha = accumulator / dt;
		GlobalTime.setAlpha(alpha);

		gameState.setAlpha(alpha);
	}

	public void render() {
		gameState.render();
	}

	public void setDelta(float delta) {
		this.delta = delta;
	}

	public void setAlpha(float alpha) {
		gameState.setAlpha(alpha);
	}

	public Parameters getParameters() {
		return gameState.getParameters();
	}

	public void resize(int width, int height) {
		gameState.resize(width, height);
	}

}
