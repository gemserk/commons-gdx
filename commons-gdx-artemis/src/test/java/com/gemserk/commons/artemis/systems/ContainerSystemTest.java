package com.gemserk.commons.artemis.systems;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNull;
import org.junit.Test;

import com.artemis.Entity;
import com.artemis.World;
import com.gemserk.commons.artemis.WorldWrapper;
import com.gemserk.commons.artemis.components.ContainerComponent;
import com.gemserk.commons.artemis.components.OwnerComponent;

public class ContainerSystemTest {

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
		assertThat(containerComponent.getChildren().contains(e2), IsEqual.equalTo(true));
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
