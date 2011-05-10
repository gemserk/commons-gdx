package com.gemserk.commons.artemis.triggers;

import com.artemis.Entity;

public abstract class AbstractTrigger implements Trigger {
	
	private boolean alreadyTriggered = false;

	@Override
	public boolean isAlreadyTriggered() {
		return alreadyTriggered;
	}
	
	@Override
	public void trigger(Entity e) {
		alreadyTriggered = handle(e);
	}
	
	protected abstract boolean handle(Entity owner);
	
}
