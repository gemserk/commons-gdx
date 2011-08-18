package com.gemserk.commons.artemis.scripts;

import com.artemis.Entity;
import com.artemis.World;
import com.gemserk.commons.artemis.events.EventManager;

public class EventSystemScript extends ScriptJavaImpl {
	
	private final EventManager eventListenerManager;
	
	public EventSystemScript(EventManager eventListenerManager) {
		this.eventListenerManager = eventListenerManager;
	}
	
	@Override
	public void update(World world, Entity e) {
		eventListenerManager.process();
	}
	
}