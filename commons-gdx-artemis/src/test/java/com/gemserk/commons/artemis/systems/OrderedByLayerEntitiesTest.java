package com.gemserk.commons.artemis.systems;

import static org.junit.Assert.assertSame;

import org.junit.Test;

import com.artemis.Entity;
import com.artemis.World;
import com.gemserk.commons.artemis.components.OwnerComponent;
import com.gemserk.commons.artemis.components.RenderableComponent;

public class OrderedByLayerEntitiesTest {

	OrderedByLayerEntities orderedByLayerEntities = new OrderedByLayerEntities(-100, 100);
	
	@Test
	public void shouldReturnEntityFirstIfLesserLayer() {
		World world = new World();
		Entity e1 = world.createEntity();
		Entity e2 = world.createEntity();

		e1.addComponent(new RenderableComponent(0));
		e2.addComponent(new RenderableComponent(1));

		orderedByLayerEntities.add(e1);
		orderedByLayerEntities.add(e2);

		assertSame(orderedByLayerEntities.get(0), e1);
		assertSame(orderedByLayerEntities.get(1), e2);
	}

	@Test
	public void shouldReturnEntitySecondIfGreaterLayer() {
		World world = new World();
		Entity e1 = world.createEntity();
		Entity e2 = world.createEntity();

		e1.addComponent(new RenderableComponent(0));
		e2.addComponent(new RenderableComponent(-1));

		orderedByLayerEntities.add(e1);
		orderedByLayerEntities.add(e2);

		assertSame(orderedByLayerEntities.get(0), e2);
		assertSame(orderedByLayerEntities.get(1), e1);
	}

	@Test
	public void shouldReturnEntityFirstIfGreaterLayerInverted() {
		World world = new World();
		Entity e1 = world.createEntity();
		Entity e2 = world.createEntity();

		e1.addComponent(new RenderableComponent(1));
		e2.addComponent(new RenderableComponent(0));

		orderedByLayerEntities.add(e1);
		orderedByLayerEntities.add(e2);

		assertSame(orderedByLayerEntities.get(0), e2);
		assertSame(orderedByLayerEntities.get(1), e1);
	}

	@Test
	public void testOrderByInsertionOrder() {
		World world = new World();
		Entity e1 = world.createEntity();
		Entity e2 = world.createEntity();

		e1.addComponent(new RenderableComponent(5));
		e2.addComponent(new RenderableComponent(5));

		orderedByLayerEntities.add(e1);
		orderedByLayerEntities.add(e2);

		assertSame(orderedByLayerEntities.get(0), e1);
		assertSame(orderedByLayerEntities.get(1), e2);
	}

	@Test
	public void shouldReturnSubEntityBeforeParentEntityIfLesserSubLayer() {
		World world = new World();

		Entity e1 = world.createEntity();
		Entity e3 = world.createEntity();

		e1.addComponent(new RenderableComponent(5, 0));
		e3.addComponent(new RenderableComponent(5, -1, true));

		e3.addComponent(new OwnerComponent(e1));

		orderedByLayerEntities.add(e1);
		orderedByLayerEntities.add(e3);

		assertSame(orderedByLayerEntities.get(0), e3);
		assertSame(orderedByLayerEntities.get(1), e1);
	}

	@Test
	public void shouldReturnSubEntityAfterParentEntityIfGreaterSubLayer() {
		World world = new World();

		Entity e1 = world.createEntity();
		Entity e3 = world.createEntity();

		e1.addComponent(new RenderableComponent(5, 0));
		e3.addComponent(new RenderableComponent(5, 1, true));

		e3.addComponent(new OwnerComponent(e1));

		orderedByLayerEntities.add(e1);
		orderedByLayerEntities.add(e3);

		assertSame(orderedByLayerEntities.get(0), e1);
		assertSame(orderedByLayerEntities.get(1), e3);
	}

	@Test
	public void shouldReturnSubEntityBeforeAnotherEntityIfParentEntityBeforeThatOne() {
		World world = new World();

		Entity e1 = world.createEntity();
		Entity e2 = world.createEntity();
		Entity e3 = world.createEntity();

		e1.addComponent(new RenderableComponent(5, 0));
		e2.addComponent(new RenderableComponent(5));
		e3.addComponent(new RenderableComponent(5, 1, true));

		e3.addComponent(new OwnerComponent(e1));

		orderedByLayerEntities.add(e1);
		orderedByLayerEntities.add(e2);
		orderedByLayerEntities.add(e3);

		assertSame(orderedByLayerEntities.get(0), e1);
		assertSame(orderedByLayerEntities.get(1), e3);
		assertSame(orderedByLayerEntities.get(2), e2);
	}

	@Test
	public void testSubEntitiesAfterAnotherEntityIfParentIsAfter() {
		World world = new World();

		Entity e1 = world.createEntity();
		Entity e2 = world.createEntity();
		Entity e3 = world.createEntity();

		e1.addComponent(new RenderableComponent(6, 0));
		e2.addComponent(new RenderableComponent(5));
		e3.addComponent(new RenderableComponent(6, 1, true));

		e3.addComponent(new OwnerComponent(e1));

		orderedByLayerEntities.add(e1);
		orderedByLayerEntities.add(e2);
		orderedByLayerEntities.add(e3);

		assertSame(orderedByLayerEntities.get(0), e2);
		assertSame(orderedByLayerEntities.get(1), e1);
		assertSame(orderedByLayerEntities.get(2), e3);
	}

