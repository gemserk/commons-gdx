package com.gemserk.commons.gdx.games;

import com.badlogic.gdx.math.Vector2;

public class SpatialHierarchicalImpl implements Spatial {

	private final Vector2 aux = new Vector2();

	private final Spatial parent;

	private final Vector2 localPosition = new Vector2();
	private final Vector2 absolutePosition = new Vector2();

	private float localAngle;
	private float w, h;

	public SpatialHierarchicalImpl(Spatial parent, float width, float height) {
		this.parent = parent;
		this.localAngle = 0f;
		setPosition(parent.getX(), parent.getY());
		setAngle(parent.getAngle());
		setSize(width, height);
	}
	
	public SpatialHierarchicalImpl(Spatial parent) {
		this(parent, parent.getWidth(), parent.getHeight());
	}

	@Override
	public float getX() {
		return getPosition().x;
	}

	@Override
	public float getY() {
		return getPosition().y;
	}

	@Override
	public Vector2 getPosition() {
		aux.set(localPosition);
		aux.rotate(parent.getAngle());
		absolutePosition.set(aux.x + parent.getX(), aux.y + parent.getY());
		return absolutePosition;
	}

	@Override
	public void setPosition(float x, float y) {
		localPosition.set(x - parent.getX(), y - parent.getY());
	}

	@Override
	public float getAngle() {
		return localAngle + parent.getAngle();
	}

	@Override
	public void setAngle(float angle) {
		localAngle = angle - parent.getAngle();
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

	@Override
	public void set(Spatial spatial) {
		setPosition(spatial.getX(), spatial.getY());
		setAngle(spatial.getAngle());
		setSize(spatial.getWidth(), spatial.getHeight());
	}

}