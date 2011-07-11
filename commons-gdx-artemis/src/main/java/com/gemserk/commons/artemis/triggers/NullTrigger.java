package com.gemserk.commons.artemis.triggers;

import com.artemis.Entity;

/**
 * Null implementation of Trigger interface.
 * 
 * @author acoppes
 */
public class NullTrigger implements Trigger {

	@Override
	public boolean isAlreadyTriggered() {
		return false;
	}

	@Override
	public void trigger(Entity e) {

	}

}
