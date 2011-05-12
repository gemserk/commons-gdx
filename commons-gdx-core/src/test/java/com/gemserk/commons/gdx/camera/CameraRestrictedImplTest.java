package com.gemserk.commons.gdx.camera;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

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
}
