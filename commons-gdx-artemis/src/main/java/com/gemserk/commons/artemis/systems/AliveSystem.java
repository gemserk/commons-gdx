package com.gemserk.commons.artemis.systems;

import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;
import com.gemserk.commons.artemis.components.AliveComponent;
import com.gemserk.commons.artemis.components.StoreComponent;
import com.gemserk.commons.gdx.GlobalTime;

public class AliveSystem extends EntityProcessingSystem {

	@SuppressWarnings("unchecked")
	public AliveSystem() {
		super(AliveComponent.class);
	}

	@Override
	protected void process(Entity e) {
		AliveComponent aliveComponent = e.getComponent(AliveComponent.class);
		float aliveTime = aliveComponent.getTime() - GlobalTime.getDelta();
		aliveComponent.setTime(aliveTime);
		if (aliveTime <= 0) {
			StoreComponent storeComponent = StoreComponent.get(e);
			if (storeComponent != null)
				storeComponent.store.free(e);
			else
				e.delete();
		}
	}

}