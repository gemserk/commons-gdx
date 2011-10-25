package com.gemserk.commons.signals.pollable;

import com.gemserk.commons.signals.SignalReceiver;

public class PollableSignalReceiver {

	private final PollableSignalRegistryImpl pollableSignalRegistry;

	PollableSignalHandler pollableSignalHandler;
	SignalReceiver signalReceiver;
	
	public PollableSignal getPollableSignal() {
		return pollableSignalHandler;
	}
	
	public PollableSignalReceiver(PollableSignalRegistryImpl pollableSignalRegistry, SignalReceiver signalReceiver, PollableSignalHandler pollableSignalHandler) {
		this.pollableSignalRegistry = pollableSignalRegistry;
		this.pollableSignalHandler = pollableSignalHandler;
		this.signalReceiver = signalReceiver;
	}

	public void unsubscribe() {
		pollableSignalRegistry.unsubscribe(this);
	}

}