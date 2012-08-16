package com.gemserk.commons.artemis.systems;

import java.util.ArrayList;

import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;
import com.gemserk.commons.artemis.components.ParentComponent;

public class HierarchySystem extends EntityProcessingSystem {

	@SuppressWarnings("unchecked")
	public HierarchySystem() {
		super(ParentComponent.class);
	}

	@Override
	protected void removed(Entity entity) {

		ParentComponent parentComponent = entity.getComponent(ParentComponent.class);
		
		// if for some reason the entity parent component was removed before this method was called
		if (parentComponent == null)
			return;
		
		entity.removeComponent(parentComponent);
		
		ArrayList<Entity> children = parentComponent.getChildren();

		// send the component to the components heaven, so it could be reused
		
		for (int i = 0; i < children.size(); i++) {
			Entity child = children.get(i);
			world.deleteEntity(child);
		}

	}

	@Override
	protected void process(Entity e) {
		
	}
	
}