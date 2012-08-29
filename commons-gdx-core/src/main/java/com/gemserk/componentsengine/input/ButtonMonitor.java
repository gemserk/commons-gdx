package com.gemserk.componentsengine.input;

public abstract class ButtonMonitor {

	public enum Status {
		PRESSED, RELEASED, HOLDED
	}

	boolean pressed;
	boolean released;
	boolean holded;

	protected abstract boolean isDown();

	private boolean wasDown = false;

	public void update() {
		boolean down = isDown();
		pressed = !wasDown && down;
		released = wasDown && !down;
		holded = down && !pressed;
		wasDown = down;
	}

	public boolean isPressed() {
		return pressed;
	}

	public boolean isReleased() {
		return released;
	}

	public boolean isHolded() {
		return holded;
	}

	public boolean status(Status status) {
		switch (status) {
		case PRESSED:
			return isPressed();
		case RELEASED:
			return isReleased();
		case HOLDED:
			return isHolded();
		default:
			return false;
		}
	}

}