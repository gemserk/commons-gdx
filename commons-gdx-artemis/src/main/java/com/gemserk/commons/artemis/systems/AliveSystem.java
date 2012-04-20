package com.gemserk.commons.artemis.systems;

import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;
import com.gemserk.commons.artemis.components.AliveComponent;
import com.gemserk.commons.gdx.GlobalTime;

public class AliveSystem extends EntityProcessingSystem {

	public AliveSystem() {
		super(AliveComponent.class);
	}

	@Override
	protected void process(Entity entity) {
		AliveComponent aliveComponent = entity.getComponent(AliveComponent.class);
		float aliveTime = aliveComponent.getTime() - GlobalTime.getDelta();
		aliveComponent.setTime(aliveTime);
		if (aliveTime <= 0) 
			entity.delete();
	}

}