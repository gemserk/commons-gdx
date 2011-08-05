package com.gemserk.commons.artemis.templates;

import com.artemis.Entity;
import com.artemis.World;
import com.gemserk.componentsengine.utils.Parameters;

public class EntityFactoryImpl implements EntityFactory {

	private World world;

	public EntityFactoryImpl(World world) {
		this.world = world;
	}

	@Override
	public Entity instantiate(EntityTemplate template) {
		return instantiate(template, null);
	}

	@Override
	public Entity instantiate(EntityTemplate template, Parameters parameters) {
		Entity entity = world.createEntity();
		if (parameters != null)
			template.apply(entity, parameters);
		else
			template.apply(entity);
		entity.refresh();
		return entity;
	}

}