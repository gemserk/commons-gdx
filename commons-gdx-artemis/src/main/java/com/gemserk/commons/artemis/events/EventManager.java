package com.gemserk.commons.artemis.events;

/**
 * Provides a way to register/unregister events and listeners to handle the those events.
 */
public interface EventManager {

	/**
	 * Submits a new event specifying the id and the object which generated the event.
	 * 
	 * @param eventId
	 *            The identifier of the event to be registered.
	 * @param eventSource
	 *            The object which generated the event (or another data to send with the event).
	 */
	void submit(String eventId, Object eventSource);

	/**
	 * Registers a new EventListener to listen the specified eventId.
	 * 
	 * @param eventId
	 *            The event identifier.
	 * @param eventListener
	 *            The EventListener to register for the specified event.
	 */
	void register(String eventId, EventListener eventListener);

	/**
	 * Unregisters the specified EventListener from listening events with the specified eventId.
	 * 
	 * @param eventId
	 *            The Event identifier.
	 * @param eventListener
	 *            The EventListener to unregister for the specified event.
	 */
	void unregister(String eventId, EventListener eventListener);

	/**
	 * Unregisters the specified EventListener from listening all events it was registered for.
	 * 
	 * @param eventListener
	 *            The EventListener to unregister from all the events it was registered to listen.
	 */
	void unregister(EventListener listener);

	/**
	 * Process all events from registered using the EventManager interface.
	 */
	void process();

}