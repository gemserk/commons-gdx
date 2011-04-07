package com.gemserk.commons.artemis.systems;

import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.utils.ImmutableBag;
import com.gemserk.commons.artemis.components.ChildComponent;

public class HierarchySystem extends EntitySystem {
	
	@SuppressWarnings("unchecked")
	public HierarchySystem() {
		super(ChildComponent.class);
	}

	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		
		for (int i = 0; i < entities.size(); i++) {
			
			Entity entity = entities.get(i);
			
			ChildComponent childComponent = entity.getComponent(ChildComponent.class);
			
			if (!world.getEntityManager().isActive(childComponent.getParent().getId()))
				world.deleteEntity(entity);
			
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