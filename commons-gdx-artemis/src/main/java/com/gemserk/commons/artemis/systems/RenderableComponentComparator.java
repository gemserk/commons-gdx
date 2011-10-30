package com.gemserk.commons.artemis.systems;

import java.util.Comparator;

import com.artemis.Entity;
import com.gemserk.commons.artemis.components.Components;
import com.gemserk.commons.artemis.components.RenderableComponent;

public class RenderableComponentComparator implements Comparator<Entity> {
	
	@Override
	public int compare(Entity o1, Entity o2) {
		RenderableComponent c1 = Components.getRenderableComponent(o1);
		RenderableComponent c2 = Components.getRenderableComponent(o2);
		return c1.getLayer() - c2.getLayer();
	}
}