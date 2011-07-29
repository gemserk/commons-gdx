package com.gemserk.commons.artemis;

import java.util.ArrayList;

import com.artemis.Component;
import com.artemis.Entity;
import com.artemis.World;

public class EntityBuilder {

	private final World world;

	private ArrayList<Component> components;

	private String tag;

	public EntityBuilder(World world) {
		this.world = world;
		components = new ArrayList<Component>();
		reset();
	}

	private void reset() {
		components.clear();
		tag = null;
	}

	public EntityBuilder component(Component component) {
		components.add(component);
		return this;
	}
	
	public EntityBuilder tag(String tag) {
		this.tag = tag;
		return this;
	}

	public Entity build() {
		Entity e = world.createEntity();
		if (tag != null)
			e.setTag(tag);
		for (int i = 0; i < components.size(); i++)
			e.addComponent(components.get(i));
		e.refresh();
		reset();
		return e;
	}

}
