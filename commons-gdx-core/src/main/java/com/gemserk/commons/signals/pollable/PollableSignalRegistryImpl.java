package com.gemserk.commons.signals.pollable;

import com.gemserk.commons.signals.SignalReceiver;
import com.gemserk.commons.signals.SignalRegistry;
import com.gemserk.commons.signals.SignalSender;

public class PollableSignalRegistryImpl {

	SignalRegistry signalRegistry;

	public PollableSignalRegistryImpl(SignalRegistry signalRegistry) {
		this.signalRegistry = signalRegistry;
	}

	public SignalSender register(String signalName) {
		return signalRegistry.register(signalName);
	}

	public void unregister(SignalSender signalSender) {
		signalRegistry.unregister(signalSender);
	}

	public PollableSignalReceiver subscribe(String signalName) {
		PollableSignalHandler pollableSignalHandler = new PollableSignalHandler();
		SignalReceiver signalReceiver = signalRegistry.subscribe(signalName, pollableSignalHandler);
		return new PollableSignalReceiver(this, signalReceiver, pollableSignalHandler);
	}

	public void unsubscribe(PollableSignalReceiver signalReceiver) {
		signalReceiver.signalReceiver.unsubscribe();
		// do nothing more for now...
	}

}
