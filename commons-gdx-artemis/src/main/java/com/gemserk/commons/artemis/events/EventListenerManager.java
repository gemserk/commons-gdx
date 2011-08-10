package com.gemserk.commons.artemis.events;


public interface EventListenerManager {

	/**
	 * Registers a new EventListener to listen the specified eventId.
	 */
	void register(String eventId, EventListener listener);

	/**
	 * Unregisters the specified EventListener from listening events with the specified eventId.
	 */
	void unregister(String eventId, EventListener listener);

	/**
	 * Unregisters the specified EventListener from listening all events it was registered for.
	 */
	void unregister(EventListener listener);

	/**
	 * Process the specified Event by calling all the EventListeners registered for it.
	 */
	void process(Event event);

}