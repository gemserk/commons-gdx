package com.gemserk.commons.artemis.systems;

import com.artemis.Entity;

public interface RenderLayer {

	/**
	 * Performs initialization.
	 */
	void init();

	/**
	 * Performs disposal, to dispose internal stuff.
	 */
	void dispose();

	/**
	 * Returns true if the entity belongs to the RenderLayer, probably based on one of its components, false otherwise.
	 */
	boolean belongs(Entity entity);

	/**
	 * Adds a new entity to the layer if belongs returned true.
	 * 
	 * @param entity
	 *            The entity to be added.
	 */
	void add(Entity entity);

	/**
	 * Removes an Entity from the layer, if belongs returned true.
	 * 
	 * @param entity
	 *            The entity to be removed.
	 */
	void remove(Entity entity);

	/**
	 * Renders all the entities of the render layer.
	 */
	void render();

	/**
	 * Returns true if the layer is enabled, false otherwise.
	 */
	boolean isEnabled();

	/**
	 * Sets if the layer should be enabled or not.
	 */
	void setEnabled(boolean enabled);

}