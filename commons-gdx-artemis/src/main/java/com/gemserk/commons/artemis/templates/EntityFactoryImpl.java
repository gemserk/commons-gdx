package com.gemserk.commons.artemis.templates;

import com.artemis.Entity;
import com.artemis.World;
import com.gemserk.commons.artemis.systems.EntityTemplateTest;
import com.gemserk.componentsengine.utils.Parameters;

public class EntityFactoryImpl implements EntityFactory {

	ParametersWithFallBack parametersWithFallBack;
	World world;

	public EntityFactoryImpl(World world) {
		this.world = world;
		parametersWithFallBack = new ParametersWithFallBack();
	}

	@Override
	public Entity instantiate(EntityTemplate template) {
		return internalInstantiate(template, template.getDefaultParameters());
	}

	@Override
	public Entity instantiate(EntityTemplate template, Parameters parameters) {
		parametersWithFallBack.setFallBackParameters(template.getDefaultParameters());
		parametersWithFallBack.setParameters(parameters);
		return internalInstantiate(template, parametersWithFallBack);
	}

	private Entity internalInstantiate(EntityTemplate template, Parameters parameters) {
		Entity entity = world.createEntity();
		template.apply(entity, parameters);
		entity.refresh();
		return entity;
	}

}