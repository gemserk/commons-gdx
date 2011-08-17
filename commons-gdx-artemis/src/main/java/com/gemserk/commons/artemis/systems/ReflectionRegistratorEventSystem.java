package com.gemserk.commons.artemis.systems;

import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;
import com.gemserk.commons.artemis.components.ScriptComponent;
import com.gemserk.commons.artemis.events.EventListenerManager;
import com.gemserk.commons.artemis.events.reflection.EventListenerReflectionRegistrator;
import com.gemserk.commons.artemis.scripts.Script;

public class ReflectionRegistratorEventSystem extends EntityProcessingSystem {

	private static final Class<ScriptComponent> scriptComponentClass = ScriptComponent.class;
	private final EventListenerManager eventListenerManager;
	private final EventListenerReflectionRegistrator eventListenerReflectionRegistrator;
	
	public ReflectionRegistratorEventSystem(EventListenerManager eventListenerManager) {
		super(ScriptComponent.class);
		this.eventListenerManager = eventListenerManager;
		this.eventListenerReflectionRegistrator = new EventListenerReflectionRegistrator();
	}

	@Override
	protected void added(Entity e) {
		super.added(e);
		Script[] scripts = e.getComponent(scriptComponentClass).getScripts();
		for (int i = 0; i < scripts.length; i++) {
			Script script = scripts[i];
			eventListenerReflectionRegistrator.registerEventListeners(script, eventListenerManager);
		}
	}

	@Override
	protected void removed(Entity e) {
		Script[] scripts = e.getComponent(scriptComponentClass).getScripts();
		for (int i = 0; i < scripts.length; i++) {
			Script script = scripts[i];
			eventListenerReflectionRegistrator.unregisterEventListeners(script, eventListenerManager);
		}
	}

	@Override
	protected void process(Entity e) {
		
	}

}
