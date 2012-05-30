package com.gemserk.commons.gdx.camera;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

import com.gemserk.commons.gdx.camera.VirtualViewport;

public class MultipleVirtualViewportBuilderTest {

	@Test
	public void shouldReturnVirtualViewportForMinSize() {
		MultipleVirtualViewportBuilder multipleVirtualViewportBuilder = new MultipleVirtualViewportBuilder(800f, 480f, 854f, 600f);
		VirtualViewport virtualViewport = multipleVirtualViewportBuilder.getVirtualViewport(800f, 480f);
		assertNotNull(virtualViewport);
		assertThat(virtualViewport.getVirtualWidth(), IsEqual.equalTo(800f));
		assertThat(virtualViewport.getVirtualHeight(), IsEqual.equalTo(480f));
	}

	@Test
	public void shouldReturnVirtualViewportForMaxSize() {
		MultipleVirtualViewportBuilder multipleVirtualViewportBuilder = new MultipleVirtualViewportBuilder(800f, 480f, 854f, 600f);
		VirtualViewport virtualViewport = multipleVirtualViewportBuilder.getVirtualViewport(854f, 600f);
		assertNotNull(virtualViewport);
		assertThat(virtualViewport.getVirtualWidth(), IsEqual.equalTo(854f));
		assertThat(virtualViewport.getVirtualHeight(), IsEqual.equalTo(600f));
	}
	
	@Test
	public void shouldReturnVirtualViewportForNormalSize() {
		MultipleVirtualViewportBuilder multipleVirtualViewportBuilder = new MultipleVirtualViewportBuilder(800f, 480f, 854f, 600f);
		VirtualViewport virtualViewport = multipleVirtualViewportBuilder.getVirtualViewport(854f, 480f);
		assertNotNull(virtualViewport);
		assertThat(virtualViewport.getVirtualWidth(), IsEqual.equalTo(854f));
		assertThat(virtualViewport.getVirtualHeight(), IsEqual.equalTo(480f));
	}

	@Test
	public void shouldReturnVirtualViewportViewportWithHalfSizeTheMinSize() {
		MultipleVirtualViewportBuilder multipleVirtualViewportBuilder = new MultipleVirtualViewportBuilder(800f, 480f, 854f, 600f);
		VirtualViewport virtualViewport = multipleVirtualViewportBuilder.getVirtualViewport(400f, 240f);
		assertNotNull(virtualViewport);
		assertThat(virtualViewport.getVirtualWidth(), IsEqual.equalTo(854f));
		assertThat(virtualViewport.getVirtualHeight(), IsEqual.equalTo(512.4f));
	}
	
	@Test
	public void shouldReturnVirtualViewportViewportWithDoubleSizeTheMinSize() {
		MultipleVirtualViewportBuilder multipleVirtualViewportBuilder = new MultipleVirtualViewportBuilder(800f, 480f, 854f, 600f);
		VirtualViewport virtualViewport = multipleVirtualViewportBuilder.getVirtualViewport(800f * 2f, 480f * 2);
		assertNotNull(virtualViewport);
		assertThat(virtualViewport.getVirtualWidth(), IsEqual.equalTo(854f));
		assertThat(virtualViewport.getVirtualHeight(), IsEqual.equalTo(512.4f));
	}

	@Test
	public void shouldReturnVirtualViewportViewportWithHalfSizeTheMaxSize() {
		MultipleVirtualViewportBuilder multipleVirtualViewportBuilder = new MultipleVirtualViewportBuilder(800f, 480f, 854f, 600f);
		VirtualViewport virtualViewport = multipleVirtualViewportBuilder.getVirtualViewport(427f, 300f);
		assertNotNull(virtualViewport);
		assertThat(virtualViewport.getVirtualWidth(), IsEqual.equalTo(854f));
		assertThat(virtualViewport.getVirtualHeight(), IsEqual.equalTo(600f));
	}

	@Test
	public void shouldReturnVirtualViewportViewportWithDoubleSizeTheMaxSize() {
		MultipleVirtualViewportBuilder multipleVirtualViewportBuilder = new MultipleVirtualViewportBuilder(800f, 480f, 854f, 600f);
		VirtualViewport virtualViewport = multipleVirtualViewportBuilder.getVirtualViewport(854f * 2f, 600f * 2);
		assertNotNull(virtualViewport);
		assertThat(virtualViewport.getVirtualWidth(), IsEqual.equalTo(854f));
		assertThat(virtualViewport.getVirtualHeight(), IsEqual.equalTo(600f));
	}
}
