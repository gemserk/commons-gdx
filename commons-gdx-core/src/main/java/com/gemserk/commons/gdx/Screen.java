package com.gemserk.commons.gdx;

public interface Screen {

	void init();
	
	void dispose();
	
	void restart();
	
	void show();
	
	void hide();
	
	void pause();
	
	void resume();

	void update();
	
	void render();
	
	void resize(int width, int height);

	void setDelta(float delta);
	
}