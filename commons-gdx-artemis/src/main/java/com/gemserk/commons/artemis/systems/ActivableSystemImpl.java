package com.gemserk.commons.artemis.systems;

public class ActivableSystemImpl implements ActivableSystem {
	
	private boolean enabled = true;

	protected void disable() {
		enabled = false;
	}

	protected void enable() {
		enabled = true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public void toggle() {
		if (isEnabled())
			disable();
		else
			enable();
	}

}
