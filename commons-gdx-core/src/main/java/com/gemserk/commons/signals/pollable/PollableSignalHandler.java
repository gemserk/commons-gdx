package com.gemserk.commons.signals.pollable;

import com.gemserk.commons.signals.Signal;
import com.gemserk.commons.signals.SignalHandler;

/**
 * Implementation of SignalHandler to be polled to know if one specific signal was sent.
 * 
 * @author acoppes
 */
public class PollableSignalHandler implements SignalHandler, PollableSignal {

	int signalCount;
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
		// don't accept more than one signal for now until the signal was consumed, also it should conserve the same signal/source isntances
		if (signalCount > 0)
			return;
		this.signal = signal;
		this.source = source;
		signalCount++;
	}

	@Override
	public boolean signalSent() {
		if (signalCount > 0) {
			// consumes the signal 
			signalCount--;
			return true;
		}
		return false;
	}

}