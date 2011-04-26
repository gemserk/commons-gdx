package com.gemserk.commons.gdx.camera;

import com.badlogic.gdx.math.Vector2;

public class Camera {
	
	public final Vector2 position = new Vector2();
	
	public float zoom = 1f;
	
	public float angle = 0f;
	
	public Camera() {

	}

	public Camera(Vector2 position, float zoom, float angle) {
		this.position.set(position);
		this.zoom = zoom;
		this.angle = angle;
	}
	
}