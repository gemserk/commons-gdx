package com.gemserk.commons.artemis.components;

import com.artemis.Component;
import com.artemis.ComponentTypeManager;
import com.artemis.Entity;

public class GroupComponent extends Component {

	public static final int type = ComponentTypeManager.getTypeFor(GroupComponent.class).getId();

	public static GroupComponent get(Entity e) {
		return (GroupComponent) e.getComponent(type);
	}

	public String group;

	public GroupComponent(String group) {
		this.group = group;
	}

}
