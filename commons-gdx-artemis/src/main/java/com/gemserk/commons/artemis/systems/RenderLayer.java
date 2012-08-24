package com.gemserk.commons.artemis.systems;


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
	boolean belongs(Renderable renderable);

	/**
	 * Adds a new entity to the layer if belongs returned true.
	 * 
	 * @param entity
	 *            The entity to be added.
	 */
	void add(Renderable renderable);

	/**
	 * Removes an Entity from the layer, if belongs returned true.
	 * 
	 * @param entity
	 *            The entity to be removed.
	 */
	void remove(Renderable renderable);

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