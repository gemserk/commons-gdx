package com.gemserk.commons.artemis.components;

import com.artemis.Component;
import com.badlogic.gdx.physics.box2d.Body;

public class PhysicsComponent extends Component {

	private Body body;
	
	private Contact contact;
	
	public Body getBody() {
		return body;
	}
	
	public Contact getContact() {
		return contact;
	}
	
	public PhysicsComponent(Body body) {
		this.body = body;
		this.contact = new Contact();
	}

}
