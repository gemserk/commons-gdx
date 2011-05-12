package com.gemserk.commons.artemis.components;

import com.artemis.Component;

public class SpatialComponent extends Component {

	private Spatial spatial;

	public Spatial getSpatial() {
		return spatial;
	}

	// /**
	// * Only if you want to modify internal implementation of spatial
	// */
	// public void setSpatial(Spatial spatial) {
	// this.spatial = spatial;
	// }

	public SpatialComponent(Spatial spatial) {
		this.spatial = spatial;
	}

}
