package com.gemserk.commons.gdx.camera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
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

	private final Matrix4 projectionMatrix = new Matrix4();
	private final Matrix4 combinedMatrix = new Matrix4();

	private boolean matrixDirty;

	private float width;
	private float height;

	public Libgdx2dCameraTransformImpl() {
		this(0, 0);
	}

	public Libgdx2dCameraTransformImpl(float centerX, float centerY) {
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		projectionMatrix.setToOrtho2D(0, 0, width, height);
		center(centerX, centerY);
		matrixDirty = true;
	}

	@Override
	public void move(float x, float y) {
		matrixDirty = true;
		translationMatrix.setToTranslation(-x, -y, 0f);
	}

	@Override
	public void center(float x, float y) {
		matrixDirty = true;
		center.set(x, y);
	}

	@Override
	public void zoom(float s) {
		matrixDirty = true;
		scaleMatrix.setToScaling(s, s, 1f);
	}

	@Override
	public void rotate(float angle) {
		matrixDirty = true;
		rotationMatrix.setToRotation(rotationAxis, angle);
	}

	// TODO: calculate time spent on unproject, optimize by caching the invertedtransform and transform.

	@Override
	public void unproject(Vector2 position) {
		recalculateMatrix();

		tmp.set(position.x, position.y, 0f);
		tmp.mul(invertedTransform);

		position.set(tmp.x, tmp.y);
	}

	@Override
	public void apply(SpriteBatch spriteBatch) {
		calculateTransform(transform);
		spriteBatch.setTransformMatrix(transform);
	}

	public void apply() {
		GL10 gl = Gdx.gl10;

		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadMatrixf(projectionMatrix.val, 0);

		calculateTransform(transform);

		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadMatrixf(transform.val, 0);
	}

	public void push() {
		GL10 gl = Gdx.gl10;
		gl.glMatrixMode(GL10.GL_MODELVIEW);
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

	@Override
	public void project(Vector2 position) {
		recalculateMatrix();

		tmp.set(position.x, position.y, 0f);
		tmp.mul(transform);

		position.set(tmp.x, tmp.y);
	}

	@Override
	public Matrix4 getCombinedMatrix() {
		recalculateMatrix();
		return combinedMatrix;
	}

	@Override
	public void getFrustum(Rectangle frustum) {
		recalculateMatrix();

		tmp.set(0f, 0f, 0f);
		tmp.mul(invertedTransform);

		float x0 = tmp.x;
		float y0 = tmp.y;

		tmp.set(width, 0f, 0f);
		tmp.mul(invertedTransform);

		float x1 = tmp.x;
		float y1 = tmp.y;

		tmp.set(0f, height, 0f);
		tmp.mul(invertedTransform);

		float x2 = tmp.x;
		float y2 = tmp.y;

		tmp.set(width, height, 0f);
		tmp.mul(invertedTransform);

		float x3 = tmp.x;
		float y3 = tmp.y;

		frustum.x = Math.min(Math.min(x0, x1), Math.min(x2, x3));
		frustum.y = Math.min(Math.min(y0, y1), Math.min(y2, y3));

		frustum.width = Math.max(Math.max(x0, x1), Math.max(x2, x3)) - frustum.x;
		frustum.height = Math.max(Math.max(y0, y1), Math.max(y2, y3)) - frustum.y;

	}

	private void recalculateMatrix() {
		if (matrixDirty) {
			combinedMatrix.set(projectionMatrix);
			calculateTransform(transform);
			Matrix4.mul(combinedMatrix.val, transform.val);

			calculateTransform(invertedTransform);
			invertedTransform.inv();

			matrixDirty = false;
		}
	}

}