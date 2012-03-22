package com.gemserk.commons.artemis.events;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.gemserk.componentsengine.utils.RandomAccessSet;

public class EventManagerImpl implements EventManager {

	private final Map<String, RandomAccessSet<EventListener>> eventListeners = new HashMap<String, RandomAccessSet<EventListener>>();
	private final InternalEventManager eventManager = new InternalEventManager();

	@Override
	public void register(String eventId, EventListener listener) {
		RandomAccessSet<EventListener> listeners = getListenersForEvent(eventId);
		listeners.add(listener);
	}

	@Override
	public void unregister(String eventId, EventListener listener) {
		RandomAccessSet<EventListener> listeners = getListenersForEvent(eventId);
		listeners.remove(listener);
	}

	// TODO: use optimized RandomAccessMap to iterate better over the map keys.

	@Override
	public void unregister(EventListener listener) {
		Set<String> keySet = eventListeners.keySet();
		for (String key : keySet)
			eventListeners.get(key).remove(listener);
	}

	private RandomAccessSet<EventListener> getListenersForEvent(String event) {
		if (!eventListeners.containsKey(event))
			eventListeners.put(event, new RandomAccessSet<EventListener>());
		return eventListeners.get(event);
	}

	public void process(Event event) {
		// Don't know exactly if it should create the collection if it wasn't there, could be useful
		// because it is preparing for a probable listener registration.
		// ArrayList<EventListener> listeners = getListenersForEvent(event.getId());
		RandomAccessSet<EventListener> listeners = eventListeners.get(event.getId());
		if (listeners == null)
			return;
		for (int i = 0; i < listeners.size(); i++)
			listeners.get(i).onEvent(event);
	}
	
	@Override
	public void process() {
		for (int i = 0; i < eventManager.getEventCount(); i++) {
			Event event = eventManager.getEvent(i);
			process(event);
		}
		eventManager.clear();
	}

	@Override
	public void submit(String id, Object source) {
		eventManager.registerEvent(id, source);
	}

}