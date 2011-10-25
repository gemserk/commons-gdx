package com.gemserk.commons.artemis.systems;

import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.gemserk.commons.artemis.components.AntiGravityComponent;
import com.gemserk.commons.artemis.components.PhysicsComponent;

public class AntiGravitySystem extends EntityProcessingSystem {
	
	private static final Class<AntiGravityComponent> antiGravityClass = AntiGravityComponent.class;
	
	private final Vector2 tmp = new Vector2();

	public AntiGravitySystem() {
		super(antiGravityClass);
	}
	
	@Override
	protected void process(Entity e) {
		PhysicsComponent physicsComponent = e.getComponent(PhysicsComponent.class);
		Body body = physicsComponent.getPhysics().getBody();
		
		Vector2 gravity = body.getWorld().getGravity();

		tmp.set(gravity).mul(-body.getMass());
		body.applyForceToCenter(tmp);
	}


}
