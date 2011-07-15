package com.gemserk.commons.gdx.input;

import com.badlogic.gdx.math.Vector2;

public interface Pointer {

	Vector2 getPressedPosition();

	Vector2 getReleasedPosition();

	Vector2 getPosition();
	
	boolean wasPressed();

	boolean wasReleased();

	void update();

}