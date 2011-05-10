package com.gemserk.commons.artemis;

import java.util.ArrayList;

import com.artemis.EntitySystem;
import com.artemis.World;

public class WorldWrapper {

	private World world;

	private ArrayList<EntitySystem> updateSystems;
	
	private ArrayList<EntitySystem> renderSystems;

	public WorldWrapper(World world) {
		this.world = world;
		updateSystems = new ArrayList<EntitySystem>();
		renderSystems = new ArrayList<EntitySystem>();
	}

	public void addUpdateSystem(EntitySystem entitySystem) {
		world.getSystemManager().setSystem(entitySystem);
		updateSystems.add(entitySystem);
	}
	
	public void addRenderSystem(EntitySystem entitySystem) {
		world.getSystemManager().setSystem(entitySystem);
		renderSystems.add(entitySystem);
	}

	public void init() {
		world.getSystemManager().initializeAll();
	}

	public void update(int delta) {
		world.loopStart();
		world.setDelta(delta);
		for (int i = 0; i < updateSystems.size(); i++) {
			EntitySystem system = updateSystems.get(i);
			system.process();
		}
	}
	
	public void render() {
		for (int i = 0; i < renderSystems.size(); i++) {
			EntitySystem system = renderSystems.get(i);
			system.process();
		}
	}

}