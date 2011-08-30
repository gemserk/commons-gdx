package com.gemserk.commons.gdx;

import com.gemserk.componentsengine.utils.Parameters;

public class GameStateImpl implements GameState {

	/**
	 * Delta time in seconds.
	 */
	private float delta;
	private Parameters parameters;

	/**
	 * Returns the delta time in seconds.
	 */
	protected float getDelta() {
		return delta;
	}

	/**
	 * Returns the parameters used when entering the game state.
	 */
	protected Parameters getParameters() {
		return parameters;
	}

	/**
	 * Returns the delta time in milliseconds
	 */
	protected int getDeltaInMs() {
		return (int) (delta * 1000f);
	}

	@Override
	public void init() {

	}

	@Override
	public void dispose() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void show() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void update() {

	}

	@Override
	public void render() {

	}

	@Override
	public void setDelta(float delta) {
		this.delta = delta;
	}

	@Override
	public void setParameters(Parameters parameters) {
		this.parameters = parameters;
	}

}
