package com.gemserk.componentsengine.utils.timers;

public interface Timer {

	boolean update(int delta);

	void reset();
	
	boolean isRunning();
	
}