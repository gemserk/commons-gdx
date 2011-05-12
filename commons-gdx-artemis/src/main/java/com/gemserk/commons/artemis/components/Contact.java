package com.gemserk.commons.artemis.components;

import com.artemis.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class Contact {

	private static class InternalContact {

		Body body;

		boolean inContact = false;

		Vector2 normal = new Vector2();

		public void setContact(Body body, Vector2 normal) {
			this.body = body;
			this.normal.set(normal);
			this.inContact = true;
		}

		public void unsetContact() {
			this.inContact = false;
			this.body = null;
			this.normal.set(0f, 0f);
		}

	}

	private static int maxContactSize = 5;

	private InternalContact[] contacts = new InternalContact[maxContactSize];

	public Contact() {
		for (int i = 0; i < contacts.length; i++)
			contacts[i] = new InternalContact();
	}

	public void addContact(com.badlogic.gdx.physics.box2d.Contact contact, Body body) {
		for (int i = 0; i < contacts.length; i++) {
			InternalContact c = contacts[i];
			if (c.inContact && c.body != body)
				continue;

			c.setContact(body, contact.getWorldManifold().getNormal());

			// if the body in contact is the first one declared by the contact, then we have to invert the normal.
			// if (contact.getFixtureA().getBody() == body)
			// c.normal.mul(-1f);

			return;
		}
	}

	public void removeContact(Body body) {
		for (int i = 0; i < contacts.length; i++) {
			InternalContact c = contacts[i];
			if (!c.inContact)
				continue;
			if (c.body != body)
				continue;
			c.unsetContact();
		}
	}

	public boolean isInContact() {
		for (int i = 0; i < contacts.length; i++) {
			InternalContact c = contacts[i];
			if (c.inContact)
				return true;
		}
		return false;
	}

	public boolean isInContact(int i) {
		return contacts[i].inContact;
	}

	public Vector2 getNormal() {
		return contacts[0].normal;
	}

	public Vector2 getNormal(int contact) {
		return contacts[contact].normal;
	}

	public int getContactCount() {
		return contacts.length;
	}

	public Entity getEntity() {
		return getEntity(0);
	}

	public Entity getEntity(int i) {
		return (Entity) contacts[i].body.getUserData();
	}

	public Body getBody() {
		return getBody(0);
	}

	public Body getBody(int i) {
		return contacts[i].body;
	}

}
