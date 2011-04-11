package com.gemserk.commons.gdx.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class LibgdxPointer {

	boolean touched = false;

	Vector2 pressedPosition = new Vector2();

	Vector2 releasedPosition = new Vector2();

	public boolean wasPressed;

	public boolean wasReleased;

	public int index;
	
	public LibgdxPointer(int index) {
		this.index = index;
	}

	public void update() {
		
		if (Gdx.input.isTouched(index)) {
			
			if (!touched) {
				touched = true;
				wasPressed = true;
				pressedPosition.set(Gdx.input.getX(index), Gdx.graphics.getHeight() - Gdx.input.getY(index));
			} else 
				wasPressed = false;
			
		} 
		
		if (Gdx.input.isTouched(index)) {
			
			if (touched) {
				touched = false;
				wasReleased = true;
				releasedPosition.set(Gdx.input.getX(index), Gdx.graphics.getHeight() - Gdx.input.getY(index));
			} else {
				wasReleased = false;
			}
			
		}
	}

}