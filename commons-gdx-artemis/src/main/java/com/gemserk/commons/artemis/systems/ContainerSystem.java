package com.gemserk.commons.artemis.systems;

import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;
import com.gemserk.commons.artemis.components.ContainerComponent;

public class ContainerSystem extends EntityProcessingSystem {

	public ContainerSystem() {
		super(ContainerComponent.class);
	}

	@Override
	protected void removed(Entity e) {
		ContainerComponent containerComponent = e.getComponent(ContainerComponent.class);
		for (int i = 0; i < containerComponent.getChildren().size(); i++)
			containerComponent.getChildren().get(i).delete();
	}

	@Override
	protected void process(Entity e) {

	}

}