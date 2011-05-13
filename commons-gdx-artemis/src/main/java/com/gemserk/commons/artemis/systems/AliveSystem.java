package com.gemserk.commons.artemis.systems;

import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;
import com.gemserk.commons.artemis.components.AliveComponent;

public class AliveSystem extends EntityProcessingSystem {

	public AliveSystem() {
		super(AliveComponent.class);
	}

	@Override
	protected void process(Entity entity) {
		AliveComponent aliveComponent = entity.getComponent(AliveComponent.class);
		int aliveTime = aliveComponent.getAliveTime() - world.getDelta();
		aliveComponent.setAliveTime(aliveTime);
		if (aliveTime <= 0)
			world.deleteEntity(entity);
	}

	// @Override
	// protected void removed(Entity entity) {
	// System.out.println("entity removed from AliveSystem");
	// AliveComponent aliveComponent = entity.getComponent(AliveComponent.class);
	// if (aliveComponent != null)
	// entity.removeComponent(aliveComponent);
	// }

}