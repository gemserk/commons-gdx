package com.gemserk.commons.artemis;

import java.util.ArrayList;

import com.artemis.EntitySystem;
import com.artemis.World;
import com.badlogic.gdx.utils.Disposable;

public class WorldWrapper {

	protected World world;

	protected ArrayList<EntitySystem> updateSystems;
	protected ArrayList<EntitySystem> renderSystems;

	protected ArrayList<WorldSystem> worldUpdateSystems;
	protected ArrayList<WorldSystem> worldRenderSystems;

	public World getWorld() {
		return world;
	}

	public WorldWrapper(World world) {
		this.world = world;
		updateSystems = new ArrayList<EntitySystem>();
		renderSystems = new ArrayList<EntitySystem>();

		worldUpdateSystems = new ArrayList<WorldSystem>();
		worldRenderSystems = new ArrayList<WorldSystem>();
	}

	public void addUpdateSystem(EntitySystem entitySystem) {
		world.getSystemManager().setSystem(entitySystem);
		updateSystems.add(entitySystem);
	}
	
	public void addUpdateSystem(WorldSystem worldSystem) {
		worldUpdateSystems.add(worldSystem);
	}

	public void addRenderSystem(EntitySystem entitySystem) {
		world.getSystemManager().setSystem(entitySystem);
		renderSystems.add(entitySystem);
	}
	
	public void addRenderSystem(WorldSystem worldSystem) {
		worldRenderSystems.add(worldSystem);
	}

	public void init() {
		world.getSystemManager().initializeAll();
		
		for (int i = 0; i < worldUpdateSystems.size(); i++) 
			worldUpdateSystems.get(i).init(world);

		for (int i = 0; i < worldRenderSystems.size(); i++) 
			worldRenderSystems.get(i).init(world);

	}

	public void update(int delta) {
		world.loopStart();
		world.setDelta(delta);
		
		for (int i = 0; i < updateSystems.size(); i++) {
			EntitySystem system = updateSystems.get(i);
			system.process();
		}
		
		for (int i = 0; i < worldUpdateSystems.size(); i++) 
			worldUpdateSystems.get(i).process(world);
	}

	public void render() {
		
		for (int i = 0; i < renderSystems.size(); i++) {
			EntitySystem system = renderSystems.get(i);
			system.process();
		}
		
		for (int i = 0; i < worldRenderSystems.size(); i++) 
			worldRenderSystems.get(i).process(world);
	}

	/**
	 * Called to dispose the world and all entity systems, be aware you can't use is again without reinitializing it.
	 */
	public void dispose() {

		for (int i = 0; i < updateSystems.size(); i++) {
			EntitySystem system = updateSystems.get(i);
			// should be part of the EntitySystem interface.
			// system.dispose();
			if (system instanceof Disposable)
				((Disposable) system).dispose();
		}

		for (int i = 0; i < renderSystems.size(); i++) {
			EntitySystem system = renderSystems.get(i);
			// system.dispose();
			if (system instanceof Disposable)
				((Disposable) system).dispose();
		}
		
		for (int i = 0; i < worldUpdateSystems.size(); i++) 
			worldUpdateSystems.get(i).dispose(world);

		for (int i = 0; i < worldRenderSystems.size(); i++) 
			worldRenderSystems.get(i).dispose(world);
	}

}