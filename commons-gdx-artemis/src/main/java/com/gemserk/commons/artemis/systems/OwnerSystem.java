package com.gemserk.commons.artemis.systems;

import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;
import com.gemserk.commons.artemis.components.ContainerComponent;
import com.gemserk.commons.artemis.components.OwnerComponent;

public class OwnerSystem extends EntityProcessingSystem {

	public OwnerSystem() {
		super(OwnerComponent.class);
	}

	@Override
	protected void added(Entity e) {
		OwnerComponent ownerComponent = e.getComponent(OwnerComponent.class);
		if (ownerComponent.getOwner() == null)
			return;

		ContainerComponent containerComponent = ownerComponent.getOwner().getComponent(ContainerComponent.class);
		containerComponent.getChildren().add(e);
	}

	protected void removed(Entity e) {
		OwnerComponent ownerComponent = e.getComponent(OwnerComponent.class);
		if (ownerComponent.getOwner() == null)
			return;

		ContainerComponent containerComponent = ownerComponent.getOwner().getComponent(ContainerComponent.class);
		if (containerComponent == null)
			return;

		containerComponent.getChildren().remove(e);
	}

	@Override
	protected void process(Entity e) {

	}

}