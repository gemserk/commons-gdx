package com.gemserk.commons.artemis.systems;

import java.util.Comparator;

import com.artemis.Entity;
import com.gemserk.commons.artemis.components.RenderableComponent;

public class RenderableComponentComparator implements Comparator<Entity> {

	@Override
	public int compare(Entity o1, Entity o2) {
		RenderableComponent c1 = RenderableComponent.get(o1);
		RenderableComponent c2 = RenderableComponent.get(o2);
		return c1.layer - c2.layer;

		// if (c1.layer != c2.layer)
		//
		// OwnerComponent ownerComponent1 = OwnerComponent.get(o1);
		// OwnerComponent ownerComponent2 = OwnerComponent.get(o2);
		//
		// if (ownerComponent1 == null && ownerComponent2 == null)
		// return c1.layer - c2.layer;
		//
		// if (ownerComponent1 == null) {
		//
		// if (ownerComponent2.getOwner() == o1)
		// return c1.subLayer - c2.subLayer;
		// return 1;
		//
		// }
		//
		// if (ownerComponent2 == null) {
		// if (ownerComponent1.getOwner() == o2)
		// return c1.subLayer - c2.subLayer;
		// return c1.layer - c2.layer;
		// // return -1;
		// }
		//
		// Entity owner1 = ownerComponent1.getOwner();
		// Entity owner2 = ownerComponent2.getOwner();
		//
		// if (owner1 != owner2)
		// return RenderableComponent.get(owner1).layer - RenderableComponent.get(owner2).layer;
		//
		// return c1.subLayer - c2.subLayer;
	}
}