package com.gemserk.commons.artemis.systems;

import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.math.Vector2;
import com.gemserk.commons.artemis.components.MovementComponent;
import com.gemserk.commons.artemis.components.SpatialComponent;

public class MovementSystem extends EntitySystem {

	Vector2 tmpPosition = new Vector2();

	Vector2 tmpVelocity = new Vector2();

	@SuppressWarnings("unchecked")
	public MovementSystem() {
		super(SpatialComponent.class, MovementComponent.class);
	}

	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		for (int i = 0; i < entities.size(); i++) {

			Entity entity = entities.get(i);

			SpatialComponent spatialComponent = entity.getComponent(SpatialComponent.class);
			MovementComponent movementComponent = entity.getComponent(MovementComponent.class);

			Vector2 position = spatialComponent.getPosition();

			Vector2 velocity = movementComponent.getVelocity();

			int delta = world.getDelta();
			float deltaF = ((float) delta) / 1000f;
			
			tmpVelocity.set(velocity).mul(deltaF);
			tmpPosition.set(position).add(tmpVelocity);
			
			float newAngle = spatialComponent.getAngle() + deltaF * movementComponent.getAngularVelocity();
			spatialComponent.setAngle(newAngle);
			
			position.set(tmpPosition);
			
		}
	}

	@Override
	public void initialize() {

	}

	@Override
	protected boolean checkProcessing() {
		return true;
	}
}