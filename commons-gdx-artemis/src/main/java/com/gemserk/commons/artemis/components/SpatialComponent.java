package com.gemserk.commons.artemis.components;

import com.artemis.Component;
import com.badlogic.gdx.math.Vector2;
import com.gemserk.commons.gdx.games.Spatial;
import com.gemserk.commons.gdx.games.SpatialImpl;

public class SpatialComponent extends Component {

	private Spatial spatial;
	private Spatial previousSpatial;
	
	public Spatial getPreviousSpatial() {
		return previousSpatial;
	}

	public Spatial getSpatial() {
		return spatial;
	}
	
	public void setSpatial(Spatial spatial) {
		this.spatial = spatial;
	}

	public Vector2 getPosition() {
		return spatial.getPosition();
	}
	
	public void setPosition(Vector2 position) {
		spatial.setPosition(position.x, position.y);
	}
	
	public SpatialComponent(Spatial spatial) {
		this.spatial = spatial;
		this.previousSpatial = new SpatialImpl(0f, 0f);
	}

}
