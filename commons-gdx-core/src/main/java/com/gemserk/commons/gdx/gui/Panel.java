package com.gemserk.commons.gdx.gui;

public class Panel extends Container {

	private float width, height;

	public Panel(float x, float y) {
		setX(x);
		setY(y);
	}

	public Panel(String id, float x, float y) {
		super(id);
		setX(x);
		setY(y);
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

}
