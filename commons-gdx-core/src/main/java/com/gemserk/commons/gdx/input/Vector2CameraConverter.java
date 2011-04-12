package com.gemserk.commons.gdx.input;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Vector2CameraConverter {
	
	private final OrthographicCamera camera;
	
	private final Vector3 v3 = new Vector3();

	public Vector2CameraConverter(OrthographicCamera camera) {
		this.camera = camera;
	}
	
	public Vector2 getVector2(float x, float y, Vector2 v2) {
		v3.set(x, y, 0f);
		camera.unproject(v3);
		v2.set(v3.x, v3.y);
		return v2;
	}

}
