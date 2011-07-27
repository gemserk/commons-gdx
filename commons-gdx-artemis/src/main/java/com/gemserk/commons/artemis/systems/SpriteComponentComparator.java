package com.gemserk.commons.artemis.systems;

import java.util.Comparator;

import com.artemis.Entity;
import com.gemserk.commons.artemis.components.RenderableComponent;

public class SpriteComponentComparator implements Comparator<Entity> {
	@Override
	public int compare(Entity o1, Entity o2) {
		RenderableComponent c1 = o1.getComponent(RenderableComponent.class);
		RenderableComponent c2 = o2.getComponent(RenderableComponent.class);
		return c1.getLayer() - c2.getLayer();
	}
}