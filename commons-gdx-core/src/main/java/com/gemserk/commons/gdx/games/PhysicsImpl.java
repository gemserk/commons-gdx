package com.gemserk.commons.gdx.games;

import com.badlogic.gdx.physics.box2d.Body;
import com.gemserk.commons.gdx.box2d.Contact;

public class PhysicsImpl implements Physics {
	
	private final Body body;
	private final Contact contact;

	public PhysicsImpl(Body body) {
		this.body = body;
		this.contact = new Contact();
	}

	@Override
	public Body getBody() {
		return body;
	}

	@Override
	public Contact getContact() {
		return contact;
	}

}
