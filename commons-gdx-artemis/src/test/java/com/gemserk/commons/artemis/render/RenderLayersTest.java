package com.gemserk.commons.artemis.render;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNull;
import org.hamcrest.core.IsSame;
import org.junit.Test;

import com.gemserk.commons.artemis.systems.RenderLayer;
import com.gemserk.componentsengine.utils.RandomAccessMap;

public class RenderLayersTest {

	static class RenderLayers {

		RandomAccessMap<String, RenderLayer> renderLayers;
		RandomAccessMap<String, RenderLayer> disabledLayers;

		public RenderLayers() {
			renderLayers = new RandomAccessMap<String, RenderLayer>();
			disabledLayers = new RandomAccessMap<String, RenderLayer>();
		}

		void add(String layerName, RenderLayer layer) {
			renderLayers.put(layerName, layer);
		}

		void disable(String layerName) {
			RenderLayer layer = renderLayers.remove(layerName);
			if (layer == null)
				return;
			disabledLayers.put(layerName, layer);
		}

		void enable(String layerName) {
			RenderLayer layer = disabledLayers.remove(layerName);
			if (layer == null)
				return;
			renderLayers.put(layerName, layer);
		}

		int enabledLayerCount() {
			return renderLayers.size();
		}

		RenderLayer getEnabledLayer(int layer) {
			if (layer >= renderLayers.size())
				return null;
			return renderLayers.get(layer);
		}

	}

	@Test
	public void shouldStartWithNoLayers() {
		RenderLayers renderLayers = new RenderLayers();
		assertThat(renderLayers.enabledLayerCount(), IsEqual.equalTo(0));
	}

	@Test
	public void shouldIncrementLayerCount() {
		RenderLayers renderLayers = new RenderLayers();
		renderLayers.add("LAYER", new MockRenderLayer());
		assertThat(renderLayers.enabledLayerCount(), IsEqual.equalTo(1));
	}

	@Test
	public void shouldReturnAddedLayer() {
		RenderLayers renderLayers = new RenderLayers();
		RenderLayer renderLayer = new MockRenderLayer();
		renderLayers.add("LAYER", renderLayer);
		assertThat(renderLayers.getEnabledLayer(0), IsNull.notNullValue());
		assertThat(renderLayers.getEnabledLayer(0), IsSame.sameInstance(renderLayer));
	}

	@Test
	public void shouldNotReturnAddedLayerAndDisabled() {
		RenderLayers renderLayers = new RenderLayers();
		RenderLayer renderLayer = new MockRenderLayer();
		renderLayers.add("LAYER", renderLayer);
		renderLayers.disable("LAYER");
		assertThat(renderLayers.enabledLayerCount(), IsEqual.equalTo(0));
		assertThat(renderLayers.getEnabledLayer(0), IsNull.nullValue());
	}

	@Test
	public void shouldReturnAddedLayerDisabledAndEnabled() {
		RenderLayers renderLayers = new RenderLayers();
		RenderLayer renderLayer = new MockRenderLayer();
		renderLayers.add("LAYER", renderLayer);
		renderLayers.disable("LAYER");
		renderLayers.enable("LAYER");
		assertThat(renderLayers.enabledLayerCount(), IsEqual.equalTo(1));
		assertThat(renderLayers.getEnabledLayer(0), IsNull.notNullValue());
		assertThat(renderLayers.getEnabledLayer(0), IsSame.sameInstance(renderLayer));
	}

	@Test
	public void shouldReturnAddedLayerAndDisabledOther() {
		RenderLayers renderLayers = new RenderLayers();
		RenderLayer renderLayer = new MockRenderLayer();
		renderLayers.add("LAYER", renderLayer);
		renderLayers.disable("LAYER2");
		assertThat(renderLayers.enabledLayerCount(), IsEqual.equalTo(1));
		assertThat(renderLayers.getEnabledLayer(0), IsNull.notNullValue());
		assertThat(renderLayers.getEnabledLayer(0), IsSame.sameInstance(renderLayer));
	}

}
