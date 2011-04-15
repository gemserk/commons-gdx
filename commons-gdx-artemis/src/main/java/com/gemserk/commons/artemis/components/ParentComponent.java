package com.gemserk.commons.artemis.components;

import java.util.ArrayList;

import com.artemis.Component;
import com.artemis.Entity;
import com.gemserk.componentsengine.properties.Property;
import com.gemserk.componentsengine.properties.PropertyBuilder;

public class ParentComponent extends Component {

	private final Property<ArrayList<Entity>> children;
	
	public ArrayList<Entity> getChildren() {
		return children.get();
	}
	
	public void addChild(Entity child){ 
		children.get().add(child);
	}
	
	public ParentComponent(Property<ArrayList<Entity>> children) {
		this.children = children;
	}
	
	public ParentComponent(ArrayList<Entity> children) {
		this(PropertyBuilder.property(children));
	}

	public ParentComponent() {
		this(new ArrayList<Entity>());
	}

}
