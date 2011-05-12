package com.gemserk.commons.artemis.components;

import com.artemis.Component;
import com.badlogic.gdx.math.Vector2;

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

	public Vector2 getPosition() {
		return spatial.getPosition();
	}
	
	public void setPosition(Vector2 position) {
		spatial.setPosition(position.x, position.y);
	}
	
	public SpatialComponent(Spatial spatial) {
		this.spatial = spatial;
	}

}
