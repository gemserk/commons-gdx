package com.gemserk.commons.artemis.systems;

public interface ActivableSystem {
	
	void toggle();
	
	boolean isEnabled();
	
}
