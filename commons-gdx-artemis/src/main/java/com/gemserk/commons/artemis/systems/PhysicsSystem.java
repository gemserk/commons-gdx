package com.gemserk.commons.artemis.systems;

import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.gemserk.commons.artemis.components.LinearVelocityLimitComponent;
import com.gemserk.commons.artemis.components.PhysicsComponent;
import com.gemserk.commons.gdx.GlobalTime;
import com.gemserk.commons.gdx.box2d.Contacts;
import com.gemserk.commons.gdx.box2d.Contacts.Contact;

public class PhysicsSystem extends EntityProcessingSystem implements ActivableSystem, Disposable {

	private static final Vector2 antiGravity = new Vector2(0, 10f);

	private ActivableSystem activableSystem = new ActivableSystemImpl();

	private final Vector2 bodyAntiGravity = new Vector2(0, 10f);

	World physicsWorld;

	private PhysicsContactListener physicsContactListener;

	public PhysicsSystem(World physicsWorld) {
		super(PhysicsComponent.class);
		this.physicsWorld = physicsWorld;
		physicsContactListener = new PhysicsContactListener();
	}

	@Override
	protected void begin() {
		physicsWorld.step(GlobalTime.getDelta(), 6, 2);
	}

	@Override
	protected void process(Entity e) {

		// synchronize sizes between spatial and physics components.

		PhysicsComponent physicsComponent = e.getComponent(PhysicsComponent.class);
		Body body = physicsComponent.getBody();

		// AntiGravityComponent antiGravityComponent = e.getComponent(AntiGravityComponent.class);
		// if (antiGravityComponent != null) {
		//
		// bodyAntiGravity.set(antiGravity);
		// bodyAntiGravity.mul(body.getMass());
		//
		// body.applyForce(bodyAntiGravity, body.getTransform().getPosition());
		// }

		LinearVelocityLimitComponent limitComponent = e.getComponent(LinearVelocityLimitComponent.class);
		if (limitComponent != null) {
			Vector2 linearVelocity = body.getLinearVelocity();

			float speed = linearVelocity.len();
			float maxSpeed = limitComponent.getLimit();

			if (speed > maxSpeed) {
				float factor = maxSpeed / speed;
				linearVelocity.mul(factor);
				body.setLinearVelocity(linearVelocity);
			}
		}

	}

	@Override
	protected boolean checkProcessing() {
		return isEnabled();
	}

	@Override
	protected void removed(Entity e) {

		// on entity removed, we should remove body from physics world

		PhysicsComponent component = e.getComponent(PhysicsComponent.class);

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

			PhysicsComponent otherPhyiscsComponent = otherEntity.getComponent(PhysicsComponent.class);
			otherPhyiscsComponent.getContact().removeContact(contact.getOtherFixture(), contact.getMyFixture());
		}

		physicsWorld.destroyBody(body);

	}

	@Override
	public void initialize() {
		physicsWorld.setContactListener(physicsContactListener);
	}

	public World getPhysicsWorld() {
		return physicsWorld;
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
