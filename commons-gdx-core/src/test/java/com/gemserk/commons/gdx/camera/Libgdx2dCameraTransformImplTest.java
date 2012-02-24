package com.gemserk.commons.gdx.camera;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.junit.Ignore;
import org.junit.Test;

import com.badlogic.gdx.math.Vector2;

public class Libgdx2dCameraTransformImplTest {

	@Ignore
	@Test
	public void shouldReturnProjectedVectorWhenCameraNotMoved() {
		Libgdx2dCameraTransformImpl camera = new Libgdx2dCameraTransformImpl();

		camera.move(0, 0);
		camera.zoom(32f);

		Vector2 position = new Vector2(10f, 10f);

		camera.project(position);

		assertThat(position.x, IsEqual.equalTo(320f));
		assertThat(position.y, IsEqual.equalTo(320f));
	}

	@Ignore
	@Test
	public void shouldReturnProjectedVectorWhenCameraMoved() {
		Libgdx2dCameraTransformImpl camera = new Libgdx2dCameraTransformImpl();

		camera.move(100, 0);
		camera.zoom(32f);

		Vector2 position = new Vector2(10f, 10f);

		camera.project(position);

		assertThat(position.x, IsEqual.equalTo(-2880f));
		assertThat(position.y, IsEqual.equalTo(320f));
	}

}
