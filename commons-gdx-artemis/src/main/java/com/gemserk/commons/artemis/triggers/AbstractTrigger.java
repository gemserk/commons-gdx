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

	/**
	 * Called when the trigger was called, should return true if it was handled an doesn't want to be handled again, false other wise.
	 */
	protected abstract boolean handle(Entity e);

}
