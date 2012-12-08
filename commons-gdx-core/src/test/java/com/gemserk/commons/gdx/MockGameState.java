package com.gemserk.commons.gdx;

import com.gemserk.componentsengine.utils.Parameters;

class MockGameState implements GameState {
	
	boolean updateCalled = false;
	boolean renderCalled = false;
	
	public boolean initialized = false;

	private float delta;

	private float alpha;

	@Override
	public void init() {
		initialized = true;
	}

	@Override
	public void pause() {

	}

	@Override
	public void update() {
		updateCalled = true;
	}

	@Override
	public void render() {
		renderCalled = true;
	}

	@Override
	public void dispose() {
		initialized = false;
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void show() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void setDelta(float delta) {
		this.delta = delta;
	}

	@Override
	public Parameters getParameters() {
		return null;
	}

	@Override
	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated function stub
		
	}

}