package com.gemserk.commons.artemis.systems;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNull;
import org.junit.Test;

import com.artemis.Component;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;
import com.artemis.World;
import com.gemserk.commons.artemis.WorldWrapper;
import com.gemserk.componentsengine.utils.RandomAccessSet;

public class ContainerSystemTest {

	class ContainerComponent extends Component {

		private RandomAccessSet<Entity> children = new RandomAccessSet<Entity>();

		public RandomAccessSet<Entity> getChildren() {
			return children;
		}

	}

	class OwnerComponent extends Component {

		private Entity owner;

		public Entity getOwner() {
			return owner;
		}

		public OwnerComponent(Entity owner) {
			this.owner = owner;
		}

	}

	public static class ContainerSystem extends EntityProcessingSystem {

		public ContainerSystem() {
			super(ContainerComponent.class);
		}

		@Override
		protected void removed(Entity e) {
			ContainerComponent containerComponent = e.getComponent(ContainerComponent.class);
			for (int i = 0; i < containerComponent.children.size(); i++)
				containerComponent.children.get(i).delete();
		}

		@Override
		protected void process(Entity e) {

		}

	}

	public static class OwnerSystem extends EntityProcessingSystem {

		public OwnerSystem() {
			super(OwnerComponent.class);
		}

		@Override
		protected void added(Entity e) {
			OwnerComponent ownerComponent = e.getComponent(OwnerComponent.class);
			if (ownerComponent.owner == null)
				return;

			ContainerComponent containerComponent = ownerComponent.owner.getComponent(ContainerComponent.class);
			containerComponent.children.add(e);
		}

		protected void removed(Entity e) {
			OwnerComponent ownerComponent = e.getComponent(OwnerComponent.class);
			if (ownerComponent.owner == null)
				return;

			ContainerComponent containerComponent = ownerComponent.owner.getComponent(ContainerComponent.class);
			if (containerComponent == null)
				return;

			containerComponent.children.remove(e);
		}

		@Override
		protected void process(Entity e) {

		}

	}

	@Test
	public void shouldBeAddedToParentContainer() {
		World world = new World();
		WorldWrapper worldWrapper = new WorldWrapper(world);
		worldWrapper.addUpdateSystem(new ContainerSystem());
		worldWrapper.addUpdateSystem(new OwnerSystem());
		worldWrapper.init();

		Entity e1 = world.createEntity();
		e1.addComponent(new ContainerComponent());
		e1.refresh();

		Entity e2 = world.createEntity();
		e2.addComponent(new OwnerComponent(e1));
		e2.refresh();

		worldWrapper.update(10);

		ContainerComponent containerComponent = e1.getComponent(ContainerComponent.class);
		assertThat(containerComponent.children.contains(e2), IsEqual.equalTo(true));
	}

	@Test
	public void shouldRemoveChildrenIfParentRemoved() {
		World world = new World();
		WorldWrapper worldWrapper = new WorldWrapper(world);
		worldWrapper.addUpdateSystem(new ContainerSystem());
		worldWrapper.addUpdateSystem(new OwnerSystem());
		worldWrapper.init();

		Entity e1 = world.createEntity();
		e1.addComponent(new ContainerComponent());
		e1.refresh();

		Entity e2 = world.createEntity();
		e2.addComponent(new OwnerComponent(e1));
		e2.refresh();

		worldWrapper.update(10);

		world.deleteEntity(e1);

		worldWrapper.update(1);
		worldWrapper.update(1);

		Entity e3 = world.getEntity(e2.getId());
		assertThat(e3, IsNull.nullValue());
	}

	// TEST: removing the container component should remove children too?

}
