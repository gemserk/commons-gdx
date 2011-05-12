package com.gemserk.commons.gdx.camera;

import com.badlogic.gdx.math.Rectangle;

public class CameraRestrictedImpl implements Camera {

	private Rectangle worldBounds;

	private float x;

	private float y;

	private float width;

	private float height;

	private float zoom = 1f;

	private float angle = 0f;
	
	public CameraRestrictedImpl() {
		
	}
	
	public CameraRestrictedImpl(float x, float y, float zoom, float angle) {
		this.x = x;
		this.y = y;
		this.zoom = zoom;
		this.angle = angle;
	}
	
	public CameraRestrictedImpl(float x, float y, float zoom, float angle, float width, float height, Rectangle worldBounds) {
		this.x = x;
		this.y = y;
		this.zoom = zoom;
		this.angle = angle;
		this.width = width;
		this.height = height;
		setWorldBounds(worldBounds);
	}
	
	public void setWorldBounds(Rectangle rectangle) {
		this.worldBounds = new Rectangle(rectangle);
		reposition();
	}
	
	public void setWorldBounds(float x1, float y1, float x2, float y2) {
		this.worldBounds = new Rectangle(x1, y1, x2 - x1, y2 - y1);
		reposition();
	}

	public void setBounds(float width, float height) {
		this.width = width;
		this.height = height;
		reposition();
	}

	@Override
	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
		reposition();
	}

	private void reposition() {
		if (worldBounds == null)
			return;
		
		if (x + getRealWidth() * 0.5f > worldBounds.getX() + worldBounds.getWidth()) {
			x = worldBounds.getX() + worldBounds.getWidth() - getRealWidth() * 0.5f;
		}

		if (x - getRealWidth() * 0.5f < worldBounds.getX()) {
			x = worldBounds.getX() + getRealWidth() * 0.5f;
		}

		if (y + getRealHeight() * 0.5f > worldBounds.getY() + worldBounds.getHeight()) {
			y = worldBounds.getY() + worldBounds.getHeight() - getRealHeight() * 0.5f;
		}

		if (y - getRealHeight() * 0.5f < worldBounds.getY()) {
			y = worldBounds.getY() + getRealHeight() * 0.5f;
		}
	}

	public float getRealHeight() {
		return height / getZoom();
	}

	public float getRealWidth() {
		return width / getZoom();
	}

	@Override
	public float getX() {
		return x;
	}

	@Override
	public float getY() {
		return y;
	}

	@Override
	public void setZoom(float zoom) {
		this.zoom = zoom;
		reposition();
	}

	@Override
	public float getZoom() {
		return zoom;
	}

	@Override
	public void setAngle(float angle) {
		this.angle = angle;
	}

	@Override
	public float getAngle() {
		return angle;
	}

}