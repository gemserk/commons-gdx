package com.gemserk.commons.gdx.gui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Panel extends Container {
	
	private float x, y;
	
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
	
}