	@Test
	public void testSubEntitiesAfterAnotherEntityIfParentIsAfter2() {
		World world = new World();

		Entity e1 = world.createEntity();
		Entity e2 = world.createEntity();
		Entity e3 = world.createEntity();

		e1.addComponent(new RenderableComponent(5));
		e2.addComponent(new RenderableComponent(5));
		e3.addComponent(new RenderableComponent(5, 2, true));

		e3.addComponent(new OwnerComponent(e1));

		orderedByLayerEntities.add(e2);
		orderedByLayerEntities.add(e1);
		orderedByLayerEntities.add(e3);

		assertSame(orderedByLayerEntities.get(0), e1);
		assertSame(orderedByLayerEntities.get(1), e3);
		assertSame(orderedByLayerEntities.get(2), e2);
	}

	@Test
	public void shouldReturnSubEntityBeforeAnotherEntityIfParentEntityBeforeThatOne2() {
		World world = new World();

		Entity e1 = world.createEntity();
		Entity e2 = world.createEntity();
		Entity e3 = world.createEntity();

		e1.addComponent(new RenderableComponent(5, 0));
		e2.addComponent(new RenderableComponent(5));
		e3.addComponent(new RenderableComponent(5, -1, true));

		e3.addComponent(new OwnerComponent(e1));
		
		orderedByLayerEntities.add(e1);
		orderedByLayerEntities.add(e2);
		orderedByLayerEntities.add(e3);

		assertSame(orderedByLayerEntities.get(0), e3);
		assertSame(orderedByLayerEntities.get(1), e1);
		assertSame(orderedByLayerEntities.get(2), e2);
	}

	@Test
	public void testTwoSubEntitiesOrder() {
		World world = new World();

		Entity e1 = world.createEntity();
		Entity e2 = world.createEntity();
		Entity e3 = world.createEntity();
		Entity e4 = world.createEntity();

		e1.addComponent(new RenderableComponent(5));
		e2.addComponent(new RenderableComponent(6));
		e3.addComponent(new RenderableComponent(5, -1, true));
		e4.addComponent(new RenderableComponent(5, -1, true));

		e3.addComponent(new OwnerComponent(e1));
		e4.addComponent(new OwnerComponent(e2));
		
		orderedByLayerEntities.add(e1);
		orderedByLayerEntities.add(e2);
		orderedByLayerEntities.add(e3);
		orderedByLayerEntities.add(e4);

		assertSame(orderedByLayerEntities.get(0), e3);
		assertSame(orderedByLayerEntities.get(1), e1);
		assertSame(orderedByLayerEntities.get(2), e4);
		assertSame(orderedByLayerEntities.get(3), e2);
	}

	@Test
	public void testTwoSubEntities2() {
		World world = new World();

		Entity e1 = world.createEntity();
		Entity e3 = world.createEntity();
		Entity e4 = world.createEntity();

		e1.addComponent(new RenderableComponent(5, 0));
		e3.addComponent(new RenderableComponent(5, 6, true));
		e4.addComponent(new RenderableComponent(5, 8, true));

		e3.addComponent(new OwnerComponent(e1));
		e4.addComponent(new OwnerComponent(e1));
		
		orderedByLayerEntities.add(e1);
		orderedByLayerEntities.add(e3);
		orderedByLayerEntities.add(e4);

		assertSame(orderedByLayerEntities.get(0), e1);
		assertSame(orderedByLayerEntities.get(1), e3);
		assertSame(orderedByLayerEntities.get(2), e4);
	}
	
	@Test
	public void bugWhenOrderingSubEntity() {
		World world = new World();

		Entity e1 = world.createEntity();
		Entity e2 = world.createEntity();
		Entity e3 = world.createEntity();

		e1.addComponent(new RenderableComponent(5));
		e2.addComponent(new RenderableComponent(5));
		e3.addComponent(new RenderableComponent(5, -1));
		
		e3.addComponent(new OwnerComponent(e2));

		orderedByLayerEntities.add(e1);
		orderedByLayerEntities.add(e2);
		orderedByLayerEntities.add(e3);

		assertSame(orderedByLayerEntities.get(0), e1);
		assertSame(orderedByLayerEntities.get(1), e3);
		assertSame(orderedByLayerEntities.get(2), e2);
	}
	
	@Test
	public void bugEntityWithNullOwner() {
		World world = new World();

		Entity e1 = world.createEntity();
		Entity e2 = world.createEntity();

		e1.addComponent(new RenderableComponent(5));
		e2.addComponent(new RenderableComponent(5));
		e2.addComponent(new OwnerComponent(null));

		orderedByLayerEntities.add(e1);
		orderedByLayerEntities.add(e2);

		assertSame(orderedByLayerEntities.get(0), e1);
		assertSame(orderedByLayerEntities.get(1), e2);
	}
	
	@Test
	public void bugEntityWithNullOwner2() {
		World world = new World();

		Entity e1 = world.createEntity();
		Entity e2 = world.createEntity();

		e1.addComponent(new RenderableComponent(5));
		e2.addComponent(new RenderableComponent(5));
		e2.addComponent(new OwnerComponent(null));

		orderedByLayerEntities.add(e2);
		orderedByLayerEntities.add(e1);

		assertSame(orderedByLayerEntities.get(0), e1);
		assertSame(orderedByLayerEntities.get(1), e2);
	}


}
