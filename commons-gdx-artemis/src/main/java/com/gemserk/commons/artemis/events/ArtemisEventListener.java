package com.gemserk.commons.artemis.events;

import com.artemis.Entity;
import com.artemis.World;

public class ArtemisEventListener extends EventListener {
	
	protected World world;
	protected Entity entity;

	public ArtemisEventListener(World world, Entity entity) {
		this.world = world;
		this.entity = entity;
	}
	
}
