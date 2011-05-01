package com.gemserk.commons.gdx.camera;

public class Camera {

	private float x, y;

	private float zoom;

	private float angle;

	public Camera() {
		this(0f, 0f, 1f, 0f);
	}

	public Camera(float x, float y, float zoom, float angle) {
		this.x = x;
		this.y = y;
		this.zoom = zoom;
		this.angle = angle;
	}

	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public void setZoom(float zoom) {
		this.zoom = zoom;
	}
	
	public void setAngle(float angle) {
		this.angle = angle;
	}

	public float getZoom() {
		return zoom;
	}

	public float getAngle() {
		return angle;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

}