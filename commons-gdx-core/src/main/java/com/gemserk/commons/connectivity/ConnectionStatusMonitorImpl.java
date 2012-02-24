package com.gemserk.commons.connectivity;

public class ConnectionStatusMonitorImpl implements ConnectionMonitor {

	ConnectionStatus connectionStatus;
	boolean connected = true;

	boolean wasConnected = false;
	boolean wasDisconnected = false;

	public ConnectionStatusMonitorImpl(ConnectionStatus connectionStatus) {
		this.connectionStatus = connectionStatus;
	}

	@Override
	public boolean wasConnected() {
		return wasConnected;
	}

	@Override
	public boolean wasDisconnected() {
		return wasDisconnected;
	}

	public void update() {
		wasConnected = false;
		wasDisconnected = false;

		if (!connected && connectionStatus.isReachable()) {
			wasConnected = true;
			connected = true;
		} else if (connected && !connectionStatus.isReachable()) {
			wasDisconnected = true;
			connected = false;
		}

	}

}