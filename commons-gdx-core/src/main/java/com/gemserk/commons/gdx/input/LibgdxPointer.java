package com.gemserk.commons.gdx.input;

import com.badlogic.gdx.math.Vector2;
import com.gemserk.commons.gdx.camera.Libgdx2dCamera;
import com.gemserk.commons.gdx.camera.Libgdx2dCameraNullImpl;

public class LibgdxPointer implements Pointer {

	public boolean touched = false;

	protected Vector2 pressedPosition = new Vector2();

	protected Vector2 releasedPosition = new Vector2();

	protected Vector2 position = new Vector2();

	public boolean wasPressed = false;

	public boolean wasReleased = false;

	private Libgdx2dCamera camera;

	private RealPointer pointer;

	public boolean wasPressed() {
		return wasPressed;
	}

	public boolean wasReleased() {
		return wasReleased;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gemserk.commons.gdx.input.Pointer#getPressedPosition()
	 */
	@Override
	public Vector2 getPressedPosition() {
		return pressedPosition;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gemserk.commons.gdx.input.Pointer#getReleasedPosition()
	 */
	@Override
	public Vector2 getReleasedPosition() {
		return releasedPosition;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gemserk.commons.gdx.input.Pointer#getPosition()
	 */
	@Override
	public Vector2 getPosition() {
		return position;
	}

	public LibgdxPointer(int index) {
		this(index, new Libgdx2dCameraNullImpl());
	}

	public LibgdxPointer(RealPointer pointer) {
		this(pointer, new Libgdx2dCameraNullImpl());
	}

	public LibgdxPointer(int index, Libgdx2dCamera camera) {
		this(new LibgdxRealPointer(index), camera);
	}

	public LibgdxPointer(RealPointer pointer, Libgdx2dCamera camera) {
		this.camera = camera;
		this.pointer = pointer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gemserk.commons.gdx.input.Pointer#update()
	 */
	@Override
	public void update() {
		position.set(getX(), getY());
		camera.unproject(position);
		updatePressed();
		updateReleased();
	}

	private void updateReleased() {
		if (pointer.isDown())
			return;
		wasPressed = false;
		if (touched) {
			touched = false;
			wasReleased = true;
			releasedPosition.set(getX(), getY());
			camera.unproject(releasedPosition);
		} else
			wasReleased = false;
	}

	private void updatePressed() {
		if (!pointer.isDown())
			return;
		wasReleased = false;
		if (!touched) {
			touched = true;
			wasPressed = true;
			pressedPosition.set(getX(), getY());
			camera.unproject(pressedPosition);
		} else
			wasPressed = false;
	}

	private int getY() {
		return pointer.getY();
	}

	private int getX() {
		return pointer.getX();
	}

}