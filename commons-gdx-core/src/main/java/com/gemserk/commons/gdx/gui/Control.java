package com.gemserk.commons.gdx.gui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Control {
	
	String getId();
		
	float getX();
	
	float getY();
	
	void setX(float x);
	
	void setY(float y);
	
	void setPosition(float x, float y);
	
	void update();

	void draw(SpriteBatch spriteBatch);
	
	void setParent(Control parent);
	
	void invalidate();
	
	void validate();
	
	boolean isVisible();
	
	void setVisible(boolean visible);

}
