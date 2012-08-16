package com.gemserk.commons.artemis.scripts;

import com.artemis.Entity;
import com.artemis.World;

public interface Script {
	
	/**
	 * Should be called each time the Entity is removed from the world.
	 * 
	 * @param world
	 *            The Artemis World where the Entity is.
	 * @param e
	 *            The Entity owner of the Script.
	 */
	void added(World world, Entity e);
	
	/**
	 * Should be called each time the Entity is enabled, assuming it is already in the world.
	 * 
	 * @param world
	 *            The Artemis World where the Entity is.
	 * @param e
	 *            The Entity owner of the Script.
	 */
	void enabled(World world, Entity e);

	/**
	 * Should be called in each World's update.
	 * 
	 * @param world
	 *            The Artemis World where the Entity is.
	 * @param e
	 *            The Entity owner of the Script.
	 */
	void update(World world, Entity e);

	/**
	 * Should be called each time the entity is disabled.
	 * 
	 * @param world
	 *            The Artemis World where the Entity is.
	 * @param e
	 *            The Entity owner of the Script.
	 */
	void disabled(World world, Entity e);
	
	/**
	 * Should be called each time the Entity is enabled, assuming it is already in the world.
	 * 
	 * @param world
	 *            The Artemis World where the Entity is.
	 * @param e
	 *            The Entity owner of the Script.
	 */
	void removed(World world, Entity e);

}
