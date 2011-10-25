package com.gemserk.commons.signals;

/**
 * Implementation of SignalHandler to be polled to know if one specific signal was sent.
 */
public class PollableSignalHandler implements SignalHandler {

	boolean signalSent;
	Signal signal;
	Object source;

	/**
	 * If signalSent returned true, this method returns the Signal instance.
	 * 
	 * @return The Signal instance for the last signal sent.
	 */
	public Signal getSignal() {
		return signal;
	}

	/**
	 * If signalSent returned true, this method returns the source Object instance.
	 * 
	 * @return The Object which originated the signal.
	 */
	public Object getSource() {
		return source;
	}

	@Override
	public void onSignal(Signal signal, Object source) {
		this.signal = signal;
		this.source = source;
		signalSent = true;
	}

	/**
	 * Returns true, only once, if onSignal() method was called by the SignalSender, false otherwise.
	 */
	public boolean signalSent() {
		if (signalSent) {
			signalSent = false;
			return true;
		}
		return signalSent;
	}

}