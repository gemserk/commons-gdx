package com.gemserk.commons.artemis.systems;

import com.artemis.Entity;
import com.artemis.utils.Bag;
import com.gemserk.componentsengine.utils.RandomAccessMap;

public abstract class EntityComponentsFactory<T> {

	public final RandomAccessMap<Entity,T> entityComponents = new RandomAccessMap<Entity,T>(256);
	final Bag<T> pool = new Bag<T>(256);

	public abstract T newInstance();

	public abstract void free(T entityComponent);

	public abstract void load(Entity e, T entityComponent);
	
	public EntityComponentsFactory() {
		for (int i = 0; i < 200; i++) 
			pool.add(newInstance());
	}

	public T add(Entity entity) {
		T entityComponent;
		if (pool.isEmpty()) {
			entityComponent = newInstance();
		} else {
			entityComponent = pool.removeLast();
		}
		load(entity, entityComponent);
		entityComponents.put(entity, entityComponent);
		return entityComponent;
	}

	public void remove(Entity entity) {
		T entityComponent = entityComponents.remove(entity);
		free(entityComponent);
		pool.add(entityComponent);
	}

	public T get(Entity entity) {
		return entityComponents.get(entity);
	}

}