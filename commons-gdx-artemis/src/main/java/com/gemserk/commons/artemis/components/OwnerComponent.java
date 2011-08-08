package com.gemserk.commons.artemis.components;

import com.artemis.Component;
import com.artemis.Entity;

public class OwnerComponent extends Component {

	private Entity owner;

	public Entity getOwner() {
		return owner;
	}

	public OwnerComponent(Entity owner) {
		this.owner = owner;
	}

}