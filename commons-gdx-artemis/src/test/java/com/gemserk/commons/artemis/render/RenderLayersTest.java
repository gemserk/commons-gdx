package com.gemserk.commons.artemis.render;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNull;
import org.hamcrest.core.IsSame;
import org.junit.Test;

import com.gemserk.commons.artemis.systems.RenderLayer;

public class RenderLayersTest {

	@Test
	public void shouldStartWithNoLayers() {
		RenderLayers renderLayers = new RenderLayers();
		assertThat(renderLayers.size(), IsEqual.equalTo(0));
	}

	@Test
	public void shouldIncrementLayerCount() {
		RenderLayers renderLayers = new RenderLayers();
		renderLayers.add("LAYER", new MockRenderLayer());
		assertThat(renderLayers.size(), IsEqual.equalTo(1));
	}

	@Test
	public void shouldReturnAddedLayer() {
		RenderLayers renderLayers = new RenderLayers();
		RenderLayer renderLayer = new MockRenderLayer();
		renderLayers.add("LAYER", renderLayer);
		assertThat(renderLayers.get(0), IsNull.notNullValue());
		assertThat(renderLayers.get(0), IsSame.sameInstance(renderLayer));
	}

	@Test
	public void shouldReturnAddedLayerAndDisabled() {
		RenderLayers renderLayers = new RenderLayers();
		RenderLayer renderLayer = new MockRenderLayer();
		renderLayers.add("LAYER", renderLayer);
		renderLayers.disable("LAYER");
		assertThat(renderLayers.size(), IsEqual.equalTo(1));
		assertThat(renderLayers.get(0), IsNull.notNullValue());
		assertThat(renderLayers.get(0), IsSame.sameInstance(renderLayer));
	}

	@Test
	public void shouldReturnAddedLayerDisabledAndEnabled() {
		RenderLayers renderLayers = new RenderLayers();
		RenderLayer renderLayer = new MockRenderLayer();
		renderLayers.add("LAYER", renderLayer);
		renderLayers.disable("LAYER");
		renderLayers.enable("LAYER");
		assertThat(renderLayers.size(), IsEqual.equalTo(1));
		assertThat(renderLayers.get(0), IsNull.notNullValue());
		assertThat(renderLayers.get(0), IsSame.sameInstance(renderLayer));
	}

}
