package com.gemserk.commons.artemis.systems;

import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;
import com.gemserk.commons.artemis.components.Components;
import com.gemserk.commons.artemis.components.PreviousStateSpatialComponent;
import com.gemserk.commons.artemis.components.SpatialComponent;
import com.gemserk.commons.gdx.games.Spatial;

/**
 * Stores spatial state on the PreviousSpatialStateComponent (name could be changed) to be used when interpolating render stuff.
 */
public class PreviousStateSpatialSystem extends EntityProcessingSystem {

	@SuppressWarnings("unchecked")
	public PreviousStateSpatialSystem() {
		super(Components.spatialComponentClass, Components.previousStateSpatialComponentClass);
	}

	@Override
	protected void process(Entity e) {
		SpatialComponent spatialComponent = Components.getSpatialComponent(e);
		Spatial spatial = spatialComponent.getSpatial();
		PreviousStateSpatialComponent previousStateSpatialComponent = Components.getPreviousStateSpatialComponent(e);
		Spatial previousSpatial = previousStateSpatialComponent.getSpatial();
		previousSpatial.set(spatial);
	}

}
