package com.gemserk.commons.gdx.games;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

import com.gemserk.commons.gdx.games.Spatial;
import com.gemserk.commons.gdx.games.SpatialHierarchicalImpl;
import com.gemserk.commons.gdx.games.SpatialImpl;

public class HierarchicalSpatialTest {

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

}
