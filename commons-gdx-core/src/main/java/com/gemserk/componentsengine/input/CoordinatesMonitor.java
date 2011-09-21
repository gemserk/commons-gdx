package com.gemserk.componentsengine.input;

public abstract class CoordinatesMonitor {

	float x = 0;
	float y = 0;

	boolean changed = false;

	protected abstract float readX();

	protected abstract float readY();

	public void update() {
		changed = false;
		float newX = readX();
		float newY = readY();

		if (newX != x || newY != y) {
			x = newX;
			y = newY;
			changed = true;
		}
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public boolean hasChanged() {
		return changed;
	}
}