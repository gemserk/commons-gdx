package com.gemserk.commons.artemis.systems;

import com.badlogic.gdx.utils.Array;

public class OrderedByLayerRenderables {

	final RenderableComparator renderableComparator = new RenderableComparator();

	private final Array<Renderable> entities = new Array<Renderable>();
	private final int minLayer, maxLayer;

	public OrderedByLayerRenderables(int minLayer, int maxLayer) {
		this.minLayer = minLayer;
		this.maxLayer = maxLayer;
	}

	public boolean belongs(Renderable renderable) {
		int layer = renderable.getLayer();
		return layer >= minLayer && layer < maxLayer;
	}

	public void add(Renderable e) {
		entities.add(e);
		entities.sort(renderableComparator);
	}

	public void remove(Renderable e) {
		entities.removeValue(e, true);
	}

	public int size() {
		return entities.size;
	}

	public Renderable get(int i) {
		return entities.get(i);
	}

}