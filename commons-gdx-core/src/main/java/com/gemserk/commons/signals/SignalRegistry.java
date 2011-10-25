package com.gemserk.commons.signals;

/**
 * Provides a way to register and unregister SignalSenders and SignalReceivers for Signals.
 */
public interface SignalRegistry {

	/**
	 * Registers a SignalSender to send signals for a Signal identified by the given name.
	 * 
	 * @param signalName
	 *            The name of the Signal.
	 */
	SignalSender register(String signalName);

	/**
	 * Unregisters a SignalSender from a Signal to avoid sending new Signals.
	 * 
	 * @param signalSender
	 *            The SignalSender to unregister.
	 */
	void unregister(SignalSender signalSender);

	/**
	 * Subscribes a new SignalHandler to handle signals of the Signal identified by the given name.
	 * 
	 * @param signalName
	 *            The name of the Signal.
	 * @param signalHandler
	 *            The SignalHandler to be registered to handle signals.
	 */
	SignalReceiver subscribe(String signalName, SignalHandler signalHandler);

	/**
	 * Removes the SignalReceiver from the SignalRegistry.
	 * 
	 * @param signalReceiver
	 *            The SignalReceiver to unsubscribe.
	 */
	void unsubscribe(SignalReceiver signalReceiver);

}