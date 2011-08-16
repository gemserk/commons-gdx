package com.gemserk.commons.gdx.box2d;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;


@RunWith(JMock.class)
public class ContactTest {
	
	Mockery mockery = new Mockery() {
		{
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};
	
	@Test
	public void shouldStartWithNoContacts() {
		Contact contact = new Contact();
		assertThat(contact.isInContact(), IsEqual.equalTo(false));
	}

	@Test
	public void shouldCreateContactForBody() {
		Contact contact = new Contact();
		
		Vector2 normal = new Vector2(1f, 0f);
		
		Body body = mockery.mock(Body.class, "body1");
		Body otherBody = mockery.mock(Body.class, "body2");
		
		contact.addContact(body, normal, otherBody);
		assertThat(contact.isInContact(), IsEqual.equalTo(true));
	}
	
	@Test
	public void shouldCreateAndRemoveContactForBody() {
		Contact contact = new Contact();
		
		Vector2 normal = new Vector2(1f, 0f);
		
		Body body = mockery.mock(Body.class, "body1");
		Body otherBody = mockery.mock(Body.class, "body2");
		
		contact.addContact(body, normal, otherBody);
		contact.removeContact(body);
		
		assertThat(contact.isInContact(), IsEqual.equalTo(false));
	}
	
	@Test
	public void shouldAddContactForBodyTwice() {
		Contact contact = new Contact();
		
		Vector2 normal = new Vector2(1f, 0f);
		
		Body body = mockery.mock(Body.class, "body1");
		Body otherBody = mockery.mock(Body.class, "body2");
		
		contact.addContact(body, normal, otherBody);
		contact.addContact(body, normal, otherBody);
		
		int contactsInContact = 0;
		for (int i = 0; i < contact.getContactCount(); i++) {
			if (contact.isInContact(i))
				contactsInContact++;
		}
		
		assertThat(contact.isInContact(), IsEqual.equalTo(true));
		assertThat(contactsInContact, IsEqual.equalTo(2));
	}
	
	@Test
	public void shouldAddContactTwiceAndRemoveOnlyOneContactFromBody() {
		Contact contact = new Contact();
		
		Vector2 normal = new Vector2(1f, 0f);
		
		Body body = mockery.mock(Body.class, "body1");
		Body otherBody = mockery.mock(Body.class, "body2");
		
		contact.addContact(body, normal, otherBody);
		contact.addContact(body, normal, otherBody);
		
		contact.removeContact(body);
		
		assertThat(contact.isInContact(), IsEqual.equalTo(true));
	}
	
	// could still be bugs when saving a contact A and a contact B and end contact called, I am removing one of the contacts without knowing which one...
	// don't know exactly how to determine which contact is.

}
