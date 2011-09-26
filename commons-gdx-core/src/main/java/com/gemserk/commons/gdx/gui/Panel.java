package com.gemserk.commons.gdx.gui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Panel extends Container {

	private float x, y;
	private float width, height;

	public Panel(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}
	
	public void setWidth(float width) {
		this.width = width;
	}
	
	public float getWidth() {
		return width;
	}
	
	public void setHeight(float height) {
		this.height = height;
	}
	
	public float getHeight() {
		return height;
	}

	@Override
	public void draw(SpriteBatch spriteBatch) {
		super.draw(spriteBatch);
	}

	@Override
	public void add(Control control) {
		float cx = control.getX();
		float cy = control.getY();
		control.setPosition(x + cx, y + cy);
		super.add(control);
	}

	@Override
	public void setPosition(float x, float y) {
		for (int i = 0; i < getControls().size(); i++) {
			Control control = getControls().get(i);
			control.setPosition(x + control.getX(), y + control.getY());
		}
	}

}
