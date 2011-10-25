package com.gemserk.commons.signals.pollable;

import com.gemserk.commons.signals.Signal;

/**
 * Provides an interface to poll to know if a signal was sent or not.
 * 
 * @author acoppes
 */
public interface PollableSignal {

	/**
	 * If signalSent returned true, this method returns the Signal instance.
	 * 
	 * @return The Signal instance for the last signal sent.
	 */
	Signal getSignal();

	/**
	 * If signalSent returned true, this method returns the source Object instance.
	 * 
	 * @return The Object which originated the signal.
	 */
	Object getSource();

	/**
	 * Returns true, only once, if onSignal() method was called by the SignalSender, false otherwise.
	 */
	boolean signalSent();

}