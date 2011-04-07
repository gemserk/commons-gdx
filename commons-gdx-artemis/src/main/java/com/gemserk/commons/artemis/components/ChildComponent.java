package com.gemserk.commons.artemis.components;

import com.artemis.Component;
import com.artemis.Entity;
import com.gemserk.componentsengine.properties.Property;

public class ChildComponent extends Component {

	private final Property<Entity> parent;
	
	public Entity getParent() {
		return parent.get();
	}

	public ChildComponent(Property<Entity> owner) {
		this.parent = owner;
		
	}

}
