package com.gemserk.commons.artemis.components;

import com.artemis.Component;
import com.artemis.ComponentTypeManager;
import com.artemis.Entity;

public class TagComponent extends Component {
	
	public static final int type = ComponentTypeManager.getTypeFor(TagComponent.class).getId();

	public static TagComponent get(Entity e) {
		return (TagComponent) e.getComponent(type);
	}
	
	private final String tag;
	
	public String getTag() {
		return tag;
	}
	
	public TagComponent(String tag) {
		this.tag = tag;
	}

}
