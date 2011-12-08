package com.gemserk.commons.gdx;

import com.gemserk.componentsengine.utils.Parameters;

/**
 * Holds internal state to know if the GameState is initialized, visible and paused or not.
 * 
 * @author acoppes
 * 
 */
public class GameStateDelegateWithInternalStateImpl implements GameState {

	boolean paused = true;
	boolean visible = false;
	boolean initialized = false;

	GameState gameState;
	
	public GameStateDelegateWithInternalStateImpl(GameState gameState) {
		this.gameState = gameState;
	}

	public void init() {
		if (initialized)
			return;
		initialized = true;
		gameState.init();
	}

	public void dispose() {
		if (!initialized)
			return;
		initialized = false;
		gameState.dispose();
	}

	public void resume() {
		if (!paused)
			return;
		paused = false;
		gameState.resume();
	}

	public void pause() {
		if (paused)
			return;
		paused = true;
		gameState.pause();
	}

	public void show() {
		if (visible)
			return;
		visible = true;
		gameState.show();
	}

	public void hide() {
		if (!visible)
			return;
		visible = false;
		gameState.hide();
	}

	public void update() {
		if (paused)
			return;
		gameState.update();
	}

	public void render() {
		if (!visible)
			return;
		gameState.render();
	}

	public void setDelta(float delta) {
		gameState.setDelta(delta);
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
