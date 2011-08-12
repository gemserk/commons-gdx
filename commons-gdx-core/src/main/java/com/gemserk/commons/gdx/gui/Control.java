package com.gemserk.commons.gdx.gui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Control {
	
	String getId();
		
	float getX();
	
	float getY();
	
	void update();

	void draw(SpriteBatch spriteBatch);

}
