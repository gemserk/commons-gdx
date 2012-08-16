package com.gemserk.commons.artemis.components;

import com.artemis.Component;
import com.artemis.ComponentTypeManager;
import com.artemis.Entity;

public class OwnerComponent extends Component {

	public static final int type = ComponentTypeManager.getTypeFor(OwnerComponent.class).getId();

	public static OwnerComponent get(Entity e) {
		return (OwnerComponent) e.getComponent(type);
	}

	private Entity owner;

	public Entity getOwner() {
		return owner;
	}
	
	public void setOwner(Entity owner) {
		this.owner = owner;
	}

	public OwnerComponent(Entity owner) {
		this.owner = owner;
	}

}