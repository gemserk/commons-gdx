package com.gemserk.commons.artemis.systems;

import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.gemserk.commons.artemis.components.Components;
import com.gemserk.commons.artemis.components.LinearVelocityLimitComponent;
import com.gemserk.commons.artemis.components.PhysicsComponent;

public class LimitLinearVelocitySystem extends EntityProcessingSystem {
	
	public static final Class<LinearVelocityLimitComponent> linearVelocityLimitComponent = LinearVelocityLimitComponent.class;

	@SuppressWarnings("unchecked")
	public LimitLinearVelocitySystem(World physicsWorld) {
		super(Components.physicsComponentClass, linearVelocityLimitComponent);
	}

	@Override
	protected void process(Entity e) {
		PhysicsComponent physicsComponent = Components.getPhysicsComponent(e);
		Body body = physicsComponent.getPhysics().getBody();

		LinearVelocityLimitComponent limitComponent = e.getComponent(LinearVelocityLimitComponent.class);
		Vector2 linearVelocity = body.getLinearVelocity();

		float speed = linearVelocity.len();
		float maxSpeed = limitComponent.getLimit();

		if (speed > maxSpeed) {
			float factor = maxSpeed / speed;
			linearVelocity.mul(factor);
			body.setLinearVelocity(linearVelocity);
		}

	}

}
