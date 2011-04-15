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
		
//		for (int i = 0; i < entities.size(); i++) {
//			
//			Entity entity = entities.get(i);
//			
//			ChildComponent childComponent = entity.getComponent(ChildComponent.class);
//			
//			if (!world.getEntityManager().isActive(childComponent.getParent().getId()))
//				world.deleteEntity(entity);
//			
//		}
		
	}
	
	@Override
	protected void removed(Entity e) {
		
		ParentComponent parentComponent = e.getComponent(ParentComponent.class);
		ArrayList<Entity> children = parentComponent.getChildren();
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