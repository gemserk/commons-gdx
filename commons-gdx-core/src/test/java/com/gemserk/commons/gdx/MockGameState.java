package com.gemserk.commons.gdx;

class MockGameState implements GameState {
	
	boolean updateCalled = false;
	
	boolean renderCalled = false;

	@Override
	public void init() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void update(int delta) {
		updateCalled = true;
	}

	@Override
	public void render(int delta) {
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

}