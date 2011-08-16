package com.gemserk.commons.artemis.systems;

import java.util.Comparator;

import com.artemis.Entity;
import com.gemserk.commons.artemis.components.RenderableComponent;

public class RenderableComponentComparator implements Comparator<Entity> {
	
	private final static Class<RenderableComponent> renderableComponentClass = RenderableComponent.class;
	
	@Override
	public int compare(Entity o1, Entity o2) {
		RenderableComponent c1 = o1.getComponent(renderableComponentClass);
		RenderableComponent c2 = o2.getComponent(renderableComponentClass);
		return c1.getLayer() - c2.getLayer();
	}
}