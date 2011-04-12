package com.gemserk.commons.gdx.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

public class LibgdxPointer {

	public boolean touched = false;

	Vector2 pressedPosition = new Vector2();

	Vector2 releasedPosition = new Vector2();

	Vector2 position = new Vector2();

	public boolean wasPressed = false;

	public boolean wasReleased = false;

	public int index;

	private OrthographicCamera camera;

	private Vector2CameraConverter vector2CameraConverter;

	public Vector2 getPressedPosition() {
		return pressedPosition;
	}

	public Vector2 getReleasedPosition() {
		return releasedPosition;
	}
	
	public Vector2 getPosition() {
		return position;
	}

	public LibgdxPointer(int index) {
		this(index, null);
	}

	public LibgdxPointer(int index, OrthographicCamera camera) {
		this.index = index;
		this.camera = camera;
		vector2CameraConverter = new Vector2CameraConverter(camera);
	}

	public void update() {

		if (Gdx.input.isTouched(index)) {

			if (!touched) {
				touched = true;
				wasPressed = true;

				if (camera != null) {
					pressedPosition = vector2CameraConverter.getVector2(Gdx.input.getX(index), Gdx.input.getY(index), pressedPosition);
				} else {
					pressedPosition.set(Gdx.input.getX(index), Gdx.graphics.getHeight() - Gdx.input.getY(index));
				}

			} else {
				wasPressed = false;
			}

			if (camera != null)
				position = vector2CameraConverter.getVector2(Gdx.input.getX(index), Gdx.input.getY(index), position);
			else
				position.set(Gdx.input.getX(index), Gdx.graphics.getHeight() - Gdx.input.getY(index));

		}

		if (!Gdx.input.isTouched(index)) {

			if (touched) {
				touched = false;
				wasReleased = true;

				if (camera != null) {
					releasedPosition = vector2CameraConverter.getVector2(Gdx.input.getX(index), Gdx.input.getY(index), releasedPosition);
				} else {
					releasedPosition.set(Gdx.input.getX(index), Gdx.graphics.getHeight() - Gdx.input.getY(index));
				}

			} else {
				wasReleased = false;
			}

		}
	}

}