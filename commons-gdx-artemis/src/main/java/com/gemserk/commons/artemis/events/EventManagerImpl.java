package com.gemserk.commons.artemis.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EventManagerImpl implements EventManager {
	
	// TODO: use a pool of objects for the Events.

	private Map<String, Event> events;
	private ArrayList<Event> eventList;

	public EventManagerImpl() {
		events = new HashMap<String, Event>();
		eventList = new ArrayList<Event>();
	}

	@Override
	public void registerEvent(String id, Object source) {
		Event event = new Event();
		event.setSource(source);
		event.setId(id);
		events.put(id, event);
		eventList.add(event);
	}

	@Override
	public Event getEvent(String id) {
		return events.get(id);
	}

	@Override
	public void handled(Event e) {
		events.remove(e.getId());
		eventList.remove(e);
	}

	@Override
	public void clear() {
		events.clear();
		eventList.clear();
	}

	@Override
	public int getEventCount() {
		return eventList.size();
	}

	@Override
	public Event getEvent(int index) {
		if (index < 0 || index >= eventList.size())
			return null;
		return eventList.get(index);
	}
	
}