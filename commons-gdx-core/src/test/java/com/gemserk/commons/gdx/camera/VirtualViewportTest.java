package com.gemserk.commons.gdx.camera;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

import com.gemserk.commons.gdx.camera.VirtualViewport;

public class VirtualViewportTest {

	@Test
	public void shouldReturnSameSizeIfSameViewport() {
		VirtualViewport virtualViewport = new VirtualViewport(800, 600);
		assertThat(virtualViewport.getWidth(800f, 600f), IsEqual.equalTo(800f));
		assertThat(virtualViewport.getHeight(800f, 600f), IsEqual.equalTo(600f));
	}

	@Test
	public void shouldReturnDoubleForMiddleSizeButSameAspect() {
		VirtualViewport virtualViewport = new VirtualViewport(800, 600);
		assertThat(virtualViewport.getWidth(400f, 300f), IsEqual.equalTo(800f));
		assertThat(virtualViewport.getHeight(400f, 300f), IsEqual.equalTo(600f));
	}

	@Test
	public void shouldReturnModifiedHeightToLetSameVirtualViewport() {
		VirtualViewport virtualViewport = new VirtualViewport(800, 600);
		assertThat(virtualViewport.getWidth(800f, 480f), IsEqual.equalTo(1000f));
		assertThat(virtualViewport.getHeight(800f, 480f), IsEqual.equalTo(600f));
	}
	
	@Test
	public void test1() {
		VirtualViewport virtualViewport = new VirtualViewport(800f, 600f);
		assertThat(virtualViewport.getWidth(400f, 600f), IsEqual.equalTo(800f));
		assertThat(virtualViewport.getHeight(400f, 600f), IsEqual.equalTo(1200f));
	}

	@Test
	public void testShrinkWithNormalViewport() {
		VirtualViewport virtualViewport = new VirtualViewport(800, 600, true);
		assertThat(virtualViewport.getWidth(800f, 600f), IsEqual.equalTo(800f));
		assertThat(virtualViewport.getHeight(800f, 600f), IsEqual.equalTo(600f));
	}

	@Test
	public void testAdaptToInverted() {
		VirtualViewport virtualViewport = new VirtualViewport(800, 600, true);
		assertThat(virtualViewport.getWidth(800f, 480f), IsEqual.equalTo(800f));
		assertThat(virtualViewport.getHeight(800f, 480f), IsEqual.equalTo(480f));
	}
	
}
