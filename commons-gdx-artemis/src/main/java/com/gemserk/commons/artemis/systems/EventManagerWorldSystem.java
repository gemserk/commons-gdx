package com.gemserk.commons.artemis.systems;

import com.artemis.World;
import com.gemserk.commons.artemis.WorldSystemImpl;
import com.gemserk.commons.artemis.events.EventManager;

public class EventManagerWorldSystem extends WorldSystemImpl {
	
	EventManager eventManager;
	
	public void setEventManager(EventManager eventManager) {
		this.eventManager = eventManager;
	}

	public void process(World world) {
		eventManager.process();
	}
	
}
