package com.gemserk.commons.gdx;

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

}
