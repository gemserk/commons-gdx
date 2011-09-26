package com.gemserk.commons.artemis.components;

import com.artemis.Component;
import com.gemserk.commons.gdx.games.Spatial;
import com.gemserk.commons.gdx.games.SpatialImpl;

/**
 * Used to store previous state of the SpatialComponent, to interpolate states when rendering.
 */
public class PreviousStateSpatialComponent extends Component {

	private Spatial spatial;

	public Spatial getSpatial() {
		return spatial;
	}

	public void setSpatial(Spatial spatial) {
		this.spatial = spatial;
	}

	public PreviousStateSpatialComponent() {
		this.spatial = new SpatialImpl(0, 0);
	}

}
