package com.gemserk.commons.artemis.systems;

import com.artemis.Entity;

public interface RenderLayer {

	void init();

	void dispose();

	boolean belongs(Entity entity);

	void add(Entity entity);

	void remove(Entity entity);

	void draw();

}