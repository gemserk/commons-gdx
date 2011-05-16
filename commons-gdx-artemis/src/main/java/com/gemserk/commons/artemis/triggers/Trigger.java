package com.gemserk.commons.artemis.triggers;

import com.artemis.Entity;

public interface Trigger {

	/**
	 * Returns true if the trigger was already triggered and should not be triggered again, false otherwise.
	 */
	boolean isAlreadyTriggered();

	// TODO: isAlreadyTriggered could be changed to shouldBeTriggered() so Triggers implement whether they should be called or not based on some stuff, also use world and entity.

	/**
	 * Triggers the Trigger with its owner Entity.
	 */
	void trigger(Entity e);

	// TODO: I would like to add extra stuff like the World and delta for example, so the Trigger could be really independent, like a Script.

}