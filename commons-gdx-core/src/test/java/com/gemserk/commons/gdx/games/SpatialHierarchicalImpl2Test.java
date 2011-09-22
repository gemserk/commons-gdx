package com.gemserk.commons.gdx.games;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

public class SpatialHierarchicalImpl2Test {

	@Test
	public void shouldReturnParentPosition() {
		float x = 20f;
		float y = 50f;

		Spatial parent = new SpatialImpl(x, y, 1f, 1f, 0f);
		Spatial child = new SpatialImpl(0f, 0f, 1f, 1f, 0f);

		Spatial hierarchicalSpatial = new SpatialHierarchicalImpl2(parent, child);

		assertThat(hierarchicalSpatial.getX(), IsEqual.equalTo(0f));
		assertThat(hierarchicalSpatial.getY(), IsEqual.equalTo(0f));
		
		assertThat(child.getX(), IsEqual.equalTo(-20f));
		assertThat(child.getY(), IsEqual.equalTo(-50f));
	}

	@Test
	public void shouldSetAbsolutePositionWhenSettingChildPosition() {

		Spatial parent = new SpatialImpl(10f, 20f, 1f, 1f, 0f);
		Spatial child = new SpatialImpl(0f, 0f, 1f, 1f, 0f);

		Spatial hierarchicalSpatial = new SpatialHierarchicalImpl2(parent, child);

		hierarchicalSpatial.setPosition(35f, 55f);

		assertThat(hierarchicalSpatial.getX(), IsEqual.equalTo(35f));
		assertThat(hierarchicalSpatial.getY(), IsEqual.equalTo(55f));
		assertThat(hierarchicalSpatial.getPosition().x, IsEqual.equalTo(35f));
		assertThat(hierarchicalSpatial.getPosition().y, IsEqual.equalTo(55f));
	}

	@Test
	public void shouldCalculateOnGet() {
		Spatial parent = new SpatialImpl(10f, 20f, 1f, 1f, 0f);
		Spatial child = new SpatialImpl(0f, 0f, 1f, 1f, 0f);

		Spatial hierarchicalSpatial = new SpatialHierarchicalImpl2(parent, child);

		hierarchicalSpatial.setPosition(35f, 55f);
		parent.setPosition(0f, 0f);

		assertThat(hierarchicalSpatial.getX(), IsEqual.equalTo(25f));
		assertThat(hierarchicalSpatial.getY(), IsEqual.equalTo(35f));
		assertThat(hierarchicalSpatial.getPosition().x, IsEqual.equalTo(25f));
		assertThat(hierarchicalSpatial.getPosition().y, IsEqual.equalTo(35f));
	}

	@Test
	public void shouldReturnParentAngle() {
		Spatial parent = new SpatialImpl(0f, 0f, 1f, 1f, 55f);
		Spatial child = new SpatialImpl(0f, 0f, 1f, 1f, 0f);

		Spatial hierarchicalSpatial = new SpatialHierarchicalImpl2(parent, child);

		assertThat(hierarchicalSpatial.getAngle(), IsEqual.equalTo(0f));
		
		assertThat(child.getAngle(), IsEqual.equalTo(-55f));
	}

	@Test
	public void shouldSetAngle() {
		Spatial parent = new SpatialImpl(0f, 0f, 1f, 1f, 55f);
		Spatial child = new SpatialImpl(0f, 0f, 1f, 1f, 0f);

		Spatial hierarchicalSpatial = new SpatialHierarchicalImpl2(parent, child);

		hierarchicalSpatial.setAngle(25f);
		assertThat(hierarchicalSpatial.getAngle(), IsEqual.equalTo(25f));
	}

	@Test
	public void shouldRecalculateAngleBasedOnParentAngle() {
		Spatial parent = new SpatialImpl(0f, 0f, 1f, 1f, 55f);
		Spatial child = new SpatialImpl(0f, 0f, 1f, 1f, 0f);

		Spatial hierarchicalSpatial = new SpatialHierarchicalImpl2(parent, child);

		hierarchicalSpatial.setAngle(25f);
		parent.setAngle(75f);
		assertThat(hierarchicalSpatial.getAngle(), IsEqual.equalTo(45f));
	}

