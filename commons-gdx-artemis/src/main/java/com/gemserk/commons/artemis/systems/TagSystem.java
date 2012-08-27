package com.gemserk.commons.artemis.systems;

import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.gemserk.commons.artemis.components.TagComponent;

public class TagSystem extends EntitySystem {

	private static final Class<TagComponent> tagComponentClass = TagComponent.class;

	@SuppressWarnings("unchecked")
	public TagSystem() {
		super(TagComponent.class);
	}

	@Override
	protected void initialize() {
		super.initialize();
	}

	@Override
	protected void added(Entity e) {
		super.added(e);
		TagComponent tagComponent = e.getComponent(tagComponentClass);
		world.getTagManager().register(tagComponent.getTag(), e);
	}

	@Override
	protected void removed(Entity e) {
		super.removed(e);
		TagComponent tagComponent = e.getComponent(tagComponentClass);
		Entity entityWithTag = world.getTagManager().getEntity(tagComponent.getTag());
		if (entityWithTag == null)
			return;
		if (entityWithTag != e)
			return;
		world.getTagManager().unregister(tagComponent.getTag());
	}

	@Override
	protected void processEntities() {
	}
}
