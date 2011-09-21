package com.gemserk.componentsengine.input;

public abstract class AnalogInputMonitor {

	float value = 0f;
	
	float oldValue = 0f;

	boolean changed = false;

	protected abstract float newValue();

	public void update() {
		changed = false;
		float newValue = newValue();

		if (newValue != value) {
			oldValue = value;
			value = newValue;
			changed = true;
		}
	}
	
	public float getOldValue() {
		return oldValue;
	}

	public float getValue() {
		return value;
	}

	public boolean hasChanged() {
		return changed;
	}
}