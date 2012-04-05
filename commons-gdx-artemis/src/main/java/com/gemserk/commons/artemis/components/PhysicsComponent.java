package com.gemserk.commons.artemis.components;

import com.artemis.Component;
import com.artemis.ComponentType;
import com.artemis.ComponentTypeManager;
import com.artemis.Entity;
import com.badlogic.gdx.physics.box2d.Body;
import com.gemserk.commons.gdx.box2d.Contacts;
import com.gemserk.commons.gdx.games.Physics;
import com.gemserk.commons.gdx.games.PhysicsImpl;

public class PhysicsComponent extends Component {
	
	public static final ComponentType type = ComponentTypeManager.getTypeFor(PhysicsComponent.class);

	public static PhysicsComponent get(Entity e) {
		return (PhysicsComponent) e.getComponent(type);
	}

	private final Physics physics;
	
	public Body getBody() {
		return physics.getBody();
	}
	
	public Contacts getContact() {
		return physics.getContact();
	}
	
	public Physics getPhysics() {
		return physics;
	}
	
	public PhysicsComponent(Body body) {
		this(new PhysicsImpl(body));
	}
	
	public PhysicsComponent(Physics physics) {
		this.physics = physics;
	}

}
