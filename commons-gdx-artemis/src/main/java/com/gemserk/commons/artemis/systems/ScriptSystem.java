package com.gemserk.commons.artemis.systems;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;
import com.gemserk.commons.artemis.components.ScriptComponent;
import com.gemserk.commons.artemis.scripts.Script;

public class ScriptSystem extends EntityProcessingSystem {

	ComponentMapper<ScriptComponent> scriptComponentMapper;

	public ScriptSystem() {
		super(ScriptComponent.class);
	}

	@Override
	protected void initialize() {
		super.initialize();
		scriptComponentMapper = new ComponentMapper<ScriptComponent>(ScriptComponent.class, world.getEntityManager());
	}

	@Override
	protected void added(Entity e) {
		super.added(e);
		Script[] scripts = scriptComponentMapper.get(e).getScripts();
		for (int i = 0; i < scripts.length; i++)
			scripts[i].init(world, e);
	}

	@Override
	protected void removed(Entity e) {
		Script[] scripts = scriptComponentMapper.get(e).getScripts();
		for (int i = 0; i < scripts.length; i++)
			scripts[i].dispose(world, e);
		super.removed(e);
	}

	@Override
	protected void process(Entity e) {
		Script[] scripts = scriptComponentMapper.get(e).getScripts();
		for (int i = 0; i < scripts.length; i++)
			scripts[i].update(world, e);
	}

}
