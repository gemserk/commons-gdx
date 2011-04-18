package com.gemserk.commons.artemis.systems;

import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.utils.ImmutableBag;
import com.gemserk.commons.artemis.components.AliveComponent;

public class AliveSystem extends EntitySystem {
	
	@SuppressWarnings("unchecked")
	public AliveSystem() {
		super(AliveComponent.class);
	}

	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		
		for (int i = 0; i < entities.size(); i++) {
			Entity entity = entities.get(i);
			AliveComponent aliveComponent = entity.getComponent(AliveComponent.class);
			// ESPLOTó
			int aliveTime = aliveComponent.getAliveTime() - world.getDelta();
			aliveComponent.setAliveTime(aliveTime);
			if (aliveTime <= 0) 
				world.deleteEntity(entity);
		}
		
	}
	
	@Override
	protected void removed(Entity entity) {
		
		AliveComponent aliveComponent = entity.getComponent(AliveComponent.class);
		if (aliveComponent != null)
			entity.removeComponent(aliveComponent);
		
	}

	@Override
	public void initialize() {

	}

	@Override
	protected boolean checkProcessing() {
		return true;
	}
}