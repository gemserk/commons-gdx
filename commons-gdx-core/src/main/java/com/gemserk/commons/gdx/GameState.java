package com.gemserk.commons.gdx;

public interface GameState {
	
	void init();
	
	void pause();
	
	void update(int delta);
	
	void render(int delta);
	
	void dispose();

}
