package com.gemserk.commons.artemis;

import java.util.ArrayList;

import com.artemis.EntitySystem;
import com.artemis.World;

public class WorldWrapper {

	 World world;

	ArrayList<EntitySystem> systems;

	public WorldWrapper(World world) {
		this.world = world;
		systems = new ArrayList<EntitySystem>();
	}

	public void add(EntitySystem entitySystem) {
		world.getSystemManager().setSystem(entitySystem);
		systems.add(entitySystem);
	}

	public void init() {
		world.getSystemManager().initializeAll();
	}

	public void update(int delta) {

		world.loopStart();
		world.setDelta(delta);

		for (int i = 0; i < systems.size(); i++) {
			EntitySystem system = systems.get(i);
			system.process();
		}

	}

}