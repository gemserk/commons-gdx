package com.gemserk.commons.artemis.systems;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

import com.badlogic.gdx.math.Vector2;
import com.gemserk.commons.gdx.games.Spatial;
import com.gemserk.commons.gdx.games.SpatialImpl;

public class HierarchicalSpatialTest {

	static class SpatialHierarchicalImpl implements Spatial {

		private static final Vector2 aux = new Vector2();

		private final Spatial parent;

		private final Vector2 localPosition = new Vector2();
		private final Vector2 absolutePosition = new Vector2();

		private float localAngle;

		public SpatialHierarchicalImpl(Spatial parent) {
			this.parent = parent;
			this.localAngle = 0f;
			setPosition(parent.getX(), parent.getY());
			setAngle(parent.getAngle());
		}

		@Override
		public float getX() {
			return getPosition().x;
		}

		@Override
		public float getY() {
			return getPosition().y;
		}

		@Override
		public Vector2 getPosition() {
			aux.set(localPosition);
			aux.rotate(parent.getAngle());
			absolutePosition.set(aux.x + parent.getX(), aux.y + parent.getY());
			return absolutePosition;
		}

		@Override
		public void setPosition(float x, float y) {
			localPosition.set(x - parent.getX(), y - parent.getY());
		}

		@Override
		public float getAngle() {
			return localAngle + parent.getAngle();
		}

		@Override
		public void setAngle(float angle) {
			localAngle = angle - parent.getAngle();
		}

		@Override
		public float getWidth() {
			return 0;
		}

		@Override
		public float getHeight() {
			return 0;
		}

		@Override
		public void setSize(float width, float height) {

		}

		@Override
		public void set(Spatial spatial) {
			setPosition(spatial.getX(), spatial.getY());
			setAngle(spatial.getAngle());
		}

	}

	@Test
	public void shouldReturnParentPosition() {
		float x = 20f;
		float y = 50f;

		Spatial parent = new SpatialImpl(x, y, 1f, 1f, 0f);

		Spatial child = new SpatialHierarchicalImpl(parent);

		assertThat(child.getX(), IsEqual.equalTo(x));
		assertThat(child.getY(), IsEqual.equalTo(y));
	}

	@Test
	public void shouldSetAbsolutePositionWhenSettingChildPosition() {
		Spatial parent = new SpatialImpl(10f, 20f, 1f, 1f, 0f);

		Spatial child = new SpatialHierarchicalImpl(parent);

		child.setPosition(35f, 55f);

		assertThat(child.getX(), IsEqual.equalTo(35f));
		assertThat(child.getY(), IsEqual.equalTo(55f));
		assertThat(child.getPosition().x, IsEqual.equalTo(35f));
		assertThat(child.getPosition().y, IsEqual.equalTo(55f));
	}

	@Test
	public void shouldCalculateOnGet() {
		Spatial parent = new SpatialImpl(10f, 20f, 1f, 1f, 0f);

		Spatial child = new SpatialHierarchicalImpl(parent);

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
		Spatial child = new SpatialHierarchicalImpl(parent);
		assertThat(child.getAngle(), IsEqual.equalTo(55f));
	}

	@Test
	public void shouldSetAngle() {
		Spatial parent = new SpatialImpl(0f, 0f, 1f, 1f, 55f);
		Spatial child = new SpatialHierarchicalImpl(parent);
		child.setAngle(25f);
		assertThat(child.getAngle(), IsEqual.equalTo(25f));
	}

	@Test
	public void shouldRecalculateAngleBasedOnParentAngle() {
		Spatial parent = new SpatialImpl(0f, 0f, 1f, 1f, 55f);
		Spatial child = new SpatialHierarchicalImpl(parent);
		child.setAngle(25f);
		parent.setAngle(75f);
		assertThat(child.getAngle(), IsEqual.equalTo(45f));
	}

	@Test
	public void shouldRecalculatePositionBasedOnParentAngle() {
		Spatial parent = new SpatialImpl(0f, 0f, 1f, 1f, 0f);
		Spatial child = new SpatialHierarchicalImpl(parent);
		child.setPosition(100f, 0f);
		parent.setAngle(90f);
		assertEquals(0f, child.getX(), 0.1f);
		assertEquals(100f, child.getY(), 0.1f);
	}

}
