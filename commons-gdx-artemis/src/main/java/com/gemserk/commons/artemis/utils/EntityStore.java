package com.gemserk.commons.artemis.utils;

import com.artemis.Entity;
import com.gemserk.commons.utils.Store;
import com.gemserk.commons.utils.StoreFactory;

public class EntityStore extends Store<Entity> {
	
	public EntityStore(StoreFactory<Entity> storeFactory) {
		super(storeFactory);
	}

	public void free(Entity e) {
		e.disable();
		super.free(e);
	}

	public Entity get() {
		Entity e = super.get();
		e.enable();
		return e;
	}

}
