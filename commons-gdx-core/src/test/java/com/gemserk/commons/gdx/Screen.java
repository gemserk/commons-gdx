package com.gemserk.commons.gdx;

public interface Screen {

	void init();
	
	void dispose();
	
	void show();
	
	void hide();
	
	void pause();
	
	void resume();

	void update(int delta);
	
	void render(int delta);
	
	void resize(int width, int height);

}