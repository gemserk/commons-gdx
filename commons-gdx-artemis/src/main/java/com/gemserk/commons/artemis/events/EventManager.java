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
	 * Removes all the registered events.
	 */
	void clear();

	/**
	 * Returns how many registered events are.
	 */
	int getEventCount();

	/**
	 * Returns the event registered in that index?
	 */
	Event getEvent(int index);

}