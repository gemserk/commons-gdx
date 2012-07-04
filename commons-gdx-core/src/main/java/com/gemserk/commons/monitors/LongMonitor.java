package com.gemserk.commons.monitors;

public class LongMonitor {
	
	long value;
	boolean changed;

	public LongMonitor(long value) {
		this.value = value;
		this.changed = false;
	}

	public void update(long newvalue) {
		changed = newvalue != value;
		value = newvalue;
	}

	public boolean isChanged() {
		return changed;
	}

}