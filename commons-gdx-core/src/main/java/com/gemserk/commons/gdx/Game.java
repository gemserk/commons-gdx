package com.gemserk.commons.gdx;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;

public class Game implements ApplicationListener {

	private Screen screen;

	public Screen getScreen() {
		return screen;
	}

	@Override
	public void dispose() {
		if (screen == null)
			return;
		screen.hide();
		screen.pause();
		screen.dispose();
	}

	@Override
	public void pause() {
		if (screen != null)
			screen.pause();
	}

	@Override
	public void resume() {
		if (screen != null)
			screen.resume();
	}

	@Override
	public void render() {
		if (screen == null)
			return;
		screen.update(getDelta());
		screen.render(getDelta());
	}

	protected int getDelta() {
		return (int) (Gdx.graphics.getDeltaTime() * 1000f);
	}

	@Override
	public void resize(int width, int height) {
		if (screen != null)
			screen.resize(width, height);
	}

	public void setScreen(Screen screen) {
		if (screen == null)
			return;

		if (this.screen != null) {
			this.screen.pause();
			this.screen.hide();
		}

		this.screen = screen;
		screen.init();
		screen.resume();
		screen.show();
	}

	@Override
	public void create() {

	}

}