package com.gemserk.commons.artemis.triggers;

import com.artemis.Entity;

public interface Trigger {

	/**
	 * Returns true if the trigger was already triggered and should not be triggered again, false otherwise.
	 */
	boolean isAlreadyTriggered();

	/**
	 * Triggers the Trigger with its owner Entity.
	 */
	void trigger(Entity e);

}