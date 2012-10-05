package com.gemserk.commons.artemis.systems;

import com.artemis.World;
import com.gemserk.commons.artemis.WorldSystemImpl;
import com.gemserk.commons.artemis.utils.EntityStore;
import com.gemserk.commons.gdx.artemis.stores.EntityStores;
import com.gemserk.componentsengine.utils.RandomAccessWithKey;

public class EntityStoreRefresherSystem extends WorldSystemImpl {

	EntityStores entityStores;

	@Override
	public void process(World world) {
		RandomAccessWithKey<Object, EntityStore> stores = entityStores.getEntityStores();
		for (int i = 0; i < stores.size(); i++) {
			EntityStore entityStore = stores.get(i);
			entityStore.refreshPool();
			// int freed = entityStore.refreshPool();
			// if(freed!=0)
			// System.out.println(stores.getKey(i) + " - " + freed);
		}
	}

}
