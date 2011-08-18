package com.gemserk.commons.artemis.events;

import java.util.ArrayList;

import com.gemserk.componentsengine.utils.Pool;
import com.gemserk.componentsengine.utils.Pool.PoolObjectFactory;

class EventManagerImpl implements EventManager {

	private final Pool<Event> eventPool = new Pool<Event>(new PoolObjectFactory<Event>() {
		@Override
		public Event createObject() {
			return new Event();
		}
	}, 50);

	private ArrayList<Event> eventList = new ArrayList<Event>();

	@Override
	public void registerEvent(String id, Object source) {
		Event event = eventPool.newObject();
		event.setSource(source);
		event.setId(id);
		eventList.add(event);
	}

	@Override
	public void clear() {
		for (int i = 0; i < getEventCount(); i++)
			eventPool.free(eventList.get(i));
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