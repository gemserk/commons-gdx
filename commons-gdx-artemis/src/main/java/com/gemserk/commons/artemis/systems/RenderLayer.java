package com.gemserk.commons.artemis.systems;

import com.artemis.Entity;

public interface RenderLayer {

	void init();

	void dispose();

	/**
	 * Returns true if the entity belongs to the RenderLayer, probably based on one of its components, false otherwise.
	 */
	boolean belongs(Entity entity);

	void add(Entity entity);

	void remove(Entity entity);

	/**
	 * Renders all the entities of the render layer.
	 */
	void render();

}