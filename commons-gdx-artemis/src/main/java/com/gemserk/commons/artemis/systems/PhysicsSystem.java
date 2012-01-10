package com.gemserk.commons.artemis.systems;

import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.gemserk.commons.artemis.components.Components;
import com.gemserk.commons.artemis.components.PhysicsComponent;
import com.gemserk.commons.artemis.utils.PhysicsUtils;
import com.gemserk.commons.gdx.GlobalTime;
import com.gemserk.commons.gdx.box2d.Contacts;

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
		// make the velocity and position steps variable
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
	protected void disabled(Entity e) {
		PhysicsComponent physicsComponent = Components.getPhysicsComponent(e);
		physicsComponent.getBody().setActive(false);
		PhysicsUtils.releaseContacts(physicsComponent.getContact());
	}

	@Override
	protected void enabled(Entity e) {
		PhysicsComponent physicsComponent = Components.getPhysicsComponent(e);
		physicsComponent.getBody().setActive(true);
	}

	@Override
	protected void removed(Entity e) {
		PhysicsComponent physicsComponent = Components.getPhysicsComponent(e);

		if (physicsComponent == null)
			return;

		Body body = physicsComponent.getBody();
		body.setUserData(null);

		// removes contact from the other entity
		PhysicsUtils.releaseContacts(physicsComponent.getContact());

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
