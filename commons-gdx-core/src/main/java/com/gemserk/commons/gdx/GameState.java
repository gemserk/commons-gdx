package com.gemserk.commons.gdx;

import com.gemserk.componentsengine.utils.Parameters;

/**
 * Provides an API to create separated states of your game.
 */
public interface GameState {

	/**
	 * Called when the GameState is initialized, all resources should be created to be used later.
	 */
	void init();

	/**
	 * Called when the GameState is disposed, all resources used by the GameState should be released.
	 */
	void dispose();

	/**
	 * Called when the GameState is resumed.
	 */
	void resume();

	/**
	 * Called when the GameState is paused.
	 */
	void pause();

	/**
	 * Called when the GameState was made visible.
	 */
	void show();

	/**
	 * Called when the GameState was made invisible.
	 */
	void hide();

	/**
	 * Called to update the GameState with a previous set of delta value.
	 */
	void update();

	/**
	 * Called to render the GameState with a previous set of the alpha value for interpolations.
	 */
	void render();

	/**
	 * Sets the delta value in seconds to be used later in your update method.
	 * 
	 * @param delta
	 *            A value in seconds.
	 */
	void setDelta(float delta);

	/**
	 * If you are rendering multiple times between updates, set the alpha value between 0 and 1 to make interpolations between frames, based on <a href="http://gafferongames.com/game-physics/fix-your-timestep/">fix your timestep</a>
	 */
	void setAlpha(float alpha);

	/**
	 * Returns a Parameters instance used to pass parameters to the GameState.
	 */
	Parameters getParameters();

	/**
	 * Called when the Screen was resized.
	 * 
	 * @param width
	 *            The new width.
	 * @param height
	 *            The new height.
	 */
	void resize(int width, int height);

}
