package com.gemserk.commons.artemis.systems;

import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;
import com.gemserk.commons.artemis.components.TimerComponent;
import com.gemserk.commons.artemis.triggers.Trigger;

public class TimerSystem extends EntityProcessingSystem implements ActivableSystem {

	private final ActivableSystem activableSystem = new ActivableSystemImpl();

	public TimerSystem() {
		super(TimerComponent.class);
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
		TimerComponent timerComponent = e.getComponent(TimerComponent.class);
		timerComponent.setCurrentTime(timerComponent.getCurrentTime()- world.getDelta());
		
		if (!timerComponent.isFinished())
			return;
		
		Trigger trigger = timerComponent.getTrigger();
		if (trigger.isAlreadyTriggered())
			return;
		
		trigger.trigger(e);
	}

}
