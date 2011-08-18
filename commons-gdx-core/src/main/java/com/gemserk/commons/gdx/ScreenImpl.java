package com.gemserk.commons.gdx;

/**
 * Screen implementation with internal state to call the GameState methods in the correct way and only once when required.
 * 
 * @author acoppes
 * 
 */
public class ScreenImpl implements Screen {

	private final GameState gameState;

	private boolean paused = true;

	private boolean visible = false;

	private boolean inited = false;

	private float delta;
	
	protected float getDelta() {
		return delta;
	}

	public ScreenImpl(GameState gameState) {
		this.gameState = gameState;
	}

	@Override
	public void init() {
		if (inited)
			return;
		inited = true;
		gameState.init();
	}

	@Override
	public void dispose() {
		if (!inited)
			return;
		inited = false;
		gameState.dispose();
	}

	@Override
	public void restart() {
		dispose();
		init();
	}

	@Override
	public void render() {
		if (!visible)
			return;
		gameState.setDelta(this.delta);
		gameState.render();
	}

	@Override
	public void update() {
		if (paused)
			return;
		gameState.setDelta(this.delta);
		gameState.update();
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
	public void setDelta(float delta) {
		this.delta = delta;
	}

}