package com.gemserk.commons.monitors;

public class FloatMonitor {
	
	float value;
	boolean changed;
	
	float epsilon = 0.001f;
	
	public float getValue() {
		return value;
	}

	public FloatMonitor(float value) {
		this.value = value;
		this.changed = false;
	}

	public void update(float newvalue) {
		changed = (Math.abs(newvalue - value) < epsilon);
		value = newvalue;
	}

	public boolean isChanged() {
		return changed;
	}

}