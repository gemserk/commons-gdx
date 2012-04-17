package com.gemserk.commons.artemis.systems;

import java.util.Comparator;

import com.artemis.Entity;
import com.gemserk.commons.artemis.components.OwnerComponent;
import com.gemserk.commons.artemis.components.RenderableComponent;

public class RenderableComponentComparator implements Comparator<Entity> {

	@Override
	public int compare(Entity o1, Entity o2) {
		RenderableComponent c1 = RenderableComponent.get(o1);
		RenderableComponent c2 = RenderableComponent.get(o2);

		if (c1.layer != c2.layer)
			return c1.layer - c2.layer;

		OwnerComponent ownerComponent1 = OwnerComponent.get(o1);
		OwnerComponent ownerComponent2 = OwnerComponent.get(o2);

		int id1 = o1.getId();
		int id2 = o2.getId();

		if (ownerComponent1 != null)
			id1 = ownerComponent1.getOwner().getId();

		if (ownerComponent2 != null)
			id2 = ownerComponent2.getOwner().getId();

		if (id1 != id2)
			return id1 - id2;

		return c1.subLayer - c2.subLayer;
	}
	
}