package com.gemserk.commons.artemis.scripts;

import com.artemis.Entity;
import com.artemis.World;
import com.gemserk.commons.artemis.events.EventManager;

public class EventSystemScript extends ScriptJavaImpl {
	
	private final EventManager eventManager;
	
	public EventSystemScript(EventManager eventListenerManager) {
		this.eventManager = eventListenerManager;
	}
	
	@Override
	public void update(World world, Entity e) {
		eventManager.process();
	}
	
}