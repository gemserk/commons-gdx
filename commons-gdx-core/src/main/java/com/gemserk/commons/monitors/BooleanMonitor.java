package com.gemserk.commons.monitors;

public class BooleanMonitor {
	
	private boolean value;
	private boolean changed;
	
	public boolean getValue() {
		return value;
	}
	
	public BooleanMonitor(boolean value) {
		this.value = value;
		this.changed = false;
	}

	public void update(boolean newvalue) {
		changed = newvalue != value;
		value = newvalue;
	}

	public boolean isChanged() {
		return changed;
	}

}