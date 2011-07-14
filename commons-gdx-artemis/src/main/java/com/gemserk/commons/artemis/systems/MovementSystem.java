package com.gemserk.commons.artemis.systems;

import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;
import com.badlogic.gdx.math.Vector2;
import com.gemserk.commons.artemis.components.MovementComponent;
import com.gemserk.commons.artemis.components.SpatialComponent;
import com.gemserk.commons.gdx.games.Spatial;

public class MovementSystem extends EntityProcessingSystem {

	private final Vector2 tmpPosition = new Vector2();

	private final Vector2 tmpVelocity = new Vector2();

	@SuppressWarnings("unchecked")
	public MovementSystem() {
		super(SpatialComponent.class, MovementComponent.class);
	}

	@Override
	protected void process(Entity e) {
		SpatialComponent spatialComponent = e.getComponent(SpatialComponent.class);
		MovementComponent movementComponent = e.getComponent(MovementComponent.class);

		Spatial spatial = spatialComponent.getSpatial();

		Vector2 velocity = movementComponent.getVelocity();

		int delta = world.getDelta();
		float deltaF = ((float) delta) / 1000f;

		tmpVelocity.set(velocity).mul(deltaF);
		tmpPosition.set(spatial.getX(), spatial.getY()).add(tmpVelocity);

		float newAngle = spatial.getAngle() + deltaF * movementComponent.getAngularVelocity();
		spatial.setAngle(newAngle);

		spatial.setPosition(tmpPosition.x, tmpPosition.y);
	}

}