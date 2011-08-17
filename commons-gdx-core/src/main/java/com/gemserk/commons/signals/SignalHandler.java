package com.gemserk.commons.signals;

/**
 * Implement it and register it on the SignalRegistry to receive signals.
 * 
 * @author acoppes
 * 
 */
public interface SignalHandler {

	/**
	 * Called when a signal was sent to all SignalHandlers of a Signal and this SignalHandler was in.
	 * 
	 * @param signal
	 *            The signal sent.
	 * @param source
	 *            The object who sent the signal.
	 */
	void onSignal(Signal signal, Object source);

}