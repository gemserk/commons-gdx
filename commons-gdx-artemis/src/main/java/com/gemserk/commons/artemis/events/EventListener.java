package com.gemserk.commons.artemis.events;

/**
 * Base class to use when registering a listener for an event.
 */
public class EventListener {

	/**
	 * Called when to handle the event.
	 * 
	 * @param event
	 *            The event to handle.
	 */
	public void onEvent(Event event) {}

}