	@Test
	public void shouldRecalculatePositionBasedOnParentAngle() {
		Spatial parent = new SpatialImpl(0f, 0f, 1f, 1f, 0f);
		Spatial child = new SpatialImpl(0f, 0f, 1f, 1f, 0f);

		Spatial hierarchicalSpatial = new SpatialHierarchicalImpl2(parent, child);

		hierarchicalSpatial.setPosition(100f, 0f);
		parent.setAngle(90f);

		assertEquals(0f, hierarchicalSpatial.getX(), 0.1f);
		assertEquals(100f, hierarchicalSpatial.getY(), 0.1f);
	}

	@Test
	public void shouldReturnCorrectAngleForMultipleChilds() {
		Spatial parent = new SpatialImpl(0f, 0f, 1f, 1f, 0f);
		Spatial child = new SpatialImpl(0f, 0f, 1f, 1f, 0f);
		Spatial grandChild = new SpatialImpl(0f, 0f, 1f, 1f, 0f);

		Spatial hierarchicalSpatial = new SpatialHierarchicalImpl2(parent, child);
		Spatial hierarchicalSpatial2 = new SpatialHierarchicalImpl2(hierarchicalSpatial, grandChild);

		hierarchicalSpatial.setPosition(10f, 0f);
		hierarchicalSpatial2.setPosition(20f, 0f);

		parent.setAngle(90f);

		assertEquals(90f, hierarchicalSpatial.getAngle(), 0.1f);
		assertEquals(90f, hierarchicalSpatial2.getAngle(), 0.1f);

		hierarchicalSpatial.setAngle(120f);

		assertEquals(120f, hierarchicalSpatial2.getAngle(), 0.1f);
	}

	@Test
	public void shouldReturnCorrectPositionForMultipleChilds() {
		Spatial parent = new SpatialImpl(0f, 0f, 1f, 1f, 0f);
		Spatial child = new SpatialImpl(0f, 0f, 1f, 1f, 0f);
		Spatial grandChild = new SpatialImpl(0f, 0f, 1f, 1f, 0f);

		Spatial hierarchicalSpatial = new SpatialHierarchicalImpl2(parent, child);
		Spatial hierarchicalSpatial2 = new SpatialHierarchicalImpl2(hierarchicalSpatial, grandChild);

		hierarchicalSpatial.setPosition(20f, 0f);
		hierarchicalSpatial2.setPosition(30f, 0f);

		assertEquals(30f, hierarchicalSpatial2.getX(), 0.1f);
		assertEquals(0f, hierarchicalSpatial2.getY(), 0.1f);

		hierarchicalSpatial.setAngle(0f);
		parent.setAngle(90f);

		assertEquals(90f, hierarchicalSpatial.getAngle(), 0.1f);
		assertEquals(0f, hierarchicalSpatial.getX(), 0.1f);
		assertEquals(20f, hierarchicalSpatial.getY(), 0.1f);

		assertEquals(90f, hierarchicalSpatial2.getAngle(), 0.1f);
		assertEquals(0f, hierarchicalSpatial2.getX(), 0.1f);
		assertEquals(30f, hierarchicalSpatial2.getY(), 0.1f);
	}
	
	@Test
	public void setAsChildComponentShouldConvertCoordinates() {
		Spatial parent = new SpatialImpl(50f, 50f, 1f, 1f, 0f);
		Spatial child = new SpatialImpl(25f, 25f, 1f, 1f, 0f);

		Spatial hierarchicalSpatial = new SpatialHierarchicalImpl2(parent, child);
		
		assertEquals(25f, hierarchicalSpatial.getX(), 0.1f);
		assertEquals(25f, hierarchicalSpatial.getY(), 0.1f);
		
		assertEquals(-25f, child.getX(), 0.1f);
		assertEquals(-25f, child.getY(), 0.1f);
	}

}
