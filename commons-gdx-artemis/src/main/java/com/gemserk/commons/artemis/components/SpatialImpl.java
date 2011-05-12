package com.gemserk.commons.artemis.components;

import com.badlogic.gdx.math.Vector2;

public class SpatialImpl implements Spatial {

	private float w, h;

	private float angle;

	private Vector2 position = new Vector2();

	@Override
	public float getX() {
		return position.x;
	}

	@Override
	public float getY() {
		return position.y;
	}

	@Override
	public void setPosition(float x, float y) {
		position.set(x,y);
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
		this.setPosition(x, y);
		this.w = w;
		this.h = h;
		this.angle = angle;
	}

	public SpatialImpl(Spatial spatial) {
		this(spatial.getX(), spatial.getY(), spatial.getWidth(), spatial.getHeight(), spatial.getAngle());
	}

	@Override
	public void set(Spatial spatial) {
		setPosition(spatial.getX(), spatial.getY());
		setSize(spatial.getWidth(), spatial.getHeight());
		setAngle(spatial.getAngle());
	}

	@Override
	public Vector2 getPosition() {
		return position;
	}

}
