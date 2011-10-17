package com.gemserk.commons.artemis;

import com.artemis.World;

/**
 * A system that works over the Artemis World, not over entities with a specific component.
 */
public interface WorldSystem {

	/**
	 * Called to initialize the system for a given world.
	 * 
	 * @param world
	 *            The artemis world.
	 */
	void init(World world);

	/**
	 * Called to process the system over the given world.
	 * 
	 * @param world
	 *            The artemis world.
	 */
	void process(World world);

	/**
	 * Called to dispose the system.
	 * 
	 * @param world
	 *            The artemis world.
	 */
	void dispose(World world);

}
