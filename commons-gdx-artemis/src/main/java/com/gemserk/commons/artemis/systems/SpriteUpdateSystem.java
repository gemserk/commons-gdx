package com.gemserk.commons.artemis.systems;

import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.gemserk.animation4j.interpolator.FloatInterpolator;
import com.gemserk.commons.artemis.components.Components;
import com.gemserk.commons.artemis.components.PreviousStateSpatialComponent;
import com.gemserk.commons.artemis.components.SpatialComponent;
import com.gemserk.commons.artemis.components.SpriteComponent;
import com.gemserk.commons.gdx.games.Spatial;
import com.gemserk.commons.gdx.time.TimeStepProvider;
import com.gemserk.commons.gdx.time.TimeStepProviderGlobalImpl;
import com.gemserk.componentsengine.utils.AngleUtils;
import com.gemserk.componentsengine.utils.RandomAccessMap;

/**
 * Updates Sprites from SpriteComponent to the location of the Spatial from the SpatialComponent, if the entity has a PreviousSpatialStateComponent, then it performs an interpolation between both spatial information using the GlobalTime.getAlpha().
 */
public class SpriteUpdateSystem extends EntitySystem {

	static class EntityComponents {

		public SpatialComponent spatialComponent;
		public SpriteComponent spriteComponent;
		public PreviousStateSpatialComponent previousStateSpatialComponent;

	}

	static class Factory extends EntityComponentsFactory<EntityComponents> {

		@Override
		public EntityComponents newInstance() {
			return new EntityComponents();
		}

		@Override
		public void free(EntityComponents entityComponent) {
		}

		@Override
		public void load(Entity e, EntityComponents entityComponent) {
			entityComponent.spatialComponent = Components.getSpatialComponent(e);
			entityComponent.spriteComponent = Components.getSpriteComponent(e);
			entityComponent.previousStateSpatialComponent = Components.getPreviousStateSpatialComponent(e);
		}

	}

	private final TimeStepProvider timeStepProvider;
	private Factory factory;

	public SpriteUpdateSystem() {
		this(new TimeStepProviderGlobalImpl());
	}

	@SuppressWarnings("unchecked")
	public SpriteUpdateSystem(TimeStepProvider timeStepProvider) {
		super(Components.spatialComponentClass, Components.spriteComponentClass);
		this.timeStepProvider = timeStepProvider;
		this.factory = new Factory();
	}

	@Override
	protected void processEntities() {
		RandomAccessMap<Entity, EntityComponents> allTheEntityComponents = factory.entityComponents;
		int entitiesSize = allTheEntityComponents.size();
		for (int entityIndex = 0; entityIndex < entitiesSize; entityIndex++) {
			EntityComponents components = allTheEntityComponents.get(entityIndex);
			SpatialComponent spatialComponent = components.spatialComponent;
			SpriteComponent spriteComponent = components.spriteComponent;

			PreviousStateSpatialComponent previousStateSpatialComponent = components.previousStateSpatialComponent;

			Spatial spatial = spatialComponent.getSpatial();

			float x = spatial.getX();
			float y = spatial.getY();
			float angle = spatial.getAngle();

			if (previousStateSpatialComponent != null) {
				float interpolationAlpha = timeStepProvider.getAlpha();
				Spatial previousSpatial = previousStateSpatialComponent.getSpatial();
				x = FloatInterpolator.interpolate(previousSpatial.getX(), spatial.getX(), interpolationAlpha);
				y = FloatInterpolator.interpolate(previousSpatial.getY(), spatial.getY(), interpolationAlpha);
				float angleDiff = (float) AngleUtils.minimumDifference(previousSpatial.getAngle(), spatial.getAngle());
				angle = FloatInterpolator.interpolate(spatial.getAngle() - angleDiff, spatial.getAngle(), interpolationAlpha);
			}

			spriteComponent.update(x, y, spatial.getWidth(), spatial.getHeight(), angle);
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

	@Override
	protected boolean checkProcessing() {
		return true;
	}
}