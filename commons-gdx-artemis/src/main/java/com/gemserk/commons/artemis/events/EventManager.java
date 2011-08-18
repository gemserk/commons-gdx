package com.gemserk.commons.artemis.events;

public interface EventManager {

	/**
	 * Registers a new event specifying the id and the object which generated the event.
	 * 
	 * @param id
	 *            The id of the event to be registered.
	 * @param source
	 *            The object which generated the event.
	 */
	void registerEvent(String id, Object source);

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

	/**
	 * Process all events from registered using the EventManager interface.
	 */
	void process();


}