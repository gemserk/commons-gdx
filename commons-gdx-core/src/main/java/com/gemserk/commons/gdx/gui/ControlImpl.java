package com.gemserk.commons.gdx.gui;

public abstract class ControlImpl implements Control {
	
	String id;
	float x,y;
	Control parent = new NullControl();
	boolean dirty = false;
	
	public boolean isDirty() {
		return dirty;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
	public float getX() {
		return x + parent.getX();
	}
	
	public float getY() {
		return y + parent.getY();
	}
	
	public void setX(float x) {
		this.x = x;
		invalidate();
	}
	
	public void setY(float y) {
		this.y = y;
		invalidate();
	}
	
	@Override
	public void setPosition(float x, float y) {
		setX(x);
		setY(y);
	}
	
	public void setParent(Control parent) {
		this.parent = parent;
		invalidate();
	}
	
	@Override
	public void invalidate() {
		dirty = true;
	}
	
	@Override
	public void validate() {
		dirty = false;
	}

}
