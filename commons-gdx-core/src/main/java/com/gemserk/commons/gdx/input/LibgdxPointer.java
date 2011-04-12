package com.gemserk.commons.gdx.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class LibgdxPointer {

	boolean touched = false;

	Vector2 pressedPosition = new Vector2();

	Vector3 pressedPosition3d = new Vector3();

	Vector2 releasedPosition = new Vector2();

	Vector3 releasedPosition3d = new Vector3();

	public boolean wasPressed;

	public boolean wasReleased;

	public int index;

	private OrthographicCamera camera;
	
	public Vector2 getPressedPosition() {
		return pressedPosition;
	}
	
	public Vector2 getReleasedPosition() {
		return releasedPosition;
	}

	public LibgdxPointer(int index) {
		this(index, null);
	}

	public LibgdxPointer(int index, OrthographicCamera camera) {
		this.index = index;
		this.camera = camera;
	}

	public void update() {

		if (Gdx.input.isTouched(index)) {

			if (!touched) {
				touched = true;
				wasPressed = true;
				
				if (camera != null) {

					pressedPosition3d.set(Gdx.input.getX(index), Gdx.input.getY(index), 0f);
					camera.unproject(pressedPosition3d);
					pressedPosition.set(pressedPosition3d.x, pressedPosition3d.y);

				} else {
					pressedPosition.set(Gdx.input.getX(index), Gdx.graphics.getHeight() - Gdx.input.getY(index));
				}

			} else {
				wasPressed = false;
			}

		}

		if (!Gdx.input.isTouched(index)) {

			if (touched) {
				touched = false;
				wasReleased = true;
				
				if (camera != null) {
					
					releasedPosition3d.set(Gdx.input.getX(index), Gdx.input.getY(index), 0f);
					camera.unproject(releasedPosition3d);
					releasedPosition.set(releasedPosition3d.x, releasedPosition3d.y);

				} else {
					releasedPosition.set(Gdx.input.getX(index), Gdx.graphics.getHeight() - Gdx.input.getY(index));
				}

			} else {
				wasReleased = false;
			}

		}
	}

}