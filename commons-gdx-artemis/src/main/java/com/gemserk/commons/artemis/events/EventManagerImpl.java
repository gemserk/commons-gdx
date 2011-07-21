package com.gemserk.commons.artemis.events;

import java.util.HashMap;
import java.util.Map;

public class EventManagerImpl implements EventManager {

	private Map<String, Event> events;

	public EventManagerImpl() {
		events = new HashMap<String, Event>();
	}

	@Override
	public void registerEvent(String id, Object source) {
		Event event = new Event();
		event.setSource(source);
		event.setId(id);
		events.put(id, event);
	}

	@Override
	public Event getEvent(String id) {
		return events.get(id);
	}

	@Override
	public void handled(Event e) {
		events.remove(e.getId());
	}

	@Override
	public void clear() {
		events.clear();
	}
	
}