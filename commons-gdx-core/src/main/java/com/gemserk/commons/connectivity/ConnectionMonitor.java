package com.gemserk.commons.connectivity;

public interface ConnectionMonitor {

	boolean wasConnected();

	boolean wasDisconnected();

	void update();

}