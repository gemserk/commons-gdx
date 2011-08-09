package com.gemserk.commons.artemis;

import com.artemis.Entity;
import com.artemis.World;

public interface Script {

	/**
	 * Called when the Entity is added to the World.
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
	 * Called when the Entity is removed from the world.
	 * 
	 * @param world
	 *            The Artemis World where the Entity is.
	 * @param e
	 *            The Entity owner of the Script.
	 */
	void dispose(World world, Entity e);

}
