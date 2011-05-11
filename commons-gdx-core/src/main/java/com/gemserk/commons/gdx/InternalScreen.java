package com.gemserk.commons.gdx;

public class InternalScreen extends ScreenAdapter {

	private final GameState gameState;

	private boolean inited = false;

	public InternalScreen(GameState gameState) {
		this.gameState = gameState;
	}

	private void init() {
		if (inited)
			return;
		gameState.init();
		inited = true;
	}

	@Override
	public void show() {
		super.show();
		init();
	}
	
	@Override
	public void hide() {
		super.hide();
		gameState.pause();
	}

	@Override
	public void internalRender(float delta) {
		super.internalRender(delta);
		init();
		gameState.render((int) (delta * 1000f));
	}

	@Override
	public void internalUpdate(float delta) {
		super.internalUpdate(delta);
		init();
		gameState.update((int) (delta * 1000f));
	}

	@Override
	public void dispose() {
		super.dispose();
		if (inited)
			gameState.dispose();
		inited = false;
	}

}
