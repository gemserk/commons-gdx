package com.gemserk.commons.signals;

/**
 * Implementation of SignalHandler to be polled to know if one specific signal was sent.
 * 
 * @author acoppes
 */
public class PollableSignalHandler implements SignalHandler, PollableSignal {

	boolean signalSent;
	Signal signal;
	Object source;

	@Override
	public Signal getSignal() {
		return signal;
	}

	@Override
	public Object getSource() {
		return source;
	}

	@Override
	public void onSignal(Signal signal, Object source) {
		this.signal = signal;
		this.source = source;
		signalSent = true;
	}

	@Override
	public boolean signalSent() {
		if (signalSent) {
			signalSent = false;
			return true;
		}
		return signalSent;
	}

}