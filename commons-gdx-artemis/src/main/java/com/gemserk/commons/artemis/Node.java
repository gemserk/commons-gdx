package com.gemserk.commons.artemis;

import com.artemis.Component;
import com.artemis.Entity;
import com.artemis.utils.Bag;

public class Node extends Component {
	
	private Entity parent;
	
	private Bag<Entity> children;

	public Node() {
		children = new Bag<Entity>();
	}

	public Node(Entity parent) {
		this();
		this.parent = parent;
	}

	public Entity getParent() {
		return parent;
	}

	public Bag<Entity> getChildren() {
		return children;
	}

	public void addChild(Entity child) {
		children.add(child);
	}

	public void removeChild(Entity child) {
		children.remove(child);
	}

}