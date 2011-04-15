package com.gemserk.commons.artemis.components;

import com.artemis.Component;
import com.artemis.Entity;
import com.gemserk.componentsengine.properties.Property;
import com.gemserk.componentsengine.properties.PropertyBuilder;

public class ChildComponent extends Component {

	private final Property<Entity> parent;
	
	public Entity getParent() {
		return parent.get();
	}

	public ChildComponent(Property<Entity> owner) {
		this.parent = owner;
	}
	
	public ChildComponent(Entity owner) {
		this(PropertyBuilder.property(owner));
	}

}
