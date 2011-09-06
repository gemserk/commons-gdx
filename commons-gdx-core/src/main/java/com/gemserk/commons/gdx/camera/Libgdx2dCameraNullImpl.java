package com.gemserk.commons.gdx.camera;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

public class Libgdx2dCameraNullImpl implements Libgdx2dCamera {
	
	Matrix4 combinedMatrix = new Matrix4();

	@Override
	public void move(float x, float y) {
		
	}

	@Override
	public void zoom(float s) {
		
	}

	@Override
	public void rotate(float angle) {
		
	}

	@Override
	public void unproject(Vector2 position) {

	}

	@Override
	public void apply(SpriteBatch spriteBatch) {
		
	}

	@Override
	public void center(float x, float y) {
		
	}

	@Override
	public void project(Vector2 position) {
		
	}

	@Override
	public void apply() {
		
	}

	@Override
	public Matrix4 getCombinedMatrix() {
		return combinedMatrix;
	}

}