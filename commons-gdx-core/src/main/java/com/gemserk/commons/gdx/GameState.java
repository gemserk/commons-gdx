package com.gemserk.commons.gdx;

import com.gemserk.componentsengine.utils.Parameters;

public interface GameState {

	/**
	 * Called when the GameState start.
	 */
	void init();

	/**
	 * Called when the GameState ends.
	 */
	void dispose();

	void resume();

	void pause();

	void show();

	void hide();

	void update();

	void render();

	void setDelta(float delta);

	/**
	 * Sets the alpha value used for interpolations reasons when rendering stuff, based on
	 * <a href="http://gafferongames.com/game-physics/fix-your-timestep/">fix your timestep</a>
	 */
	void setAlpha(float alpha);

	Parameters getParameters();

	void resize(int width, int height);
	
}
