package com.gemserk.commons.artemis.systems;

import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;
import com.gemserk.commons.artemis.components.Contact;
import com.gemserk.commons.artemis.components.HitComponent;
import com.gemserk.commons.artemis.components.PhysicsComponent;
import com.gemserk.commons.artemis.triggers.Trigger;

public class HitDetectionSystem extends EntityProcessingSystem implements ActivableSystem {

	private final ActivableSystem activableSystem = new ActivableSystemImpl();

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
		Contact contact = physicsComponent.getContact();
		Trigger trigger = hitComponent.getTrigger();

		for (int i = 0; i < contact.getContactCount(); i++) {

			if (!contact.isInContact(i))
				continue;

			Entity otherEntity = contact.getEntity(i);
			if (otherEntity == null)
				continue;

			if (trigger.isAlreadyTriggered())
				return;

			trigger.trigger(e);

		}

	}

}
