package com.gemserk.commons.artemis.systems;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;
import com.gemserk.commons.artemis.components.ContainerComponent;

public class ContainerSystem extends EntityProcessingSystem {

	protected static final Logger logger = LoggerFactory.getLogger(ContainerSystem.class);
	private static final Class<ContainerComponent> containerComponentClass = ContainerComponent.class;

	public ContainerSystem() {
		super(ContainerComponent.class);
	}

	@Override
	protected void removed(Entity e) {
		ContainerComponent containerComponent = e.getComponent(containerComponentClass);
		for (int i = 0; i < containerComponent.getChildren().size(); i++) {
			Entity child = containerComponent.getChildren().get(i);
			child.delete();
			if (logger.isDebugEnabled())
				logger.debug("Deleting entity " + child.getUniqueId() + " from children of " + e.getUniqueId());
		}
	}

	@Override
	protected void process(Entity e) {

	}

}