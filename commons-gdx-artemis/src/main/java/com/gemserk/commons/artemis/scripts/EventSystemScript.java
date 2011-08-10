package com.gemserk.commons.artemis.scripts;

import com.artemis.Entity;
import com.artemis.World;
import com.gemserk.commons.artemis.events.Event;
import com.gemserk.commons.artemis.events.EventListenerManagerTest.EventListenerManager;
import com.gemserk.commons.artemis.events.EventManager;

public class EventSystemScript extends ScriptJavaImpl {
	
	private final EventManager eventManager;
	private final EventListenerManager eventListenerManager;
	
	public EventSystemScript(EventManager eventManager, EventListenerManager eventListenerManager) {
		this.eventManager = eventManager;
		this.eventListenerManager = eventListenerManager;
	}
	
	@Override
	public void update(World world, Entity e) {
		for (int i = 0; i < eventManager.getEventCount(); i++) {
			Event event = eventManager.getEvent(i);
			eventListenerManager.process(event);
		}
		eventManager.clear();
	}
	
}