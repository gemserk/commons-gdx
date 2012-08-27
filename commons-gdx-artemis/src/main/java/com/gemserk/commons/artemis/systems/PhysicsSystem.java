package com.gemserk.commons.artemis.systems;

import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.gemserk.commons.artemis.components.Components;
import com.gemserk.commons.artemis.components.PhysicsComponent;
import com.gemserk.commons.artemis.utils.PhysicsUtils;
import com.gemserk.commons.gdx.GlobalTime;

public class PhysicsSystem extends EntitySystem implements ActivableSystem, Disposable {

	private ActivableSystem activableSystem = new ActivableSystemImpl();

	World physicsWorld;
	int velocityIterations;
	int positionIterations;

	private PhysicsContactListener physicsContactListener;

	public PhysicsSystem(World physicsWorld) {
		this(physicsWorld, 6, 2);
	}
	
	@SuppressWarnings("unchecked")
	public PhysicsSystem(World physicsWorld, int velocityIterations, int positionIterations) {
		super(Components.physicsComponentClass);
		this.physicsWorld = physicsWorld;
		this.velocityIterations = velocityIterations;
		this.positionIterations = positionIterations;
		physicsContactListener = new PhysicsContactListener();
	}

	@Override
	protected void begin() {
		physicsWorld.step(GlobalTime.getDelta(), velocityIterations, positionIterations);
	}


	@Override
	protected boolean checkProcessing() {
		return isEnabled();
	}
	
	@Override
	protected void processEntities() {
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
