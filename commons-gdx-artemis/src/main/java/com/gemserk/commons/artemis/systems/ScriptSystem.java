package com.gemserk.commons.artemis.systems;

import java.util.ArrayList;

import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;
import com.gemserk.commons.artemis.components.Components;
import com.gemserk.commons.artemis.scripts.Script;

public class ScriptSystem extends EntityProcessingSystem {

	class EntityComponents {
		public ArrayList<Script> scripts;
	}

	class Factory extends EntityComponentsFactory<EntityComponents> {

		@Override
		public EntityComponents newInstance() {
			return new EntityComponents();
		}

		@Override
		public void free(EntityComponents entityComponent) {
			entityComponent.scripts = null;
		}

		@Override
		public void load(Entity e, EntityComponents entityComponent) {
			entityComponent.scripts = Components.getScriptComponent(e).getScripts();
		}

	}

	private Factory factory;

	@SuppressWarnings("unchecked")
	public ScriptSystem() {
		super(Components.scriptComponentClass);
		this.factory = new Factory();
	}

	@Override
	protected void enabled(Entity e) {
		super.enabled(e);
		EntityComponents entityComponents = factory.add(e);
		ArrayList<Script> scripts = entityComponents.scripts;
		int size = scripts.size();
		for (int i = 0; i < size; i++) {
			Script script = scripts.get(i);
			script.enabled(world, e);
		}
	}

	@Override
	protected void disabled(Entity e) {
		EntityComponents entityComponents = factory.get(e);
		ArrayList<Script> scripts = entityComponents.scripts;
		
		int size = scripts.size();
		for (int i = 0; i < size; i++) {
			Script script = scripts.get(i);
			script.disabled(world, e);
		}
		
		factory.remove(e);
		super.disabled(e);
	}

	@Override
	protected void process(Entity e) {
		EntityComponents entityComponents = factory.get(e);
		ArrayList<Script> scripts = entityComponents.scripts;
		int size = scripts.size();
		for (int i = 0; i < size; i++) {
			Script script = scripts.get(i);
			script.update(world, e);
		}
	}

}
