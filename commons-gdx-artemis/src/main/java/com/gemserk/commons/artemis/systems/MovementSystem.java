package com.gemserk.commons.artemis.systems;

import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.badlogic.gdx.math.Vector2;
import com.gemserk.commons.artemis.components.MovementComponent;
import com.gemserk.commons.artemis.components.SpatialComponent;
import com.gemserk.commons.gdx.GlobalTime;
import com.gemserk.commons.gdx.games.Spatial;
import com.gemserk.componentsengine.utils.RandomAccessMap;

public class MovementSystem extends EntitySystem {
	
	static class EntityComponents {
		SpatialComponent spatialComponent;
		MovementComponent movementComponent;
	}

	static class EntityComponentsHolder extends EntityComponentsFactory<EntityComponents> {

		@Override
		public EntityComponents newInstance() {
			return new EntityComponents();
		}

		@Override
		public void free(EntityComponents entityComponent) {
			entityComponent.spatialComponent = null;
			entityComponent.movementComponent = null;
		}

		@Override
		public void load(Entity e, EntityComponents entityComponent) {
			entityComponent.spatialComponent = SpatialComponent.get(e);
			entityComponent.movementComponent = MovementComponent.get(e);
		}

	}

	private final Vector2 tmpPosition = new Vector2();
	private final Vector2 tmpVelocity = new Vector2();

	EntityComponentsHolder componentsHolder;

	@SuppressWarnings("unchecked")
	public MovementSystem() {
		super(SpatialComponent.class, MovementComponent.class);
		componentsHolder = new EntityComponentsHolder();
	}

	@Override
	protected void enabled(Entity e) {
		super.enabled(e);
		componentsHolder.add(e);
	}
	
	@Override
	protected void disabled(Entity e) {
		super.disabled(e);
		componentsHolder.remove(e);
	}

	@Override
	protected void processEntities() {
		RandomAccessMap<Entity, EntityComponents> allTheEntityComponents = componentsHolder.entityComponents;
		int entitiesSize = allTheEntityComponents.size();
		
		float delta = GlobalTime.getDelta();
		
		for (int entityIndex = 0; entityIndex < entitiesSize; entityIndex++) {
			EntityComponents entityComponents = allTheEntityComponents.get(entityIndex);
			
			MovementComponent movementComponent = entityComponents.movementComponent;
			Spatial spatial = entityComponents.spatialComponent.getSpatial();

			Vector2 velocity = movementComponent.getVelocity();

			tmpVelocity.set(velocity).mul(delta);
			tmpPosition.set(spatial.getX(), spatial.getY()).add(tmpVelocity);

			float newAngle = spatial.getAngle() + delta * movementComponent.getAngularVelocity();
			spatial.setAngle(newAngle);

			spatial.setPosition(tmpPosition.x, tmpPosition.y);
		}
	}

	@Override
	protected boolean checkProcessing() {
		return true;
	}

}