package com.gemserk.commons.gdx.box2d;

import static org.jmock.Expectations.same;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.hamcrest.core.IsEqual;
import org.hamcrest.number.OrderingComparisons;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.WorldManifold;
import com.gemserk.commons.gdx.box2d.Contacts.Contact;

@RunWith(JMock.class)
public class ContactsTest {

	Mockery mockery = new Mockery() {
		{
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};
	private Contacts contacts;
	private com.badlogic.gdx.physics.box2d.Contact box2dcontact;
	private WorldManifold worldManifold;
	private Vector2 normal;
	private Fixture fixtureA;
	private Fixture fixtureB;

	@Before
	public void setUp() {
		contacts = new Contacts();
		box2dcontact = mockery.mock(com.badlogic.gdx.physics.box2d.Contact.class);
		worldManifold = mockery.mock(WorldManifold.class);
		normal = new Vector2(0, 1);
		fixtureA = mockery.mock(Fixture.class, "fixtureA");
		fixtureB = mockery.mock(Fixture.class, "fixtureB");
	}

	@Test
	public void onCreationThereAreNoActiveContacts() {

		assertThat(0, IsEqual.equalTo(contacts.getContactCount()));
		assertFalse(contacts.isInContact());
	}

	@Test
	public void whenAddingOneContactABItIsAdded() {

		mockery.checking(new Expectations() {
			{
				oneOf(box2dcontact).getWorldManifold();
				will(returnValue(worldManifold));
				oneOf(worldManifold).getNormal();
				will(returnValue(normal));
				oneOf(box2dcontact).getFixtureA();
				will(returnValue(fixtureA));
				oneOf(box2dcontact).getFixtureB();
				will(returnValue(fixtureB));
				ignoring(fixtureA);
				ignoring(fixtureB);
			}
		});

		contacts.addContact(box2dcontact, true);
		assertTrue(contacts.isInContact());
		assertThat(1, IsEqual.equalTo(contacts.getContactCount()));

		Contact contact = contacts.getContact(0);
		assertThat(contact.getNormal().dst(0, 1), OrderingComparisons.lessThan(0.1f));
		assertSame(fixtureA, contact.getMyFixture());
		assertSame(fixtureB, contact.getOtherFixture());

	}

	@Test
	public void whenAddingOneContactBAItIsAdded() {

		mockery.checking(new Expectations() {
			{
				oneOf(box2dcontact).getWorldManifold();
				will(returnValue(worldManifold));
				oneOf(worldManifold).getNormal();
				will(returnValue(normal));
				oneOf(box2dcontact).getFixtureA();
				will(returnValue(fixtureA));
				oneOf(box2dcontact).getFixtureB();
				will(returnValue(fixtureB));
				ignoring(fixtureA);
				ignoring(fixtureB);
			}
		});

		contacts.addContact(box2dcontact, false);
		assertTrue(contacts.isInContact());
		assertThat(1, IsEqual.equalTo(contacts.getContactCount()));

		Contact contact = contacts.getContact(0);
		assertThat(contact.getNormal().dst(0, -1), OrderingComparisons.lessThan(0.1f));
		assertSame(fixtureA, contact.getOtherFixture());
		assertSame(fixtureB, contact.getMyFixture());

	}

	@Test
	public void removesFirstContactIfItMatches() {

		mockery.checking(new Expectations() {
			{
				ignoring(fixtureA);
				ignoring(fixtureB);
			}
		});

		contacts.addContact(fixtureA, fixtureB, normal);

		assertTrue(contacts.isInContact());
		assertThat(1, IsEqual.equalTo(contacts.getContactCount()));

		contacts.removeContact(fixtureA, fixtureB);
		assertFalse(contacts.isInContact());
		assertThat(0, IsEqual.equalTo(contacts.getContactCount()));

		Contact removedContact = contacts.contacts.get(0);
		assertFalse(removedContact.inContact);
		assertNull(removedContact.myFixture);
		assertNull(removedContact.otherFixture);
	}

	@Test
	public void removesSecondContactIfItMatches() {

		final Fixture fixtureC = mockery.mock(Fixture.class);

		mockery.checking(new Expectations() {
			{
				ignoring(fixtureA);
				ignoring(fixtureB);
				ignoring(fixtureC);
			}
		});

		contacts.addContact(fixtureA, fixtureC, new Vector2());
		contacts.addContact(fixtureA, fixtureB, normal);

		assertTrue(contacts.isInContact());
		assertThat(2, IsEqual.equalTo(contacts.getContactCount()));

		contacts.removeContact(fixtureA, fixtureB);
		assertTrue(contacts.isInContact());
		assertThat(1, IsEqual.equalTo(contacts.getContactCount()));

		Contact stillValidContact = contacts.contacts.get(0);
		assertTrue(stillValidContact.inContact);
		assertSame(fixtureA, stillValidContact.myFixture);
		assertSame(fixtureC, stillValidContact.otherFixture);

		Contact removedContact = contacts.contacts.get(1);
		assertFalse(removedContact.inContact);
		assertNull(removedContact.myFixture);
		assertNull(removedContact.otherFixture);
	}

	@Test
	public void removesFirstOfTwoContactIfItMatches() {

		final Fixture fixtureC = mockery.mock(Fixture.class);

		mockery.checking(new Expectations() {
			{
				ignoring(fixtureA);
				ignoring(fixtureB);
				ignoring(fixtureC);
			}
		});

		contacts.addContact(fixtureA, fixtureB, normal);
		contacts.addContact(fixtureA, fixtureC, new Vector2());

		assertTrue(contacts.isInContact());
		assertThat(2, IsEqual.equalTo(contacts.getContactCount()));

		contacts.removeContact(fixtureA, fixtureB);
		assertTrue(contacts.isInContact());
		assertThat(1, IsEqual.equalTo(contacts.getContactCount()));

		Contact stillValidContact = contacts.contacts.get(0);
		assertTrue(stillValidContact.inContact);
		assertSame(fixtureA, stillValidContact.myFixture);
		assertSame(fixtureC, stillValidContact.otherFixture);

		Contact removedContact = contacts.contacts.get(1);
		assertFalse(removedContact.inContact);
		assertNull(removedContact.myFixture);
		assertNull(removedContact.otherFixture);
	}

	@Test
	public void addingAContactReusesOneIfAvailable() {

		mockery.checking(new Expectations() {
			{
				ignoring(fixtureA);
				ignoring(fixtureB);
			}
		});

		Contact reusedContact = new Contact();
		contacts.contacts.add(reusedContact);
		contacts.activeContacts = 0;
		contacts.addContact(fixtureA, fixtureB, new Vector2());

		assertTrue(contacts.isInContact());
		assertThat(1, IsEqual.equalTo(contacts.getContactCount()));

		Contact contact = contacts.contacts.get(0);
		assertThat(true, IsEqual.equalTo(contact.inContact));
		assertThat(contact.myFixture, same(fixtureA));
		assertThat(contact.otherFixture, same(fixtureB));
	}

}
