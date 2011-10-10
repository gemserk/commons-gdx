package com.gemserk.commons.gdx.gui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MockControl implements Control {
	
	String id;
	
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public float getX() {
		return 0;
	}

	@Override
	public float getY() {
		// TODO Auto-generated function stub
		return 0;

	}

	@Override
	public void setPosition(float x, float y) {
		// TODO Auto-generated function stub

	}

	@Override
	public void update() {
		// TODO Auto-generated function stub

	}

	@Override
	public void draw(SpriteBatch spriteBatch) {
		// TODO Auto-generated function stub

	}

	@Override
	public void setX(float x) {
		// TODO Auto-generated function stub
		
	}

	@Override
	public void setY(float y) {
		// TODO Auto-generated function stub
		
	}

	@Override
	public void setParent(Control parent) {
		// TODO Auto-generated function stub
		
	}

}