package com.gemserk.commons.artemis.systems;

import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.badlogic.gdx.utils.IntMap;

public class ActiveEntitiesCounterSystem extends EntitySystem {

	IntMap<Entity> activeEntities = new IntMap<Entity>();

	@Override
	protected void processEntities() {
	}

	@Override
	protected boolean checkProcessing() {
		return false;
	}

	@Override
	protected void change(Entity e) {
		if (e.isEnabled())
			activeEntities.put(e.getId(), null);
		else
			activeEntities.remove(e.getId());
	}

	public int getActiveEntitiesCount() {
		return activeEntities.size;
	}
}
