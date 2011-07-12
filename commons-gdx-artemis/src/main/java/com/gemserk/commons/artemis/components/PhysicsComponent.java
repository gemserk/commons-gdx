package com.gemserk.commons.artemis.components;

import com.artemis.Component;
import com.badlogic.gdx.physics.box2d.Body;
import com.gemserk.commons.gdx.box2d.Contact;
import com.gemserk.commons.gdx.games.Physics;
import com.gemserk.commons.gdx.games.PhysicsImpl;

public class PhysicsComponent extends Component {

	private final Physics physics;
	
	public Body getBody() {
		return physics.getBody();
	}
	
	public Contact getContact() {
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
