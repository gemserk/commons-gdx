package com.gemserk.commons.artemis.systems;

import com.artemis.Entity;
import com.artemis.utils.Bag;
import com.badlogic.gdx.utils.IntMap;

public abstract class EntityComponentsFactory<T> {

	public final IntMap<T> entityComponents = new IntMap<T>();
	final Bag<T> pool = new Bag<T>(64);

	public abstract T newInstance();

	public abstract void free(T entityComponent);

	public abstract void load(Entity e, T entityComponent);

	public T add(Entity entity) {
		T entityComponent;
		if (pool.isEmpty()) {
			entityComponent = newInstance();
		} else {
			entityComponent = pool.removeLast();
		}
		load(entity, entityComponent);
		entityComponents.put(entity.getId(), entityComponent);
		return entityComponent;
	}

	public void remove(Entity entity) {
		T entityComponent = entityComponents.remove(entity.getId());
		free(entityComponent);
		pool.add(entityComponent);
	}

	public T get(Entity entity) {
		return entityComponents.get(entity.getId());
	}

}