package com.gemserk.commons.artemis.scripts;

import com.artemis.Entity;
import com.artemis.World;
import com.gemserk.commons.artemis.events.EventListenerManager;

public class EventSystemScript extends ScriptJavaImpl {
	
	private final EventListenerManager eventListenerManager;
	
	public EventSystemScript(EventListenerManager eventListenerManager) {
		this.eventListenerManager = eventListenerManager;
	}
	
	@Override
	public void update(World world, Entity e) {
		eventListenerManager.process();
	}
	
}