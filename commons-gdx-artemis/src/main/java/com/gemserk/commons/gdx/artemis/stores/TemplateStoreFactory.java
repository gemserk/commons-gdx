package com.gemserk.commons.gdx.artemis.stores;

import com.artemis.Entity;
import com.gemserk.commons.artemis.components.StoreComponent;
import com.gemserk.commons.artemis.templates.EntityFactory;
import com.gemserk.commons.artemis.templates.EntityTemplate;
import com.gemserk.commons.artemis.utils.EntityStore;
import com.gemserk.commons.reflection.Injector;
import com.gemserk.commons.utils.StoreFactory;

public class TemplateStoreFactory implements StoreFactory<Entity> {

	EntityFactory entityFactory;
	Injector injector;

	EntityStore entityStore;

	final Class<? extends EntityTemplate> entityTemplateClass;
	final EntityTemplate entityTemplate;

	public Class<? extends EntityTemplate> getEntityTemplateClass() {
		if (entityTemplate != null)
			return entityTemplate.getClass();
		return entityTemplateClass;
	}

	public void setEntityStore(EntityStore entityStore) {
		this.entityStore = entityStore;
	}

	public TemplateStoreFactory(Class<? extends EntityTemplate> entityTemplateClass) {
		this.entityTemplateClass = entityTemplateClass;
		this.entityTemplate = null;
	}

	public TemplateStoreFactory(EntityTemplate entityTemplate) {
		this.entityTemplate = entityTemplate;
		this.entityTemplateClass = null;
	}

	@Override
	public Entity createObject() {
		if (entityStore == null)
			throw new RuntimeException("Can't set null EntityStore to the created entity");

		Entity entity = null;

		if (entityTemplate != null)
			entity = entityFactory.instantiate(entityTemplate);
		else
			entity = entityFactory.instantiate(injector.getInstance(entityTemplateClass));

		StoreComponent storeComponent = StoreComponent.get(entity);
		if (storeComponent == null)
			storeComponent = new StoreComponent(null);
		storeComponent.store = entityStore;
		entity.addComponent(storeComponent);
		return entity;
	}
}