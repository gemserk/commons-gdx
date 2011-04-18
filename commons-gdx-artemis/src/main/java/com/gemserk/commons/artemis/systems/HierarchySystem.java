package com.gemserk.commons.artemis.systems;

import java.util.ArrayList;

import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.utils.ImmutableBag;
import com.gemserk.commons.artemis.components.ParentComponent;

public class HierarchySystem extends EntitySystem {

	@SuppressWarnings("unchecked")
	public HierarchySystem() {
		super(ParentComponent.class);
	}

	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {

		// for (int i = 0; i < entities.size(); i++) {
		//
		// Entity entity = entities.get(i);
		//
		// ChildComponent childComponent = entity.getComponent(ChildComponent.class);
		//
		// if (!world.getEntityManager().isActive(childComponent.getParent().getId()))
		// world.deleteEntity(entity);
		//
		// }

	}

	// @Override
	// protected void added(Entity entity) {
	// ParentComponent parentComponent = entity.getComponent(ParentComponent.class);
	// ArrayList<Entity> children = parentComponent.getChildren();
	// for (int i = 0; i < children.size(); i++) {
	// Entity child = children.get(i);
	// ChildComponent childComponent = child.getComponent(ChildComponent.class);
	// if (childComponent == null) {
	// childComponent = new ChildComponent(entity);
	// child.addComponent(childComponent);
	// child.refresh();
	// } else {
	// childComponent.setParent(entity);
	// }
	// }
	// }

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
	public void initialize() {

	}

	@Override
	protected boolean checkProcessing() {
		return true;
	}
}