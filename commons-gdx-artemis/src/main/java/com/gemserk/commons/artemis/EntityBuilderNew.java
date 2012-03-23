package com.gemserk.commons.artemis;

import com.artemis.Component;
import com.artemis.Entity;
import com.artemis.World;
import com.gemserk.commons.artemis.templates.EntityTemplate;
import com.gemserk.componentsengine.utils.Parameters;

public class EntityBuilderNew {

	private final World world;

	private Entity entity;

	public EntityBuilderNew(World world) {
		this.world = world;
	}

	public EntityBuilderNew component(Component component) {
		checkEntityCreated();
		entity.addComponent(component);
		return this;
	}

	public EntityBuilderNew template(EntityTemplate template) {
		checkEntityCreated();
		template.apply(entity);
		return this;
	}

	public EntityBuilderNew template(EntityTemplate template, Parameters parameters) {
		checkEntityCreated();
		template.apply(entity, parameters);
		return this;
	}

	private void checkEntityCreated() {
		if (entity == null)
			entity = world.createEntity();
	}

	public Entity build() {
		checkEntityCreated();

		Entity builtEntity = entity;
		entity = null;

		builtEntity.refresh();
		return builtEntity;
	}

}
