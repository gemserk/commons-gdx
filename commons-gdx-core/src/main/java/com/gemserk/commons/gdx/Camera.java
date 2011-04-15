package com.gemserk.commons.gdx;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

public class Camera {

	private final OrthographicCamera internalCamera;

	private final Matrix4 transform = new Matrix4();

	private final Matrix4 scaleMatrix = new Matrix4();
	
	private final Matrix4 rotationMatrix = new Matrix4();

	Vector3 translation = new Vector3(0f, 0f, 0f);
	
	Vector3 axis = new Vector3(0f, 0f, 1f);
	
	float rotation = 0f;
	
	// define center of the camera!
	
	// INPUT!!!

	public Camera() {
		this(null);
	}

	public Camera(OrthographicCamera internalCamera) {
		this.internalCamera = internalCamera;
	}

	public void move(float x, float y) {
		translation.add(x, y, 0f);
	}

	public void zoom(float s) {
		scaleMatrix.setToScaling(s, s, 1f);
	}
	
	public void rotate(float angle) {
		rotation += angle;
		rotationMatrix.setToRotation(axis, rotation);
	}

	public void apply(SpriteBatch spriteBatch) {

		transform.idt();
		transform.mul(scaleMatrix);
		transform.mul(rotationMatrix);
		transform.trn(translation);

		if (internalCamera != null)
			spriteBatch.setProjectionMatrix(internalCamera.combined);

		spriteBatch.setTransformMatrix(transform);
	}

}