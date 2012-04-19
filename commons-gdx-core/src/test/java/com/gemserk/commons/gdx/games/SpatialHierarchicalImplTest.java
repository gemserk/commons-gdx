package com.gemserk.commons.gdx.games;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

public class SpatialHierarchicalImplTest {

	@Test
	public void shouldReturnParentPosition() {
		float x = 20f;
		float y = 50f;

		Spatial parent = new SpatialImpl(x, y, 1f, 1f, 0f);

		Spatial child = new SpatialHierarchicalImpl(parent, 1f, 1f);

		assertThat(child.getX(), IsEqual.equalTo(x));
		assertThat(child.getY(), IsEqual.equalTo(y));
	}

	@Test
	public void shouldSetAbsolutePositionWhenSettingChildPosition() {
		Spatial parent = new SpatialImpl(10f, 20f, 1f, 1f, 0f);

		Spatial child = new SpatialHierarchicalImpl(parent, 1f, 1f);

		child.setPosition(35f, 55f);

		assertThat(child.getX(), IsEqual.equalTo(35f));
		assertThat(child.getY(), IsEqual.equalTo(55f));
		assertThat(child.getPosition().x, IsEqual.equalTo(35f));
		assertThat(child.getPosition().y, IsEqual.equalTo(55f));
	}

	@Test
	public void shouldCalculateOnGet() {
		Spatial parent = new SpatialImpl(10f, 20f, 1f, 1f, 0f);

		Spatial child = new SpatialHierarchicalImpl(parent, 1f, 1f);

		child.setPosition(35f, 55f);
		parent.setPosition(0f, 0f);

		assertThat(child.getX(), IsEqual.equalTo(25f));
		assertThat(child.getY(), IsEqual.equalTo(35f));
		assertThat(child.getPosition().x, IsEqual.equalTo(25f));
		assertThat(child.getPosition().y, IsEqual.equalTo(35f));
	}

	@Test
	public void shouldReturnParentAngle() {
		Spatial parent = new SpatialImpl(0f, 0f, 1f, 1f, 55f);
		Spatial child = new SpatialHierarchicalImpl(parent, 1f, 1f);
		assertThat(child.getAngle(), IsEqual.equalTo(55f));
	}

	@Test
	public void shouldSetAngle() {
		Spatial parent = new SpatialImpl(0f, 0f, 1f, 1f, 55f);
		Spatial child = new SpatialHierarchicalImpl(parent, 1f, 1f);
		child.setAngle(25f);
		assertThat(child.getAngle(), IsEqual.equalTo(25f));
	}

	@Test
	public void shouldRecalculateAngleBasedOnParentAngle() {
		Spatial parent = new SpatialImpl(0f, 0f, 1f, 1f, 55f);
		Spatial child = new SpatialHierarchicalImpl(parent, 1f, 1f);
		child.setAngle(25f);
		parent.setAngle(75f);
		assertThat(child.getAngle(), IsEqual.equalTo(45f));
	}

	@Test
	public void shouldRecalculatePositionBasedOnParentAngle() {
		Spatial parent = new SpatialImpl(0f, 0f, 1f, 1f, 0f);
		Spatial child = new SpatialHierarchicalImpl(parent, 1f, 1f);
		child.setPosition(100f, 0f);
		parent.setAngle(90f);
		assertEquals(0f, child.getX(), 0.1f);
		assertEquals(100f, child.getY(), 0.1f);
	}
	
	@Test
	public void shouldReturnCorrectAngleForMultipleChilds() {
		Spatial parent = new SpatialImpl(0f, 0f, 1f, 1f, 0f);
		Spatial child = new SpatialHierarchicalImpl(parent, 1f, 1f);
		Spatial third = new SpatialHierarchicalImpl(child, 1f, 1f);
		
		child.setPosition(10f, 0f);
		third.setPosition(20f, 0f);
		
		parent.setAngle(90f);
		
		assertEquals(90f, child.getAngle(), 0.1f);
		assertEquals(90f, third.getAngle(), 0.1f);
		
		child.setAngle(120f);
		
		assertEquals(120f, third.getAngle(), 0.1f);
	}
	
	@Test
	public void shouldReturnCorrectPositionForMultipleChilds() {
		Spatial parent = new SpatialImpl(0f, 0f, 1f, 1f, 0f);
		Spatial child = new SpatialHierarchicalImpl(parent, 1f, 1f);
		Spatial third = new SpatialHierarchicalImpl(child, 1f, 1f);
		
		child.setPosition(20f, 0f);
		third.setPosition(30f, 0f);
		
		assertEquals(30f, third.getX(), 0.1f);
		assertEquals(0f, third.getY(), 0.1f);

		child.setAngle(0f);
		parent.setAngle(90f);
		
		assertEquals(90f, child.getAngle(), 0.1f);
		assertEquals(0f, child.getX(), 0.1f);
		assertEquals(20f, child.getY(), 0.1f);
		
		assertEquals(90f, third.getAngle(), 0.1f);
		assertEquals(0f, third.getX(), 0.1f);
		assertEquals(30f, third.getY(), 0.1f);
	}
	
	@Test
	public void shouldModifyLocalPosition() {
		Spatial parent = new SpatialImpl(50f, 50f, 1f, 1f, 0f);
		SpatialHierarchicalImpl child = new SpatialHierarchicalImpl(parent, 1f, 1f);
		
		assertEquals(50f, child.getX(), 0.1f);
		assertEquals(50f, child.getY(), 0.1f);
		
		child.setLocalPosition(2f, 5f);
		
		assertEquals(52f, child.getX(), 0.1f);
		assertEquals(55f, child.getY(), 0.1f);
	}
	
	@Test
	public void testOfTheNiceDrawing() {
		Spatial parent = new SpatialImpl(10f, 10f, 1f, 1f, -45f);
		SpatialHierarchicalImpl child = new SpatialHierarchicalImpl(parent, 1f, 1f);
		
		child.setPosition(10f, 15f);
		child.setAngle(-45f);
		
		assertEquals(10f, child.getX(), 0.1f);
		assertEquals(15f, child.getY(), 0.1f);
		
		assertEquals(-45, child.getAngle(), 0.1f);
	}
	
	@Test
	public void testChangeOfParent() {
		Spatial parent1 = new SpatialImpl(10f, 10f, 1f, 1f, -45f);
		Spatial parent2 = new SpatialImpl(50f, 60f, 1f, 1f,  78f);
		
		SpatialHierarchicalImpl child = new SpatialHierarchicalImpl(parent1, 1f, 1f);
		
		child.setPosition(10f, 15f);
		child.setAngle(-45f);
		
		assertEquals(10f, child.getX(), 0.1f);
		assertEquals(15f, child.getY(), 0.1f);
		assertEquals(-45, child.getAngle(), 0.1f);
		
		child.setParent(parent2);
		
		assertEquals(10f, child.getX(), 0.1f);
		assertEquals(15f, child.getY(), 0.1f);
		assertEquals(-45, child.getAngle(), 0.1f);
	}

}
