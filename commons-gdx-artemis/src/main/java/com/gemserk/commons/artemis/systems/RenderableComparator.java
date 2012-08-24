package com.gemserk.commons.artemis.systems;

import java.util.Comparator;

public class RenderableComparator implements Comparator<Renderable> {

	@Override
	public int compare(Renderable o1, Renderable o2) {
		if (o1.layer != o2.layer)
			return o1.layer - o2.layer;

		int id1 = o1.getId();
		int id2 = o2.getId();

		if (id1 != id2)
			return id1 - id2;

		return o1.subLayer - o2.subLayer;
	}

}