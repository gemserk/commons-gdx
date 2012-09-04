package com.gemserk.commons.artemis.systems;

import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.gemserk.commons.artemis.components.ContainerComponent;
import com.gemserk.commons.artemis.components.StoreComponent;

public class ContainerSystem extends EntitySystem {

	@SuppressWarnings("unchecked")
	public ContainerSystem() {
		super(ContainerComponent.class);
	}

	@Override
	protected void disabled(Entity e) {
		ContainerComponent containerComponent = ContainerComponent.get(e);
		for (int i = 0; i < containerComponent.getChildren().size(); i++) {
			Entity child = containerComponent.getChildren().get(i);
			// what about the stores?

			StoreComponent storeComponent = StoreComponent.get(child);
			if (storeComponent != null)
				storeComponent.store.free(child);
			else
				child.delete();
		}
	}

	@Override
	protected void processEntities() {
	}

}