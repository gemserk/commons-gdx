package com.gemserk.commons.signals;

public class SignalReceiver {

	Signal signal;
	SignalHandler signalHandler;

	public void unsubscribe() {
		if (signal == null)
			return;
		signal.signalRegistry.unsubscribe(this);
	}

}