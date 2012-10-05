package com.gemserk.commons.gdx.artemis.stores;

import com.artemis.Entity;
import com.gemserk.commons.artemis.utils.EntityStore;

public class NullEntityStore extends EntityStore {

	public NullEntityStore() {
		super(null);
	}

	@Override
	public void free(Entity e) {
		e.disable();
	}

	@Override
	public Entity get() {
		throw new UnsupportedOperationException("Can't call get() from null entity store");
	}

}
