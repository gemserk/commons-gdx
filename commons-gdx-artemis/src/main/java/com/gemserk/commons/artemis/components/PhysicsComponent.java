package com.gemserk.commons.artemis.components;

import com.artemis.Component;
import com.artemis.ComponentTypeManager;
import com.artemis.Entity;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.gemserk.commons.gdx.box2d.Contacts;
import com.gemserk.commons.gdx.games.Physics;
import com.gemserk.commons.gdx.games.PhysicsImpl;

public class PhysicsComponent extends Component {

	public static interface ImmediateModePhysicsListener {

		public void beginContact(Entity e, Contact contact, boolean fixtureA);

		public void endContact(Entity e, Contact contact, boolean fixtureA);

		public void preSolve(Entity e, Contact contact, boolean fixtureA);

	}

	public static final int type = ComponentTypeManager.getTypeFor(PhysicsComponent.class).getId();

	public static PhysicsComponent get(Entity e) {
		return (PhysicsComponent) e.getComponent(type);
	}

	private final Physics physics;
	public ImmediateModePhysicsListener physicsListener;

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
		this(new PhysicsImpl(body), null);
	}

	public PhysicsComponent(Physics physics) {
		this(physics, null);
	}

	public PhysicsComponent(Body body, ImmediateModePhysicsListener physicsListener) {
		this(new PhysicsImpl(body), physicsListener);
	}

	public PhysicsComponent(Physics physics, ImmediateModePhysicsListener physicsListener) {
		this.physics = physics;
		this.physicsListener = physicsListener;

	}

}
