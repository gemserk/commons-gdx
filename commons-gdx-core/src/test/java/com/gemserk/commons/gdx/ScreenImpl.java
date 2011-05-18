package com.gemserk.commons.gdx;

public class ScreenImpl implements Screen {

	private final GameState gameState;

	boolean paused = false;
	
	boolean visible = false;
	
	boolean inited = false;
	
	@Override
	public boolean isInited() {
		return inited;
	}

	public boolean isPaused() {
		return paused;
	}

	public boolean isVisible() {
		return visible;
	}
	
	public ScreenImpl(GameState gameState) {
		this.gameState = gameState;
	}

	@Override
	public void init() {
		// if already inited, do nothing
		if (inited)
			return;
		inited = true;
		gameState.init();
	}

	@Override
	public void render(int delta) {
		if (!visible)
			return;
		gameState.render(delta);
	}

	@Override
	public void update(int delta) {
		if (paused)
			return;
		gameState.update(delta);
	}

	@Override
	public void resize(int width, int height) {
		// call gamestate resize?
	}

	@Override
	public void show() {
		if (visible)
			return;
		visible = true;
		gameState.show();
	}

	@Override
	public void hide() {
		if (!visible)
			return;
		visible = false;
		gameState.hide();
	}

	@Override
	public void pause() {
		if (paused)
			return;
		paused = true;
		gameState.pause();
	}

	@Override
	public void resume() {
		if (!paused)
			return;
		paused = false;
		gameState.resume();
	}

	@Override
	public void dispose() {
		// alreadyDisposed()
		if (!inited)
			return;
		inited = false;
		gameState.dispose();
	}

}