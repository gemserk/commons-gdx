package com.gemserk.commons.gdx.input;

public class MockRealPointer implements RealPointer {
	
	int x,y;
	boolean down;

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public boolean isDown() {
		return down;
	}

}
