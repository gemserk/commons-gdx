package com.gemserk.commons.gdx.camera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Libgdx2dCameraTransformImpl implements Libgdx2dCamera {

	private static final Vector3 rotationAxis = new Vector3(0f, 0f, 1f);

	private final Matrix4 transform = new Matrix4();

	private final Matrix4 invertedTransform = new Matrix4();

	private final Matrix4 scaleMatrix = new Matrix4();

	private final Matrix4 rotationMatrix = new Matrix4();

	private final Matrix4 translationMatrix = new Matrix4();

	private final Vector2 center = new Vector2();

	private final Vector3 tmp = new Vector3();

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
		rotationMatrix.setToRotation(rotationAxis, angle);
	}

	// TODO: calculate time spent on unproject, optimize by caching the invertedtransform and transform.

	@Override
	public void unproject(Vector2 position) {
		calculateTransform(invertedTransform);

		invertedTransform.inv();

		tmp.set(position.x, position.y, 0f);
		tmp.mul(invertedTransform);

		position.set(tmp.x, tmp.y);
	}

	@Override
	public void apply(SpriteBatch spriteBatch) {
		calculateTransform(transform);
		spriteBatch.setTransformMatrix(transform);
	}

	public void push() {
		GL10 gl = Gdx.gl10;
		gl.glPushMatrix();
		calculateTransform(transform);
		gl.glLoadIdentity();
		gl.glMultMatrixf(transform.val, 0);
	}

	public void pop() {
		GL10 gl = Gdx.gl10;
		gl.glPopMatrix();
	}

	private void calculateTransform(Matrix4 m) {
		m.idt();
		m.mul(scaleMatrix);
		m.mul(rotationMatrix);
		m.mul(translationMatrix);
		m.trn(center.x, center.y, 0f);
	}

}