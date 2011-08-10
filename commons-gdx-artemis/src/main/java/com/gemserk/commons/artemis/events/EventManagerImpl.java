package com.gemserk.commons.artemis.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.gemserk.componentsengine.utils.Pool;
import com.gemserk.componentsengine.utils.Pool.PoolObjectFactory;

public class EventManagerImpl implements EventManager {

	private final Pool<Event> eventPool = new Pool<Event>(new PoolObjectFactory<Event>() {
		@Override
		public Event createObject() {
			return new Event();
		}
	}, 50);

	private Map<String, Event> events = new HashMap<String, Event>();
	private ArrayList<Event> eventList = new ArrayList<Event>();

	@Override
	public void registerEvent(String id, Object source) {
		Event event = eventPool.newObject();
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
		eventPool.free(e);
	}

	@Override
	public void clear() {
		for (int i = 0; i < getEventCount(); i++) 
			eventPool.free(eventList.get(i));
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