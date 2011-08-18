package com.gemserk.commons.gdx;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;

public class Game implements ApplicationListener {

	protected Screen screen;

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
		screen.setDelta(Gdx.graphics.getDeltaTime());
		screen.update();
		screen.render();
	}

	protected float getDeltaInMs() {
		return Gdx.graphics.getDeltaTime() * 1000f;
	}

	@Override
	public void resize(int width, int height) {
		if (screen != null)
			screen.resize(width, height);
	}

	public void setScreen(Screen screen) {
		setScreen(screen, false);
	}
	
	public void setScreen(Screen screen, boolean shouldDispose) {
		if (screen == null)
			return;

		Screen currentScreen = getScreen();
		
		if (currentScreen != null) {
			currentScreen.pause();
			currentScreen.hide();
			if (shouldDispose)
				currentScreen.dispose();
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