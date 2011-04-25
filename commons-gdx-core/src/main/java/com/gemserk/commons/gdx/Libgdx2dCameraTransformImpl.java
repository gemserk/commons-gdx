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
	
	private final Matrix4 translationMatrix = new Matrix4();

	Vector2 center = new Vector2();
	
	Vector3 axis = new Vector3(0f, 0f, 1f);
	
	float rotation = 0f;
	
	public Libgdx2dCameraTransformImpl() {
		this(null);
	}

	public Libgdx2dCameraTransformImpl(OrthographicCamera internalCamera) {
		this.internalCamera = internalCamera;
	}

	@Override
	public void move(float x, float y) {
		translationMatrix.setToTranslation(-x, -y, 0f);
	}
	
	@Override
	public void center(float x, float y) {
		center.set(x, y);
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
		
		// invertedTransform.trn(center.x, center.y, 0f);
		
		invertedTransform.mul(scaleMatrix);
		invertedTransform.mul(rotationMatrix);
		invertedTransform.mul(translationMatrix);
		
		invertedTransform.trn(center.x, center.y, 0f);
		
		invertedTransform.inv();
		
		tmp.mul(invertedTransform);
		
		position.set(tmp.x, tmp.y);
		
	}

	@Override
	public void apply(SpriteBatch spriteBatch) {

		transform.idt();

		transform.mul(scaleMatrix);
		transform.mul(rotationMatrix);
		transform.mul(translationMatrix);
		
		transform.trn(center.x, center.y, 0f);
		
		// System.out.println(transform);
		
		if (internalCamera != null)
			spriteBatch.setProjectionMatrix(internalCamera.combined);

		spriteBatch.setTransformMatrix(transform);
	}


}