package com.gemserk.commons.artemis.systems;

import static org.junit.Assert.*;

import org.junit.Test;

import com.artemis.Entity;
import com.artemis.World;
import com.gemserk.commons.artemis.components.OwnerComponent;
import com.gemserk.commons.artemis.components.RenderableComponent;

public class RenderableComponentComparatorTest {

	RenderableComponentComparator renderableComponentComparator = new RenderableComponentComparator();

	@Test
	public void shouldReturnEntityFirstIfLesserLayer() {
		World world = new World();
		Entity e1 = world.createEntity();
		Entity e2 = world.createEntity();

		e1.addComponent(new RenderableComponent(0));
		e2.addComponent(new RenderableComponent(1));

		assertEquals(true, firstEntityBeforeSecondEntity(e1, e2));
	}
	
	@Test
	public void shouldReturnEntitySecondIfGreaterLayer() {
		World world = new World();
		Entity e1 = world.createEntity();
		Entity e2 = world.createEntity();

		e1.addComponent(new RenderableComponent(0));
		e2.addComponent(new RenderableComponent(-1));

		assertEquals(true, firstEntityBeforeSecondEntity(e2, e1));
	}

	@Test
	public void shouldReturnEntityFirstIfGreaterLayerInverted() {
		World world = new World();
		Entity e1 = world.createEntity();
		Entity e2 = world.createEntity();

		e1.addComponent(new RenderableComponent(1));
		e2.addComponent(new RenderableComponent(0));

		assertEquals(false, firstEntityBeforeSecondEntity(e1, e2));
	}

	@Test
	public void shouldReturnBothEntitiesInSameLayer() {
		World world = new World();
		Entity e1 = world.createEntity();
		Entity e2 = world.createEntity();

		e1.addComponent(new RenderableComponent(5));
		e2.addComponent(new RenderableComponent(5));

		assertEquals(true, bothEntitiesSameLevel(e1, e2));
	}

	@Test
	public void shouldReturnSubEntityBeforeParentEntityIfLesserSubLayer() {
		World world = new World();

		Entity e1 = world.createEntity();
		Entity e3 = world.createEntity();

		e1.addComponent(new RenderableComponent(5, 0));
		e3.addComponent(new RenderableComponent(5, -1, true));
		
		e3.addComponent(new OwnerComponent(e1));

		assertEquals(true, firstEntityBeforeSecondEntity(e3, e1));
	}
	
	@Test
	public void shouldReturnSubEntityAfterParentEntityIfGreaterSubLayer() {
		World world = new World();

		Entity e1 = world.createEntity();
		Entity e3 = world.createEntity();

		e1.addComponent(new RenderableComponent(5, 0));
		e3.addComponent(new RenderableComponent(5, 1, true));
		
		e3.addComponent(new OwnerComponent(e1));

		assertEquals(true, firstEntityBeforeSecondEntity(e1, e3));
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

		assertEquals(true, firstEntityBeforeSecondEntity(e3, e2));
		assertEquals(false, firstEntityBeforeSecondEntity(e2, e3));
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

		assertEquals(true, firstEntityBeforeSecondEntity(e2, e3));
		assertEquals(false, firstEntityBeforeSecondEntity(e3, e2));
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

		assertEquals(true, firstEntityBeforeSecondEntity(e3, e2));
		assertEquals(false, firstEntityBeforeSecondEntity(e2, e3));
	}

	@Test
	public void testTwoSubEntitiesOrder() {
		World world = new World();

		Entity e1 = world.createEntity();
		Entity e2 = world.createEntity();
		Entity e3 = world.createEntity();
		Entity e4 = world.createEntity();

		e1.addComponent(new RenderableComponent(5, 0));
		e2.addComponent(new RenderableComponent(6, 0));
		e3.addComponent(new RenderableComponent(5, -1, true));
		e4.addComponent(new RenderableComponent(5, -1, true));
		
		e3.addComponent(new OwnerComponent(e1));
		e4.addComponent(new OwnerComponent(e2));

		assertEquals(true, firstEntityBeforeSecondEntity(e3, e4));
		assertEquals(false, firstEntityBeforeSecondEntity(e4, e3));
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

		assertEquals(true, firstEntityBeforeSecondEntity(e3, e4));
		assertEquals(false, firstEntityBeforeSecondEntity(e4, e3));
	}
	
	private boolean bothEntitiesSameLevel(Entity e1, Entity e2) {
		return renderableComponentComparator.compare(e1, e2) == 0;
	}

	private boolean firstEntityBeforeSecondEntity(Entity e1, Entity e2) {
		return renderableComponentComparator.compare(e1, e2) < 0;
	}

}
