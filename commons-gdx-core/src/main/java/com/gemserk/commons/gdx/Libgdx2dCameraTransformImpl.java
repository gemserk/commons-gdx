package com.gemserk.commons.gdx;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Libgdx2dCameraTransformImpl implements Libgdx2dCamera {

	private final OrthographicCamera internalCamera;

	private final Matrix4 transform = new Matrix4();
	
	private final Matrix4 invertedTransform = new Matrix4();

	private final Matrix4 scaleMatrix = new Matrix4();
	
	private final Matrix4 rotationMatrix = new Matrix4();

	Vector3 translation = new Vector3(0f, 0f, 0f);
	
	Vector3 axis = new Vector3(0f, 0f, 1f);
	
	float rotation = 0f;
	
	// TODO: define center of the camera!
	
	public Libgdx2dCameraTransformImpl() {
		this(null);
	}

	public Libgdx2dCameraTransformImpl(OrthographicCamera internalCamera) {
		this.internalCamera = internalCamera;
	}

	@Override
	public void move(float x, float y) {
		translation.add(x, y, 0f);
	}

	@Override
	public void zoom(float s) {
		scaleMatrix.setToScaling(s, s, 1f);
	}
	
	@Override
	public void rotate(float angle) {
		rotation += angle;
		rotationMatrix.setToRotation(axis, rotation);
	}
	
	Vector3 tmp = new Vector3();
	
	// TODO: calculate time spent on unproject, optimize by caching the invertedtransform and transform.
	
	@Override
	public void unproject(Vector2 position) {
		
		tmp.set(position.x, position.y, 0f);
		
		if (internalCamera != null)
			internalCamera.unproject(tmp);
		
		invertedTransform.idt();
		invertedTransform.mul(scaleMatrix);
		invertedTransform.mul(rotationMatrix);
		invertedTransform.trn(translation);
		
		invertedTransform.inv();
		
		tmp.mul(invertedTransform);
		
		position.set(tmp.x, tmp.y);
		
	}

	@Override
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