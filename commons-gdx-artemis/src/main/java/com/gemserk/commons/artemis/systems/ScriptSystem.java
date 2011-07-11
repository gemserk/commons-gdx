package com.gemserk.commons.artemis.systems;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;
import com.gemserk.commons.artemis.Script;
import com.gemserk.commons.artemis.components.ScriptComponent;

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
		Script script = scriptComponentMapper.get(e).getScript();
		script.init(world, e);
	}
	
	@Override
	protected void removed(Entity e) {
		Script script = scriptComponentMapper.get(e).getScript();
		script.dispose(world, e);
		super.removed(e);
	}

	@Override
	protected void process(Entity e) {
		Script script = scriptComponentMapper.get(e).getScript();
		script.update(world, e);
	}

}
