package com.gemserk.commons.gdx.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.gemserk.commons.gdx.camera.Libgdx2dCamera;
import com.gemserk.commons.gdx.camera.Libgdx2dCameraNullImpl;

public class LibgdxPointer {

	public boolean touched = false;

	Vector2 pressedPosition = new Vector2();

	Vector2 releasedPosition = new Vector2();

	Vector2 position = new Vector2();

	public boolean wasPressed = false;

	public boolean wasReleased = false;

	public int index;

	private Libgdx2dCamera camera;

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
		this(index, new Libgdx2dCameraNullImpl());
	}

	public LibgdxPointer(int index, Libgdx2dCamera camera) {
		this.index = index;
		this.camera = camera;
	}

	public void update() {

		position.set(getX(), getY());
		camera.unproject(position);

		if (Gdx.input.isTouched(index)) {

			if (!touched) {
				touched = true;
				wasPressed = true;

				pressedPosition.set(getX(), getY());
				camera.unproject(pressedPosition);

			} else 
				wasPressed = false;
		}

		if (!Gdx.input.isTouched(index)) {

			if (touched) {
				touched = false;
				wasReleased = true;

				releasedPosition.set(getX(), getY());
				camera.unproject(releasedPosition);

			} else 
				wasReleased = false;

		}
	}

	private int getY() {
		return Gdx.graphics.getHeight() - Gdx.input.getY(index);
	}

	private int getX() {
		return Gdx.input.getX(index);
	}

}