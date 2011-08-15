package com.gemserk.commons.artemis.systems;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;
import com.gemserk.commons.artemis.components.ContainerComponent;
import com.gemserk.commons.artemis.components.OwnerComponent;

public class OwnerSystem extends EntityProcessingSystem {
	
	protected static final Logger logger = LoggerFactory.getLogger(OwnerSystem.class);
	private static final Class<OwnerComponent> ownerComponentClass = OwnerComponent.class;
	private static final Class<ContainerComponent> containerComponentClass = ContainerComponent.class;
	
	public OwnerSystem() {
		super(OwnerComponent.class);
	}

	@Override
	protected void added(Entity e) {
		OwnerComponent ownerComponent = e.getComponent(ownerComponentClass);
		if (ownerComponent.getOwner() == null)
			return;

		ContainerComponent containerComponent = ownerComponent.getOwner().getComponent(containerComponentClass);
		if (containerComponent == null)
			return;
		containerComponent.getChildren().add(e);
		
		if (logger.isDebugEnabled())
			logger.debug("Added entity " + e.getUniqueId() + " as child of " + ownerComponent.getOwner().getUniqueId());
	}

	protected void removed(Entity e) {
		OwnerComponent ownerComponent = e.getComponent(ownerComponentClass);
		if (ownerComponent.getOwner() == null)
			return;

		ContainerComponent containerComponent = ownerComponent.getOwner().getComponent(containerComponentClass);
		if (containerComponent == null)
			return;
		containerComponent.getChildren().remove(e);
		
		if (logger.isDebugEnabled())
			logger.debug("Removed entity " + e.getUniqueId() + " from children of " + ownerComponent.getOwner().getUniqueId());
	}

	@Override
	protected void process(Entity e) {

	}

}