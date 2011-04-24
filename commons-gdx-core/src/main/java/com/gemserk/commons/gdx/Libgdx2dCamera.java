package com.gemserk.commons.gdx;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public interface Libgdx2dCamera {

	/**
	 * Moves the camera to the specified coordinates.
	 */
	void move(float x, float y);
	
	/**
	 * Centers the camera in the specified coordinates.
	 */
	void center(float x, float y);

	/**
	 * Zooms the camera the specified scale factor.
	 * @param s The scale factor to zoom the camera.
	 */
	void zoom(float s);

	/**
	 * Rotates the camera the specified angle.
	 */
	void rotate(float angle);

	/**
	 * Converts view port coordinates to world coordinates based on this camera transformations.
	 * @param position The Vector2 to be converted.
	 */
	void unproject(Vector2 position);

	/**
	 * Applies the camera to the specified sprite batch
	 */
	void apply(SpriteBatch spriteBatch);

}