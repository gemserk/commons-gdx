package com.gemserk.commons.artemis.systems;

import com.artemis.Entity;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.gemserk.commons.artemis.components.PhysicsComponent;

public class PhysicsContactListener implements ContactListener {

	@Override
	public void beginContact(Contact contact) {
		if(!contact.isTouching())
			return;
		
		Body bodyA = contact.getFixtureA().getBody();
		Body bodyB = contact.getFixtureB().getBody();

		Entity entityA = (Entity) bodyA.getUserData();
		Entity entityB = (Entity) bodyB.getUserData();

		addBodyToContacts(entityA, contact,true);
		addBodyToContacts(entityB, contact,false);
	}

	/**
	 * Adds the body to the entity contacts.
	 * 
	 * @param e
	 *            The entity to add the contact to.
	 * @param body
	 *            The body to add to the contacts.
	 * @param contact
	 *            The real contact, used internally to get some data like normals and stuff.
	 */
	private void addBodyToContacts(Entity e, Contact contact, boolean fixtureA) {
		if (e == null)
			return;
		PhysicsComponent physicsComponent = e.getComponent(PhysicsComponent.class);
		if (physicsComponent == null)
			return;
		
		physicsComponent.getContact().addContact(contact, fixtureA);
	}

	@Override
	public void endContact(Contact contact) {
		Body bodyA = contact.getFixtureA().getBody();
		Body bodyB = contact.getFixtureB().getBody();

		Entity entityA = (Entity) bodyA.getUserData();
		Entity entityB = (Entity) bodyB.getUserData();

		removeBodyFromContacts(entityA, contact,true);
		removeBodyFromContacts(entityB, contact,false);
	}

	/**
	 * Removes body from entity contacts.
	 * 
	 * @param e
	 *            The entity to remove the contact from.
	 * @param body
	 *            The body to be removed from contacts.
	 */
	private void removeBodyFromContacts(Entity e, Contact contact, boolean fixtureA) {
		if (e == null)
			return;
		PhysicsComponent physicsComponent = e.getComponent(PhysicsComponent.class);
		if (physicsComponent == null)
			return;
		physicsComponent.getContact().removeContact(contact, fixtureA);
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated function stub

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated function stub

	}

}