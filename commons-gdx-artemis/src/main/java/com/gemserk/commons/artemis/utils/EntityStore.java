package com.gemserk.commons.artemis.utils;

import com.artemis.Entity;
import com.badlogic.gdx.utils.Array;
import com.gemserk.commons.utils.Store;
import com.gemserk.commons.utils.StoreFactory;

public class EntityStore extends Store<Entity> {

	Array<Entity> freedThisFrame = new Array<Entity>(false, 20, Entity.class);

	/**
	 * Creates a new EntityStore.
	 * 
	 * @param storeFactory
	 *            The StoreFactory to use.
	 */
	public EntityStore(StoreFactory<Entity> storeFactory) {
		super(storeFactory);
	}

	public void free(Entity e) {
		if (freedThisFrame.contains(e, true))
			return;
		e.disable();
		freedThisFrame.add(e);
	}

	public Entity get() {
		Entity e = super.get();
		e.enable();
		return e;
	}

	public int refreshPool() {
		int size = freedThisFrame.size;
		if (size <= 0)
			return 0;

		Entity[] entities = freedThisFrame.items;
		for (int i = 0; i < size; i++) {
			Entity entity = entities[i];
			super.free(entity);
		}
		freedThisFrame.clear();
		return size;
	}
	
}
