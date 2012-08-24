package com.gemserk.commons.artemis.systems;

import java.util.ArrayList;

import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.gemserk.commons.artemis.components.Components;
import com.gemserk.commons.artemis.components.ScriptComponent;
import com.gemserk.commons.artemis.scripts.Script;
import com.gemserk.componentsengine.utils.RandomAccessMap;

public class ScriptSystem extends EntitySystem {

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
	protected void added(Entity e) {
		super.added(e);
		ArrayList<Script> scripts = ScriptComponent.get(e).getScripts();
		int size = scripts.size();
		for (int i = 0; i < size; i++) {
			Script script = scripts.get(i);
			script.added(world, e);
		}
	}
	
	@Override
	protected void removed(Entity e) {
		super.removed(e);
		ArrayList<Script> scripts = ScriptComponent.get(e).getScripts();
		int size = scripts.size();
		for (int i = 0; i < size; i++) {
			Script script = scripts.get(i);
			script.removed(world, e);
		}
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
	protected void processEntities() {
		RandomAccessMap<Entity, EntityComponents> allTheEntityComponents = factory.entityComponents;
		int entitiesSize = allTheEntityComponents.size();
		for (int entityIndex = 0; entityIndex < entitiesSize; entityIndex++) {
			EntityComponents entityComponents = allTheEntityComponents.get(entityIndex);
			ArrayList<Script> scripts = entityComponents.scripts;
			int size = scripts.size();
			for (int i = 0; i < size; i++) {
				Script script = scripts.get(i);
				Entity entity = allTheEntityComponents.getKey(entityIndex);
				script.update(world, entity);
			}
		}
	}

	@Override
	protected boolean checkProcessing() {
		return true;
	}

}
