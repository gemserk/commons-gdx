package com.gemserk.commons.artemis.render;

import com.gemserk.commons.artemis.systems.RenderLayer;
import com.gemserk.componentsengine.utils.RandomAccessMap;

/**
 * Provides an easy way to access to render layers to disable/enable them.
 * 
 * @author acoppes
 * 
 */
public class RenderLayers {

	RandomAccessMap<String, RenderLayer> renderLayers;

	public RenderLayers() {
		renderLayers = new RandomAccessMap<String, RenderLayer>();
	}

	public void add(String layerName, RenderLayer layer) {
		this.add(layerName, layer, true);
	}
	
	public void add(String layerName, RenderLayer layer, boolean enabled) {
		renderLayers.put(layerName, layer);
		layer.setEnabled(enabled);
	}

	public RenderLayer get(String layerName) {
		return renderLayers.get(layerName);
	}

	public void disable(String layerName) {
		RenderLayer layer = get(layerName);
		if (layer == null)
			return;
		layer.setEnabled(false);
	}

	public void enable(String layerName) {
		RenderLayer layer = get(layerName);
		if (layer == null)
			return;
		layer.setEnabled(true);
	}
	
	public void toggle(String layerName) {
		RenderLayer layer = get(layerName);
		if (layer == null)
			return;
		layer.setEnabled(!layer.isEnabled());
	}

	public int size() {
		return renderLayers.size();
	}

	public RenderLayer get(int layer) {
		if (layer >= renderLayers.size())
			return null;
		return renderLayers.get(layer);
	}

}