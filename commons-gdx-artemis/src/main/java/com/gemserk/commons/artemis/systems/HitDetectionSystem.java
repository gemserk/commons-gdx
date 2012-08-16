package com.gemserk.commons.artemis.systems;

import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;
import com.gemserk.commons.artemis.components.HitComponent;
import com.gemserk.commons.artemis.components.PhysicsComponent;
import com.gemserk.commons.artemis.triggers.Trigger;
import com.gemserk.commons.gdx.box2d.Contacts;

public class HitDetectionSystem extends EntityProcessingSystem implements ActivableSystem {

	private final ActivableSystem activableSystem = new ActivableSystemImpl();

	@SuppressWarnings("unchecked")
	public HitDetectionSystem() {
		super(HitComponent.class);
	}

	@Override
	public void toggle() {
		activableSystem.toggle();
	}

	@Override
	public boolean isEnabled() {
		return activableSystem.isEnabled();
	}

	@Override
	protected void process(Entity e) {
		HitComponent hitComponent = e.getComponent(HitComponent.class);

		PhysicsComponent physicsComponent = e.getComponent(PhysicsComponent.class);
		Contacts contact = physicsComponent.getContact();
		Trigger trigger = hitComponent.getTrigger();

		if (!contact.isInContact())
			return;

		if (trigger.isAlreadyTriggered())
			return;

		trigger.trigger(e);
	}

}
