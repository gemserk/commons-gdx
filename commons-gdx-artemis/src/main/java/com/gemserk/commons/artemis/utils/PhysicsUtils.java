package com.gemserk.commons.artemis.utils;

import com.artemis.Entity;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Array;
import com.gemserk.commons.artemis.components.Components;
import com.gemserk.commons.artemis.components.PhysicsComponent;
import com.gemserk.commons.gdx.box2d.Contacts;
import com.gemserk.commons.gdx.box2d.Contacts.Contact;

public class PhysicsUtils {

	static final Array<Contact> contactsToRemove = new Array<Contact>(10);

	public static void releaseContacts(Entity entity) {
		PhysicsComponent physicsComponent = Components.getPhysicsComponent(entity);
		releaseContacts(physicsComponent.getContact());
	}

	public static void releaseContacts(Contacts contacts) {
		// removes contact from the other entity
		for (int i = 0; i < contacts.getContactCount(); i++) {
			Contact contact = contacts.getContact(i);

			Fixture myFixture = contact.getMyFixture();
			Fixture otherFixture = contact.getOtherFixture();
			
			contactsToRemove.add(contact);

			Body otherBody = otherFixture.getBody();
			if (otherBody == null)
				continue;

			Entity otherEntity = (Entity) otherBody.getUserData();
			if (otherEntity == null)
				continue;

			// entitiesInContact.add(otherEntity);
			
			// removes the contact from the other entity.

			PhysicsComponent otherPhyiscsComponent = Components.getPhysicsComponent(otherEntity);
			otherPhyiscsComponent.getContact().removeContact(otherFixture, myFixture);
		}
		
		for (int i = 0; i < contactsToRemove.size; i++) {
			Contact contact = contactsToRemove.get(i);
			contacts.removeContact(contact.getMyFixture(), contact.getOtherFixture());
		}
		
		contactsToRemove.clear();
	}

}
