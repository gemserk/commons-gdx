package com.gemserk.commons.artemis.systems;

import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;
import com.gemserk.commons.artemis.components.Components;
import com.gemserk.commons.artemis.components.ParticleEmitterComponent;
import com.gemserk.commons.artemis.components.SpatialComponent;
import com.gemserk.commons.gdx.GlobalTime;
import com.gemserk.commons.gdx.games.Spatial;

public class ParticleEmitterSystem extends EntityProcessingSystem {
	
	@SuppressWarnings("unchecked")
	public ParticleEmitterSystem() {
		super(Components.particleEmitterComponentClass, Components.spatialComponentClass);
	}

	@Override
	protected void process(Entity e) {
		SpatialComponent spatialComponent = Components.getSpatialComponent(e);
		Spatial spatial = spatialComponent.getSpatial();
		ParticleEmitterComponent particleEmitterComponent = Components.getParticleEmitterComponent(e);
		particleEmitterComponent.particleEmitter.setPosition(spatial.getX(), spatial.getY());
		particleEmitterComponent.particleEmitter.update(GlobalTime.getDelta());
	}
	
}
