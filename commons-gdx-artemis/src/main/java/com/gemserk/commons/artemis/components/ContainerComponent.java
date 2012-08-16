package com.gemserk.commons.artemis.components;

import com.artemis.Component;
import com.artemis.ComponentTypeManager;
import com.artemis.Entity;
import com.gemserk.componentsengine.utils.RandomAccessSet;

public class ContainerComponent extends Component {

	public static final int type = ComponentTypeManager.getTypeFor(ContainerComponent.class).getId();

	public static ContainerComponent get(Entity e) {
		return (ContainerComponent) e.getComponent(type);
	}

	private RandomAccessSet<Entity> children = new RandomAccessSet<Entity>();
	
	public RandomAccessSet<Entity> getChildren() {
		return children;
	}

}