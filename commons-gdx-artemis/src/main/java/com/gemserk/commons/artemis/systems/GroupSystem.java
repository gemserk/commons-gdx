package com.gemserk.commons.artemis.systems;

import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.gemserk.commons.artemis.components.Components;
import com.gemserk.commons.artemis.components.GroupComponent;

public class GroupSystem extends EntitySystem {

	@SuppressWarnings("unchecked")
	public GroupSystem() {
		super(Components.groupComponentClass);
	}

	@Override
	protected void initialize() {
		super.initialize();
	}
	
	@Override
	protected void enabled(Entity e) {
		super.enabled(e);
		GroupComponent groupComponent = Components.getGroupComponent(e);
		world.getGroupManager().set(groupComponent.group, e);
	}
	
	@Override
	protected void disabled(Entity e) {
		super.disabled(e);
		world.getGroupManager().remove(e);
	}

	@Override
	protected void processEntities() {
	}

}
