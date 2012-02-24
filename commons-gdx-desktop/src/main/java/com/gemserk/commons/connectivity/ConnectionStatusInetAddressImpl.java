package com.gemserk.commons.connectivity;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ConnectionStatusInetAddressImpl implements ConnectionStatus {

	private final String host;

	public ConnectionStatusInetAddressImpl(String host) {
		this.host = host;
	}

	@Override
	public boolean isReachable() {

		try {
			InetAddress address = InetAddress.getByName(host);
			if (address != null)
				return true;
		} catch (UnknownHostException e) {

		}

		return false;

	}

}