package com.gemserk.commons.artemis.systems;

import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;
import com.badlogic.gdx.math.Vector2;
import com.gemserk.commons.artemis.components.MovementComponent;
import com.gemserk.commons.artemis.components.SpatialComponent;
import com.gemserk.commons.gdx.GlobalTime;
import com.gemserk.commons.gdx.games.Spatial;

public class MovementSystem extends EntityProcessingSystem {

	private final Vector2 tmpPosition = new Vector2();
	private final Vector2 tmpVelocity = new Vector2();
	
	private static final Class<SpatialComponent> spatialComponentClass = SpatialComponent.class;
	private static final Class<MovementComponent> movementComponentClass = MovementComponent.class;

	@SuppressWarnings("unchecked")
	public MovementSystem() {
		super(spatialComponentClass, movementComponentClass);
	}

	@Override
	protected void process(Entity e) {
		SpatialComponent spatialComponent = e.getComponent(spatialComponentClass);
		MovementComponent movementComponent = e.getComponent(movementComponentClass);

		Spatial spatial = spatialComponent.getSpatial();

		Vector2 velocity = movementComponent.getVelocity();

		float delta = GlobalTime.getDelta();

		tmpVelocity.set(velocity).mul(delta);
		tmpPosition.set(spatial.getX(), spatial.getY()).add(tmpVelocity);

		float newAngle = spatial.getAngle() + delta * movementComponent.getAngularVelocity();
		spatial.setAngle(newAngle);

		spatial.setPosition(tmpPosition.x, tmpPosition.y);
	}

}