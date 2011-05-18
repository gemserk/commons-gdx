package com.gemserk.commons.gdx;

class MockScreen implements Screen {

	boolean updateCalled = false;

	boolean renderCalled = false;

	boolean initCalled = false;

	boolean disposeCalled = false;
	
	boolean resumeCalled = false;
	
	boolean pauseCalled = false;
	
	boolean showCalled = false;
	
	boolean hideCalled = false;

	@Override
	public void update(int delta) {
		updateCalled = true;
	}

	@Override
	public void render(int delta) {
		renderCalled = true;
	}

	@Override
	public void init() {
		initCalled = true;
	}

	@Override
	public void dispose() {
		disposeCalled = true;
	}

	@Override
	public void show() {
		showCalled = true;
	}

	@Override
	public void hide() {
		hideCalled = true;
	}

	@Override
	public void pause() {
		pauseCalled = true;
	}

	@Override
	public void resume() {
		resumeCalled = true;
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated function stub
	}

}