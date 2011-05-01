package com.gemserk.commons.gdx.camera;

import com.badlogic.gdx.math.Vector2;

public class Camera {
	
	private final Vector2 position = new Vector2();
	
	private float zoom;
	
	private float angle;
	
	public Camera() {
		this(0f, 0f, 1f, 0f);
	}

	public Camera(Vector2 position, float zoom, float angle) {
		this.position.set(position);
		this.zoom = zoom;
		this.angle = angle;
	}
	
	public Camera(float x, float y, float zoom, float angle) {
		this.position.set(x, y);
		this.zoom = zoom;
		this.angle = angle;
	}
	
	public void setPosition(float x, float y) {
		position.set(x,y);
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
		return position.x;
	}
	
	public float getY() {
		return position.y;
	}
	
	
}