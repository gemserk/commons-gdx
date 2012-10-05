package com.gemserk.commons.gdx.artemis.stores;

import com.gemserk.commons.artemis.utils.EntityStore;
import com.gemserk.componentsengine.utils.RandomAccessMap;
import com.gemserk.componentsengine.utils.RandomAccessWithKey;

public class EntityStores  {
	
	private static final NullEntityStore nullEntityStore = new NullEntityStore();
	
	RandomAccessMap<Object, EntityStore> map = new RandomAccessMap<Object, EntityStore>();

	public EntityStores() {
	}

	public EntityStore put(TemplateStoreFactory storeFactory) {
		return put(0, storeFactory);
	}

	public EntityStore put(int count, TemplateStoreFactory storeFactory) {
		return put(storeFactory.getEntityTemplateClass(), count, storeFactory);
	}

	public EntityStore put(Object key, TemplateStoreFactory storeFactory) {
		return put(key, 0, storeFactory);
	}

	public EntityStore put(Object key, int count, TemplateStoreFactory storeFactory) {
		EntityStore entityStore = new EntityStore(storeFactory);
		storeFactory.setEntityStore(entityStore);
		map.put(key, entityStore);
		entityStore.preCreate(count);
		return entityStore;
	}
	
	public EntityStore get(Object key){
		return map.get(key);
	}

	public EntityStore getDefault() {
		return nullEntityStore;
	}
	
	public RandomAccessWithKey<Object, EntityStore> getEntityStores(){
		return map;
	}

}