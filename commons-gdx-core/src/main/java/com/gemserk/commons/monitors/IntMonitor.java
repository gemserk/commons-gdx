package com.gemserk.commons.monitors;

public class IntMonitor {
	
	int value;
	boolean changed;
	
	public int getValue() {
		return value;
	}

	public IntMonitor(int value) {
		this.value = value;
		this.changed = false;
	}

	public void update(int newvalue) {
		changed = newvalue != value;
		value = newvalue;
	}

	public boolean isChanged() {
		return changed;
	}

}