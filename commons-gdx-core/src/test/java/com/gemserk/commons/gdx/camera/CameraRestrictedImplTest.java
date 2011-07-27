package com.gemserk.commons.gdx.camera;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

import com.badlogic.gdx.math.Rectangle;

public class CameraRestrictedImplTest {

	@Test
	public void shouldBeInPositionWhenInsideBounds() {
		CameraRestrictedImpl camera = new CameraRestrictedImpl();
		camera.setBounds(10, 10);
		camera.setWorldBounds(-50, -50, 50, 50);
		camera.setPosition(10f, 10f);
		assertThat(camera.getX(), IsEqual.equalTo(10f));
		assertThat(camera.getY(), IsEqual.equalTo(10f));
	}

	@Test
	public void shouldBeLimitedByWorldBounds() {
		CameraRestrictedImpl camera = new CameraRestrictedImpl();
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
	public void testSetPosition() {
		CameraRestrictedImpl camera = new CameraRestrictedImpl();
		camera.setBounds(8, 4);
		camera.setWorldBounds(0, 0, 20, 10);
		camera.setPosition(0f, 0f);
		assertThat(camera.getX(), IsEqual.equalTo(4f));
		assertThat(camera.getY(), IsEqual.equalTo(2f));
		camera.setPosition(20f, 0f);
		assertThat(camera.getX(), IsEqual.equalTo(16f));
		assertThat(camera.getY(), IsEqual.equalTo(2f));
		camera.setPosition(0f, 10f);
		assertThat(camera.getX(), IsEqual.equalTo(4f));
		assertThat(camera.getY(), IsEqual.equalTo(8f));
		camera.setPosition(20f, 10f);
		assertThat(camera.getX(), IsEqual.equalTo(16f));
		assertThat(camera.getY(), IsEqual.equalTo(8f));
	}

	@Test
	public void testSetPositionWithZoom() {
		CameraRestrictedImpl camera = new CameraRestrictedImpl();
		camera.setBounds(8, 4);
		camera.setWorldBounds(0, 0, 20, 10);
		camera.setZoom(10f);
		camera.setPosition(0f, 0f);
		assertThat(camera.getX(), IsEqual.equalTo(0.4f));
		assertThat(camera.getY(), IsEqual.equalTo(0.2f));
		camera.setPosition(20f, 0f);
		assertThat(camera.getX(), IsEqual.equalTo(19.6f));
		assertThat(camera.getY(), IsEqual.equalTo(0.2f));
		camera.setPosition(0f, 10f);
		assertThat(camera.getX(), IsEqual.equalTo(0.4f));
		assertThat(camera.getY(), IsEqual.equalTo(9.8f));
		camera.setPosition(20f, 10f);
		assertThat(camera.getX(), IsEqual.equalTo(19.6f));
		assertThat(camera.getY(), IsEqual.equalTo(9.8f));
		camera.setZoom(1f);
		assertThat(camera.getX(), IsEqual.equalTo(16f));
		assertThat(camera.getY(), IsEqual.equalTo(8f));
	}

	@Test
	public void shouldRepositionIfBoundsModified() {
		CameraRestrictedImpl camera = new CameraRestrictedImpl();
		camera.setBounds(10, 10);
		camera.setWorldBounds(-50, -50, 50, 50);
		camera.setPosition(50f, 0f);
		assertThat(camera.getX(), IsEqual.equalTo(45f));
		camera.setBounds(20, 10);
		assertThat(camera.getX(), IsEqual.equalTo(40f));
	}

	@Test
	public void shouldRepositionBasedOnCurrentZoom() {
		CameraRestrictedImpl camera = new CameraRestrictedImpl();
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
		CameraRestrictedImpl camera = new CameraRestrictedImpl();
		camera.setBounds(10, 10);
		camera.setZoom(2f);
		camera.setPosition(150f, 270f);
		assertThat(camera.getX(), IsEqual.equalTo(150f));
		assertThat(camera.getY(), IsEqual.equalTo(270f));
	}

	@Test
	public void shouldNotZoomOutIfInternalBoundsAreGreaterThanWorldBounds() {
		CameraRestrictedImpl camera = new CameraRestrictedImpl();
		camera.setBounds(10, 10);
		camera.setWorldBounds(-50f, -50f, 50f, 50f);
		camera.setZoom(0.1f);
		assertThat(camera.getZoom(), IsEqual.equalTo(0.1f));
		camera.setZoom(0.01f);
		assertThat(camera.getZoom(), IsEqual.equalTo(0.1f));
	}

	@Test
	public void shouldRecalculateZoomIfBoundsChanged() {
		CameraRestrictedImpl camera = new CameraRestrictedImpl();
		camera.setBounds(10, 10);
		camera.setWorldBounds(-50f, -50f, 50f, 50f);
		camera.setZoom(0.1f);
		assertThat(camera.getZoom(), IsEqual.equalTo(0.1f));
		camera.setBounds(20, 20);
		assertThat(camera.getZoom(), IsEqual.equalTo(0.2f));
	}

	@Test
	public void shouldRecalculateZoomIfBoundsChanged2() {
		CameraRestrictedImpl camera = new CameraRestrictedImpl();
		camera.setBounds(20, 12);
		camera.setWorldBounds(10f, 6f, 30f, 18f);
		camera.setZoom(1f);
		assertThat(camera.getZoom(), IsEqual.equalTo(1f));
		camera.setZoom(0.5f);
		assertThat(camera.getZoom(), IsEqual.equalTo(1f));
	}

	@Test
	public void shouldRecalculateZoomIfBoundsChanged3() {
		CameraRestrictedImpl camera = new CameraRestrictedImpl(0f, 0f, 40f, 0f, 800f, 480f, new Rectangle(0f, 0f, 20f, 12f));
		assertThat(camera.getZoom(), IsEqual.equalTo(40f));
		camera.setZoom(1f);
		assertThat(camera.getZoom(), IsEqual.equalTo(40f));
	}

	@Test
	public void bugWhenRecalculatingZoomShouldUseSmallerDimension() {
		CameraRestrictedImpl camera = new CameraRestrictedImpl(0f, 0f, 1f, 0f, 800f, 480f, new Rectangle(0f, 0f, 12f, 4f));
		assertThat(camera.getZoom(), IsEqual.equalTo(120f));
		camera = new CameraRestrictedImpl(0f, 0f, 1f, 0f, 800f, 480f, new Rectangle(0f, 0f, 4f, 12f));
		assertThat(camera.getZoom(), IsEqual.equalTo(200f));
	}
}
