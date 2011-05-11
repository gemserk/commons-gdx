package com.gemserk.commons.artemis.components;

import com.artemis.Component;
import com.gemserk.commons.artemis.triggers.Trigger;

public class HitComponent extends Component {

	private final Trigger trigger;

	public Trigger getTrigger() {
		return trigger;
	}

	public HitComponent(Trigger trigger) {
		this.trigger = trigger;
	}

}
