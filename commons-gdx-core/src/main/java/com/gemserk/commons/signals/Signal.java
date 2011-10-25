package com.gemserk.commons.signals;

import java.util.ArrayList;

public class Signal {

	String name;
	ArrayList<SignalHandler> receivers;
	int senderCount;
	
	/**
	 * Used by the SignalSender and the SignalReceiver to unregister/unsubscribe.
	 */
	SignalRegistry signalRegistry;
	
	public String getName() {
		return name;
	}

	public Signal() {
		senderCount = 0;
		receivers = new ArrayList<SignalHandler>();
	}

}