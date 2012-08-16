package com.gemserk.commons.artemis.components;

import com.artemis.Component;
import com.artemis.ComponentTypeManager;
import com.artemis.Entity;
import com.gemserk.commons.artemis.triggers.Trigger;

public class HitComponent extends Component {
	
	public static final int type = ComponentTypeManager.getTypeFor(HitComponent.class).getId();

	public static HitComponent get(Entity e) {
		return (HitComponent) e.getComponent(type);
	}

	private final Trigger trigger;

	public Trigger getTrigger() {
		return trigger;
	}

	public HitComponent(Trigger trigger) {
		this.trigger = trigger;
	}

}
