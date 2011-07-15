package com.gemserk.commons.gdx.input;

import com.badlogic.gdx.Gdx;

public class LibgdxRealPointer implements RealPointer {

	private final int pointer;

	public LibgdxRealPointer() {
		this(0);
	}

	public LibgdxRealPointer(int index) {
		this.pointer = index;
	}

	@Override
	public int getX() {
		return Gdx.input.getX(pointer);
	}

	@Override
	public int getY() {
		return Gdx.graphics.getHeight() - Gdx.input.getY(pointer);
	}

	@Override
	public boolean isDown() {
		return Gdx.input.isTouched(pointer);
	}

}
