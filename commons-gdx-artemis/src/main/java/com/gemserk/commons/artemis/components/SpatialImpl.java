package com.gemserk.commons.artemis.components;

public class SpatialImpl implements Spatial {

	private float x, y;

	private float w, h;

	private float angle;

	@Override
	public float getX() {
		return x;
	}

	@Override
	public float getY() {
		return y;
	}

	@Override
	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public float getAngle() {
		return angle;
	}

	@Override
	public void setAngle(float angle) {
		this.angle = angle;
	}

	@Override
	public float getWidth() {
		return w;
	}

	@Override
	public float getHeight() {
		return h;
	}

	@Override
	public void setSize(float width, float height) {
		this.w = width;
		this.h = height;
	}

	public SpatialImpl(float x, float y, float w, float h, float angle) {
		super();
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.angle = angle;
	}

}
