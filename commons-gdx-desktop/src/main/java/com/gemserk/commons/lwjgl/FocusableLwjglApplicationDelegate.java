package com.gemserk.commons.lwjgl;

import org.lwjgl.opengl.Display;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.gdx.ApplicationListener;

public class FocusableLwjglApplicationDelegate implements ApplicationListener {

	protected static final Logger logger = LoggerFactory.getLogger(FocusableLwjglApplicationDelegate.class);

	boolean wasFocused = false;

	private final ApplicationListener applicationListener;

	public boolean isFocused() {
		return Display.isActive();
	}
	
	public FocusableLwjglApplicationDelegate(ApplicationListener applicationListener) {
		this.applicationListener = applicationListener;
	}

	@Override
	public void create() {
		applicationListener.create();
		wasFocused = isFocused();
	}

	@Override
	public void render() {
		applicationListener.render();

		if (!isFocused() && wasFocused) {
			pause();
			wasFocused = false;
			logger.debug("Focus lost, pausing game");
		}

		if (isFocused() && !wasFocused) {
			resume();
			wasFocused = true;
			logger.debug("Focus gained, resuming game");
		}

	}

	@Override
	public void resize(int width, int height) {
		applicationListener.resize(width, height);
	}

	@Override
	public void pause() {
		applicationListener.pause();
	}

	@Override
	public void resume() {
		applicationListener.resume();
	}

	@Override
	public void dispose() {
		applicationListener.dispose();
	}
}