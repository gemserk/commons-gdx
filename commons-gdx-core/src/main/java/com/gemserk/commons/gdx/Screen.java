package com.gemserk.commons.gdx;

import com.gemserk.componentsengine.utils.Parameters;

public interface Screen {

	/**
	 * Called to initialize the Screen.
	 */
	void init();

	/**
	 * Called to dispose the Screen.
	 */
	void dispose();

	/**
	 * Called to restart the Screen.
	 */
	void restart();

	/**
	 * Called to show the Screen, enables render calls process.
	 */
	void show();

	/**
	 * Called to hide the Screen, disables render calls process.
	 */
	void hide();

	/**
	 * Called to pause the Screen, disables update calls process.
	 */
	void pause();

	/**
	 * Called to resume the Screen, enables update calls process.
	 */
	void resume();

	/**
	 * Process screen update.
	 */
	void update();

	/**
	 * Process screen render.
	 */
	void render();

	void resize(int width, int height);

	void setDelta(float delta);

	/**
	 * Return parameters used to pass information to the screen, to be used when entering it.
	 */
	Parameters getParameters();

}