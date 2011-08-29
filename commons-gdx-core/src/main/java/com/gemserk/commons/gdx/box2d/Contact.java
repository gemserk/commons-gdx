package com.gemserk.commons.gdx.box2d;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;

public class Contact {

	private static class InternalContact {

		Fixture myFixture;
		Fixture otherFixture;
		
		boolean inContact = false;

		Vector2 normal = new Vector2();

		public void setContact(Fixture myFixture, Fixture otherFixture, Vector2 normal) {
			this.myFixture = myFixture;
			this.otherFixture = otherFixture;
			this.normal.set(normal);
			this.inContact = true;
		}

		public void unsetContact() {
			this.inContact = false;
			myFixture = null;
			otherFixture = null;
			this.normal.set(0f, 0f);
		}

	}

	private static int maxContactSize = 5;

	private InternalContact[] contacts = new InternalContact[maxContactSize];

	public Contact() {
		for (int i = 0; i < contacts.length; i++)
			contacts[i] = new InternalContact();
	}

	public void addContact(com.badlogic.gdx.physics.box2d.Contact contact, boolean AB) {
		
		Vector2 normal = contact.getWorldManifold().getNormal();
		Fixture myFixture;
		Fixture otherFixture;
		if(AB){
			myFixture = contact.getFixtureA();
			otherFixture = contact.getFixtureB();
		} else {
			myFixture = contact.getFixtureB();
			otherFixture = contact.getFixtureA();
			normal.mul(-1);// if the body in contact is the first one declared by the contact, then we have to invert the normal.
		}
				
		addContact(myFixture,otherFixture,normal);
	}

	void addContact(Fixture myFixture, Fixture otherFixture, Vector2 normal) {
		for (int i = 0; i < contacts.length; i++) {
			InternalContact c = contacts[i];
			
			if (c.inContact)
				continue;

			c.setContact(myFixture,otherFixture,normal);

			return;
		}
	}

	public void removeContact(com.badlogic.gdx.physics.box2d.Contact contact, boolean AB) {
		Fixture myFixture;
		Fixture otherFixture;
		
		if(AB){
			myFixture = contact.getFixtureA();
			otherFixture = contact.getFixtureB();
		} else {
			myFixture = contact.getFixtureB();
			otherFixture = contact.getFixtureA();
		}
		
		removeContact(myFixture,otherFixture);
	}
	
	public void removeContact(Fixture myFixture, Fixture otherFixture) {
		for (int i = 0; i < contacts.length; i++) {
			InternalContact c = contacts[i];
			if (!c.inContact)
				continue;
			
			if(c.myFixture != myFixture || c.otherFixture != otherFixture)
				continue;
			
			c.unsetContact();
			return;
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

	public Object getUserData() {
		return getUserData(0);
	}

	public Object getUserData(int i) {
		return contacts[i].otherFixture.getBody().getUserData();
	}

	public Body getBody() {
		return getBody(0);
	}

	public Body getBody(int i) {
		return contacts[i].otherFixture.getBody();
	}
	
	public Fixture getMyFixture(int i){
		return contacts[i].myFixture;
	}
	
	public Fixture getOtherFixture(int i){
		return contacts[i].otherFixture;
	}
	
	public InternalContact getContact(int i){
		return contacts[i];
	}

}
