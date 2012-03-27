package com.gemserk.commons.artemis.systems;

import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;
import com.gemserk.commons.artemis.components.ContainerComponent;

public class ContainerSystem extends EntityProcessingSystem {

	@SuppressWarnings("unchecked")
	public ContainerSystem() {
		super(ContainerComponent.class);
	}

	@Override
	protected void removed(Entity e) {
		ContainerComponent containerComponent = ContainerComponent.get(e);
		for (int i = 0; i < containerComponent.getChildren().size(); i++) {
			Entity child = containerComponent.getChildren().get(i);
			// what about the stores?
			child.delete();
		}
	}

	@Override
	protected void process(Entity e) {

	}

}