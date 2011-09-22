package com.gemserk.commons.gdx.games;

import com.badlogic.gdx.math.Vector2;

public class SpatialHierarchicalImpl2 implements Spatial {

	private final Spatial parent;
	private final Spatial child;

	private final Vector2 position = new Vector2();

	/**
	 * Creates a new hierarchical spatial with two Spatial assuming the second Spatial is already in local coordinates of the first Spatial, see SpatialUtils for more information.
	 * 
	 * @param parent
	 *            The parent Spatial
	 * @param child
	 *            The child Spatial
	 */
	public SpatialHierarchicalImpl2(Spatial parent, Spatial child) {
		this.parent = parent;
		this.child = child;
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
		position.set(child.getX(), child.getY());
		position.rotate(parent.getAngle());
		position.set(position.x + parent.getX(), position.y + parent.getY());
		return position;
	}

	@Override
	public void setPosition(float x, float y) {
		child.setPosition(x - parent.getX(), y - parent.getY());
	}

	@Override
	public float getAngle() {
		return child.getAngle() + parent.getAngle();
	}

	@Override
	public void setAngle(float angle) {
		child.setAngle(angle - parent.getAngle());
	}

	@Override
	public float getWidth() {
		return child.getWidth();
	}

	@Override
	public float getHeight() {
		return child.getHeight();
	}

	@Override
	public void setSize(float width, float height) {
		child.setSize(width, height);
	}

	@Override
	public void set(Spatial spatial) {
		setPosition(spatial.getX(), spatial.getY());
		setAngle(spatial.getAngle());
		setSize(spatial.getWidth(), spatial.getHeight());
	}

}