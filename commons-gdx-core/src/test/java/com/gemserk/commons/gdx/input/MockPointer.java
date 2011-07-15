package com.gemserk.commons.gdx.input;

import com.badlogic.gdx.math.Vector2;

public class MockPointer implements Pointer {
	
	public Vector2 position = new Vector2();
	public Vector2 pressedPosition = new Vector2();
	public Vector2 releasedPosition = new Vector2();
	public boolean wasPressed, wasReleased;
	public boolean updateCalled;
	
	@Override
	public Vector2 getPressedPosition() {
		return pressedPosition;
	}

	@Override
	public Vector2 getReleasedPosition() {
		return releasedPosition;
	}

	@Override
	public Vector2 getPosition() {
		return position;
	}

	@Override
	public boolean wasPressed() {
		return wasPressed;
	}

	@Override
	public boolean wasReleased() {
		return wasReleased;
	}

	@Override
	public void update() {
		updateCalled = true;
	}

}
