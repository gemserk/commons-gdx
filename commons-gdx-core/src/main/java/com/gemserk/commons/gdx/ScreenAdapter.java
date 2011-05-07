package com.gemserk.commons.gdx;

import com.badlogic.gdx.Screen;

public class ScreenAdapter implements Screen {
	
	/**
	 * Don't override if you want to use internalUpdate or internalRender separated methods.
	 */
	@Override
	public void render(float delta) {
		internalUpdate(delta);
		internalRender(delta);
	}

	public void internalRender(float delta) {

	}

	public void internalUpdate(float delta) {

	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {

	}

}
