package com.gemserk.commons.gdx.games;

import com.badlogic.gdx.physics.box2d.Body;
import com.gemserk.commons.gdx.box2d.Contacts;

public class PhysicsImpl implements Physics {
	
	private final Body body;
	private final Contacts contact;

	public PhysicsImpl(Body body) {
		this.body = body;
		this.contact = new Contacts();
	}

	@Override
	public Body getBody() {
		return body;
	}

	@Override
	public Contacts getContact() {
		return contact;
	}

}
