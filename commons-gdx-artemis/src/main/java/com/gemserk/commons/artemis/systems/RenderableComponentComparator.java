package com.gemserk.commons.artemis.systems;

import java.util.Comparator;

import com.artemis.Entity;
import com.gemserk.commons.artemis.components.OwnerComponent;
import com.gemserk.commons.artemis.components.RenderableComponent;

public class RenderableComponentComparator implements Comparator<Entity> {

	static class EntityComponents {
		public RenderableComponent renderableComponent;
		public OwnerComponent ownerComponent;
	}
	
	static class Factory extends EntityComponentsFactory<EntityComponents> {

		@Override
		public EntityComponents newInstance() {
			return new EntityComponents();
		}

		@Override
		public void free(EntityComponents entityComponent) {
			entityComponent.renderableComponent = null;
			entityComponent.ownerComponent = null;
		}

		@Override
		public void load(Entity e, EntityComponents entityComponent) {
			entityComponent.renderableComponent = RenderableComponent.get(e);
			entityComponent.ownerComponent = OwnerComponent.get(e);
		}
	}
	
	private final Factory factory;
	
	public RenderableComponentComparator(Factory factory) {
		this.factory = factory;
	}

	@Override
	public int compare(Entity o1, Entity o2) {
		EntityComponents entity1Components = factory.get(o1);
		EntityComponents entity2Components = factory.get(o2);
		
		RenderableComponent c1 = entity1Components.renderableComponent;
		RenderableComponent c2 = entity2Components.renderableComponent;

		if (c1.renderable.getLayer() != c2.renderable.getLayer())
			return c1.renderable.getLayer() - c2.renderable.getLayer();

		OwnerComponent ownerComponent1 = entity1Components.ownerComponent;
		OwnerComponent ownerComponent2 = entity2Components.ownerComponent;

		int id1 = o1.getId();
		int id2 = o2.getId();

		if (ownerComponent1 != null) {
			Entity owner = ownerComponent1.getOwner();
			if (owner != null)
				id1 = owner.getId();
		}

		if (ownerComponent2 != null) {
			Entity owner = ownerComponent2.getOwner();
			if (owner != null)
				id2 = owner.getId();
		}

		if (id1 != id2)
			return id1 - id2;

		return c1.renderable.getSubLayer() - c2.renderable.getSubLayer();
	}

}