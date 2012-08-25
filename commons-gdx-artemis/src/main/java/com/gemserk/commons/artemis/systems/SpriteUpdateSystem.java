package com.gemserk.commons.artemis.systems;

import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
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

			float newX = spatial.getX();
			float newY = spatial.getY();
			float angle = spatial.getAngle();

			if (previousStateSpatialComponent != null) {
				float interpolationAlpha = timeStepProvider.getAlpha();
				Spatial previousSpatial = previousStateSpatialComponent.getSpatial();
				newX = FloatInterpolator.interpolate(previousSpatial.getX(), spatial.getX(), interpolationAlpha);
				newY = FloatInterpolator.interpolate(previousSpatial.getY(), spatial.getY(), interpolationAlpha);
				float angleDiff = (float) AngleUtils.minimumDifference(previousSpatial.getAngle(), spatial.getAngle());
				// angle = FloatInterpolator.interpolate(previousSpatial.getAngle(), spatial.getAngle(), interpolationAlpha);
				angle = FloatInterpolator.interpolate(spatial.getAngle() - angleDiff, spatial.getAngle(), interpolationAlpha);
			}

			Sprite sprite = spriteComponent.getSprite();
			Vector2 center = spriteComponent.getCenter();

			if (spriteComponent.isUpdateRotation()) {
				if (sprite.getRotation() != angle)
					sprite.setRotation(angle);
			}

			float ox = spatial.getWidth() * center.x;
			float oy = spatial.getHeight() * center.y;

			if (ox != sprite.getOriginX() || oy != sprite.getOriginY())
				sprite.setOrigin(ox, oy);

			if (sprite.getWidth() != spatial.getWidth() || sprite.getHeight() != spatial.getHeight())
				sprite.setSize(spatial.getWidth(), spatial.getHeight());

			float x = newX - sprite.getOriginX();
			float y = newY - sprite.getOriginY();

			if (x != sprite.getX() || y != sprite.getY())
				sprite.setPosition(x, y);
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