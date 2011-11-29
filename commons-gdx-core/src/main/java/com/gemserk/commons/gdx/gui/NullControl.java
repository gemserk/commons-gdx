package com.gemserk.commons.gdx.gui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class NullControl implements Control {

	@Override
	public String getId() {
		return null;
	}

	@Override
	public float getX() {
		return 0;
	}

	@Override
	public float getY() {
		return 0;
	}

	@Override
	public void setX(float x) {

	}

	@Override
	public void setY(float y) {

	}

	@Override
	public void setPosition(float x, float y) {

	}

	@Override
	public void update() {

	}

	@Override
	public void draw(SpriteBatch spriteBatch) {

	}

	@Override
	public void setParent(Control parent) {

	}

	@Override
	public void invalidate() {
		
	}

	@Override
	public void validate() {

	}

	@Override
	public boolean isVisible() {
		// TODO Auto-generated function stub
		return false;
		
	}

	@Override
	public void setVisible(boolean visible) {
		// TODO Auto-generated function stub
		
	}

}
