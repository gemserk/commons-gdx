package com.gemserk.commons.artemis.systems;

import java.util.Comparator;

public class RenderableComparator implements Comparator<Renderable> {

	@Override
	public int compare(Renderable o1, Renderable o2) {
		int layer1 = o1.layer;
		int layer2 = o2.layer;
		
		if (layer1 != layer2)
			return layer1 - layer2;

		int id1 = o1.id;
		int id2 = o2.id;

		if (id1 != id2)
			return id1 - id2;

		return o1.subLayer - o2.subLayer;
	}

}