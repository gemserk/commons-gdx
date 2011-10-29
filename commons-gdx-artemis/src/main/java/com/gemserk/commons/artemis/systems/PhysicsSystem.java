package com.gemserk.commons.artemis.systems;

import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.gemserk.commons.artemis.components.Components;
import com.gemserk.commons.artemis.components.PhysicsComponent;
import com.gemserk.commons.gdx.GlobalTime;
import com.gemserk.commons.gdx.box2d.Contacts;
import com.gemserk.commons.gdx.box2d.Contacts.Contact;

public class PhysicsSystem extends EntityProcessingSystem implements ActivableSystem, Disposable {

	private ActivableSystem activableSystem = new ActivableSystemImpl();

	World physicsWorld;

	private PhysicsContactListener physicsContactListener;

	public PhysicsSystem(World physicsWorld) {
		super(Components.physicsComponentClass);
		this.physicsWorld = physicsWorld;
		physicsContactListener = new PhysicsContactListener();
	}

	@Override
	protected void begin() {
		physicsWorld.step(GlobalTime.getDelta(), 6, 2);
	}

	@Override
	protected void process(Entity e) {
		
	}

	@Override
	protected boolean checkProcessing() {
		return isEnabled();
	}

	@Override
	protected void removed(Entity e) {

		// on entity removed, we should remove body from physics world

		PhysicsComponent component = Components.physicsComponent(e);

		if (component == null) {
			return;
		}

		Body body = component.getBody();
		body.setUserData(null);

		Contacts contacts = component.getContact();

		// removes contact from the other entity
		for (int i = 0; i < contacts.getContactCount(); i++) {
			Contact contact = contacts.getContact(i);

			Body otherBody = contact.getOtherFixture().getBody();
			if (otherBody == null)
				continue;

			Entity otherEntity = (Entity) otherBody.getUserData();
			if (otherEntity == null)
				continue;

			PhysicsComponent otherPhyiscsComponent = Components.physicsComponent(otherEntity);
			otherPhyiscsComponent.getContact().removeContact(contact.getOtherFixture(), contact.getMyFixture());
		}

		physicsWorld.destroyBody(body);

	}

	@Override
	public void initialize() {
		physicsWorld.setContactListener(physicsContactListener);
	}

	public void toggle() {
		activableSystem.toggle();
	}

	public boolean isEnabled() {
		return activableSystem.isEnabled();
	}

	@Override
	public void dispose() {
		physicsWorld.dispose();
	}

}
