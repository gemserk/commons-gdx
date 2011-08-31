package com.gemserk.commons.gdx;

import com.gemserk.componentsengine.utils.Parameters;

class MockGameState implements GameState {
	
	boolean updateCalled = false;
	
	boolean renderCalled = false;

	private float delta;

	@Override
	public void init() {

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


}