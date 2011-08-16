package com.gemserk.commons.artemis.systems;

import com.artemis.Entity;
import com.badlogic.gdx.utils.Array;

public class OrderedByLayerEntities {

	private static final RenderableComponentComparator renderableComponentComparator = new RenderableComponentComparator();
	
	private final Array<Entity> entities = new Array<Entity>();
	private final int minLayer, maxLayer;

	public OrderedByLayerEntities(int minLayer, int maxLayer) {
		this.minLayer = minLayer;
		this.maxLayer = maxLayer;
	}

	public boolean belongs(int layer) {
		return layer >= minLayer && layer < maxLayer;
	}

	public void add(Entity e) {
		entities.add(e);
		entities.sort(renderableComponentComparator);
	}

	public void remove(Entity e) {
		entities.removeValue(e, true);
	}

	public int size() {
		return entities.size;
	}

	public Entity get(int i) {
		return entities.get(i);
	}

}