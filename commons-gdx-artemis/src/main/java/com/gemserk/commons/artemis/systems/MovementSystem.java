package com.gemserk.commons.artemis.systems;

import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;
import com.badlogic.gdx.math.Vector2;
import com.gemserk.commons.artemis.components.MovementComponent;
import com.gemserk.commons.artemis.components.SpatialComponent;
import com.gemserk.commons.gdx.GlobalTime;
import com.gemserk.commons.gdx.games.Spatial;

public class MovementSystem extends EntityProcessingSystem {
	
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
	protected void process(Entity e) {
		EntityComponents entityComponents = componentsHolder.get(e);
		
		MovementComponent movementComponent = entityComponents.movementComponent;
		Spatial spatial = entityComponents.spatialComponent.getSpatial();

		Vector2 velocity = movementComponent.getVelocity();

		float delta = GlobalTime.getDelta();

		tmpVelocity.set(velocity).mul(delta);
		tmpPosition.set(spatial.getX(), spatial.getY()).add(tmpVelocity);

		float newAngle = spatial.getAngle() + delta * movementComponent.getAngularVelocity();
		spatial.setAngle(newAngle);

		spatial.setPosition(tmpPosition.x, tmpPosition.y);
	}

}