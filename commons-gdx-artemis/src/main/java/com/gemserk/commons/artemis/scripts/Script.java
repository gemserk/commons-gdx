package com.gemserk.commons.artemis.scripts;

import com.artemis.Entity;
import com.artemis.World;

public interface Script {
	
	/**
	 * Called each time the Entity is enabled, assuming it is already in the world.
	 * 
	 * @param world
	 *            The Artemis World where the Entity is.
	 * @param e
	 *            The Entity owner of the Script.
	 */
	void init(World world, Entity e);

	/**
	 * Called in each World's update.
	 * 
	 * @param world
	 *            The Artemis World where the Entity is.
	 * @param e
	 *            The Entity owner of the Script.
	 */
	void update(World world, Entity e);

	/**
	 * Called each time the entity is disabled.
	 * 
	 * @param world
	 *            The Artemis World where the Entity is.
	 * @param e
	 *            The Entity owner of the Script.
	 */
	void dispose(World world, Entity e);

}
