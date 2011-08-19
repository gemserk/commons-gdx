package com.gemserk.commons.artemis.templates;

import com.artemis.Entity;
import com.artemis.World;
import com.gemserk.componentsengine.utils.Parameters;
import com.gemserk.componentsengine.utils.ParametersWrapper;

public class EntityFactoryImpl implements EntityFactory {

	private Parameters parameters = new ParametersWrapper();
	private World world;

	public EntityFactoryImpl(World world) {
		this.world = world;
	}

	@Override
	public Entity instantiate(EntityTemplate template) {
		return instantiate(template, parameters);
		// Entity entity = world.createEntity();
		// template.apply(entity);
		// entity.refresh();
		// return entity;
		// return instantiate(template, null);
	}

	@Override
	public Entity instantiate(EntityTemplate template, Parameters parameters) {
		Entity entity = world.createEntity();
		template.setParameters(parameters);
		template.apply(entity);
		entity.refresh();
		return entity;
		// return instantiate(template);
		// Entity entity = world.createEntity();
		// if (parameters != null)
		// template.setParameters(entity, parameters);
		// else
		// template.apply(entity);
		// entity.refresh();
		// return entity;
	}

}