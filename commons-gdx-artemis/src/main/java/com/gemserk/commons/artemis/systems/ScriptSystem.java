package com.gemserk.commons.artemis.systems;

import java.util.ArrayList;

import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;
import com.gemserk.commons.artemis.components.Components;
import com.gemserk.commons.artemis.scripts.Script;

public class ScriptSystem extends EntityProcessingSystem {

	@SuppressWarnings("unchecked")
	public ScriptSystem() {
		super(Components.scriptComponentClass);
	}

	@Override
	protected void enabled(Entity e) {
		super.enabled(e);
		ArrayList<Script> scripts = Components.getScriptComponent(e).getScripts();
		for (int i = 0; i < scripts.size(); i++) 
			scripts.get(i).init(world, e);
	}

	@Override
	protected void disabled(Entity e) {
		ArrayList<Script> scripts = Components.getScriptComponent(e).getScripts();
		for (int i = 0; i < scripts.size(); i++) 
			scripts.get(i).dispose(world, e);
		super.disabled(e);
	}

	@Override
	protected void process(Entity e) {
		ArrayList<Script> scripts = Components.getScriptComponent(e).getScripts();
		for (int i = 0; i < scripts.size(); i++) 
			scripts.get(i).update(world, e);
	}

}
