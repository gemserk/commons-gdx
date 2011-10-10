package com.gemserk.commons.gdx.gui;

public abstract class ControlImpl implements Control {
	
	String id;
	float x,y;
	Control parent = new NullControl();
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	@Override
	public void setPosition(float x, float y) {
		setX(x);
		setY(y);
	}
	
	public void setParent(Control parent) {
		this.parent = parent;
	}

}
