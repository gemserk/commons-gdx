package com.gemserk.commons.artemis.systems;

import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.gemserk.commons.artemis.components.Components;
import com.gemserk.commons.artemis.components.PreviousStateSpatialComponent;
import com.gemserk.commons.artemis.components.SpatialComponent;
import com.gemserk.commons.gdx.games.Spatial;
import com.gemserk.componentsengine.utils.RandomAccessMap;

/**
 * Stores spatial state on the PreviousSpatialStateComponent (name could be changed) to be used when interpolating render stuff.
 */
public class PreviousStateSpatialSystem extends EntitySystem {

	static class EntityComponents {
		public SpatialComponent spatialComponent;
		public PreviousStateSpatialComponent previousStateSpatialComponent;
	}

	static class Factory extends EntityComponentsFactory<EntityComponents> {

		@Override
		public EntityComponents newInstance() {
			return new EntityComponents();
		}

		@Override
		public void free(EntityComponents entityComponent) {
			entityComponent.spatialComponent = null;
			entityComponent.previousStateSpatialComponent = null;
		}

		@Override
		public void load(Entity e, EntityComponents entityComponent) {
			entityComponent.spatialComponent = SpatialComponent.get(e);
			entityComponent.previousStateSpatialComponent = PreviousStateSpatialComponent.get(e);
		}

	}

	private Factory factory;

	@SuppressWarnings("unchecked")
	public PreviousStateSpatialSystem() {
		super(Components.spatialComponentClass, Components.previousStateSpatialComponentClass);
		factory = new Factory();
	}

	protected void enabled(Entity e) {
		super.enabled(e);
		factory.add(e);
	}

	@Override
	protected void disabled(Entity e) {
		factory.remove(e);
		super.disabled(e);
	}

	@Override
	protected void processEntities() {
		RandomAccessMap<Entity, EntityComponents> allTheEntityComponents = factory.entityComponents;
		int entitiesSize = allTheEntityComponents.size();
		for (int e = 0; e < entitiesSize; e++) {
			EntityComponents entityComponents = allTheEntityComponents.get(e);
			SpatialComponent spatialComponent = entityComponents.spatialComponent;
			PreviousStateSpatialComponent previousStateSpatialComponent = entityComponents.previousStateSpatialComponent;
			Spatial spatial = spatialComponent.getSpatial();
			Spatial previousSpatial = previousStateSpatialComponent.getSpatial();
			previousSpatial.set(spatial);
		}
	}

}
