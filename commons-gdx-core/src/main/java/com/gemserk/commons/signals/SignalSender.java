package com.gemserk.commons.signals;

public class SignalSender {

	Signal signal;

	/**
	 * send a signal with the specified object to all receivers of the signal.
	 * 
	 * @param source
	 *            The object who sent the signal.
	 */
	public void signal(Object source) {
		if (signal == null)
			return;
		for (int i = 0; i < signal.receivers.size(); i++)
			signal.receivers.get(i).onSignal(signal, source);
	}

	public void unregister() {
		if (signal == null)
			return;
		signal.signalRegistry.unregister(this);
	}

}