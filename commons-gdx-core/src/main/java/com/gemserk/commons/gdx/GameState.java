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
	
	void update(int delta);
	
	void render(int delta);

}
