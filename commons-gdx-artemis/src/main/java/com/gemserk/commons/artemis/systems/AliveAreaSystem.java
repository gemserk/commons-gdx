package com.gemserk.commons.artemis.systems;

import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.utils.ImmutableBag;
import com.gemserk.commons.artemis.components.AliveAreaComponent;
import com.gemserk.commons.artemis.components.SpatialComponent;
import com.gemserk.commons.gdx.math.MathUtils2;

public class AliveAreaSystem extends EntitySystem {
	
	@SuppressWarnings("unchecked")
	public AliveAreaSystem() {
		super(AliveAreaComponent.class, SpatialComponent.class);
	}

	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		
		for (int i = 0; i < entities.size(); i++) {
			Entity entity = entities.get(i);
			
			AliveAreaComponent aliveAreaComponent = entity.getComponent(AliveAreaComponent.class);
			SpatialComponent spatialComponent = entity.getComponent(SpatialComponent.class);
			
			if (MathUtils2.inside(aliveAreaComponent.getAliveArea(), spatialComponent.getPosition()))
				continue;
			
			world.deleteEntity(entity);
			
		}
		
	}
	
	@Override
	protected void removed(Entity entity) {
		
		AliveAreaComponent aliveAreaComponent = entity.getComponent(AliveAreaComponent.class);
		if (aliveAreaComponent != null)
			entity.removeComponent(aliveAreaComponent);
		
	}

	@Override
	public void initialize() {

	}

	@Override
	protected boolean checkProcessing() {
		return true;
	}
}