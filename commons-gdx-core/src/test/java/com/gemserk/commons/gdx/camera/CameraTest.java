package com.gemserk.commons.gdx.camera;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

import com.badlogic.gdx.math.Rectangle;

public class CameraTest {

	private class CameraImpl implements Camera {

		private Rectangle worldBounds;

		private float x;

		private float y;

		private float width;

		private float height;

		private float zoom = 1f;

		private float angle = 0f;

		public void setWorldBounds(float x1, float y1, float x2, float y2) {
			this.worldBounds = new Rectangle(x1, y1, x2 - x1, y2 - y1);
			reposition();
		}

		public void setBounds(float width, float height) {
			this.width = width;
			this.height = height;
			reposition();
		}

		@Override
		public void setPosition(float x, float y) {
			this.x = x;
			this.y = y;
			reposition();
		}

		private void reposition() {
			if (worldBounds == null)
				return;
			
			if (x + getRealWidth() * 0.5f > worldBounds.getX() + worldBounds.getWidth()) {
				x = worldBounds.getX() + worldBounds.getWidth() - getRealWidth() * 0.5f;
			}

			if (x - getRealWidth() * 0.5f < worldBounds.getX()) {
				x = worldBounds.getX() + getRealWidth() * 0.5f;
			}

			if (y + getRealHeight() * 0.5f > worldBounds.getY() + worldBounds.getHeight()) {
				y = worldBounds.getY() + worldBounds.getHeight() - getRealHeight() * 0.5f;
			}

			if (y - getRealHeight() * 0.5f < worldBounds.getY()) {
				y = worldBounds.getY() + getRealHeight() * 0.5f;
			}
		}

		public float getRealHeight() {
			return height / getZoom();
		}

		public float getRealWidth() {
			return width / getZoom();
		}

		@Override
		public float getX() {
			return x;
		}

		@Override
		public float getY() {
			return y;
		}

		@Override
		public void setZoom(float zoom) {
			this.zoom = zoom;
			reposition();
		}

		@Override
		public float getZoom() {
			return zoom;
		}

		@Override
		public void setAngle(float angle) {
			this.angle = angle;
		}

		@Override
		public float getAngle() {
			return angle;
		}

	}

	@Test
	public void shouldBeInPositionWhenInsideBounds() {
		CameraImpl camera = new CameraImpl();
		camera.setBounds(10, 10);
		camera.setWorldBounds(-50, -50, 50, 50);
		camera.setPosition(10f, 10f);
		assertThat(camera.getX(), IsEqual.equalTo(10f));
		assertThat(camera.getY(), IsEqual.equalTo(10f));
	}

	@Test
	public void shouldBeLimitedByWorldBounds() {
		CameraImpl camera = new CameraImpl();
		camera.setBounds(10, 20);
		camera.setWorldBounds(-50, -50, 50, 50);
		camera.setPosition(49f, 0f);
		assertThat(camera.getX(), IsEqual.equalTo(45f));
		camera.setPosition(-49f, 0f);
		assertThat(camera.getX(), IsEqual.equalTo(-45f));
		camera.setPosition(0f, 49f);
		assertThat(camera.getY(), IsEqual.equalTo(40f));
		camera.setPosition(0f, -49f);
		assertThat(camera.getY(), IsEqual.equalTo(-40f));
	}

	@Test
	public void shouldRepositionIfBoundsModified() {
		CameraImpl camera = new CameraImpl();
		camera.setBounds(10, 10);
		camera.setWorldBounds(-50, -50, 50, 50);
		camera.setPosition(50f, 0f);
		assertThat(camera.getX(), IsEqual.equalTo(45f));
		camera.setBounds(20, 10);
		assertThat(camera.getX(), IsEqual.equalTo(40f));
	}

	@Test
	public void shouldRepositionBasedOnCurrentZoom() {
		CameraImpl camera = new CameraImpl();
		camera.setBounds(10, 10);
		camera.setWorldBounds(-50, -50, 50, 50);
		camera.setZoom(2f);
		camera.setPosition(50f, 0f);
		assertThat(camera.getX(), IsEqual.equalTo(47.5f));
		camera.setZoom(0.5f);
		assertThat(camera.getX(), IsEqual.equalTo(40f));
	}

	@Test
	public void shouldNoRepositionIfWorldBoundsNotSet() {
		CameraImpl camera = new CameraImpl();
		camera.setBounds(10, 10);
		camera.setZoom(2f);
		camera.setPosition(150f, 270f);
		assertThat(camera.getX(), IsEqual.equalTo(150f));
		assertThat(camera.getY(), IsEqual.equalTo(270f));
	}
}
