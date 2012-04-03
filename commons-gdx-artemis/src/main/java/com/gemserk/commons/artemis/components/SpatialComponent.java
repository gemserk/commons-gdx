package com.gemserk.commons.artemis.components;

import com.artemis.Component;
import com.artemis.ComponentType;
import com.artemis.ComponentTypeManager;
import com.artemis.Entity;
import com.badlogic.gdx.math.Vector2;
import com.gemserk.commons.gdx.games.Spatial;

public class SpatialComponent extends Component {
	
	public static final ComponentType type = ComponentTypeManager.getTypeFor(SpatialComponent.class);

	public static SpatialComponent get(Entity e) {
		return (SpatialComponent) (e.getComponent(type));
	}

	private Spatial spatial;

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
	}

}
