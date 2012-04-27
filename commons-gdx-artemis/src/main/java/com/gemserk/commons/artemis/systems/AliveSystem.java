package com.gemserk.commons.artemis.systems;

import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;
import com.gemserk.commons.artemis.components.AliveComponent;
import com.gemserk.commons.artemis.components.StoreComponent;
import com.gemserk.commons.gdx.GlobalTime;

public class AliveSystem extends EntityProcessingSystem {

	class EntityComponents {
		AliveComponent aliveComponent;
	}
	
	class Factory extends EntityComponentsFactory<EntityComponents>{

		@Override
		public EntityComponents newInstance() {
			return new EntityComponents();
		}

		@Override
		public void free(EntityComponents entityComponent) {
			entityComponent.aliveComponent = null;
			
		}

		@Override
		public void load(Entity e, EntityComponents entityComponent) {
			entityComponent.aliveComponent = AliveComponent.get(e);
		}
		
	}

	private Factory factory;
	
	
	@SuppressWarnings("unchecked")
	public AliveSystem() {
		super(AliveComponent.class);
		this.factory = new Factory();
	}

	@Override
	protected void process(Entity e) {
		EntityComponents components = factory.get(e);
		AliveComponent aliveComponent = components.aliveComponent;
		float aliveTime = aliveComponent.getTime() - GlobalTime.getDelta();
		aliveComponent.setTime(aliveTime);
		if (aliveTime <= 0) {
			StoreComponent storeComponent = StoreComponent.get(e);
			if (storeComponent != null)
				storeComponent.store.free(e);
			else
				e.delete();
		}
	}
	
	@Override
	protected void enabled(Entity e) {
		super.enabled(e);
		factory.add(e);
	}
	
	@Override
	protected void disabled(Entity e) {
		factory.remove(e);
		super.disabled(e);
	}

}