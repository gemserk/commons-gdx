package com.gemserk.commons.artemis.components;

import java.util.ArrayList;

import com.artemis.Component;
import com.artemis.ComponentTypeManager;
import com.artemis.Entity;

public class ParentComponent extends Component {
	
	public static final int type = ComponentTypeManager.getTypeFor(ParentComponent.class).getId();

	public static ParentComponent get(Entity e) {
		return (ParentComponent) e.getComponent(type);
	}

	private final ArrayList<Entity> children;
	
	public ArrayList<Entity> getChildren() {
		return children;
	}
	
	public void addChild(Entity child){ 
		children.add(child);
	}
	
	public void addChildren(ArrayList<Entity> children) {
		this.children.addAll(children);
	}
	
	public ParentComponent(ArrayList<Entity> children) {
		this.children = children;
	}

	public ParentComponent() {
		this(new ArrayList<Entity>());
	}

}
