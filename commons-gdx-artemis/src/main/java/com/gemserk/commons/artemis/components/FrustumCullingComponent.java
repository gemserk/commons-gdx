package com.gemserk.commons.artemis.components;

import com.artemis.Component;
import com.artemis.ComponentTypeManager;
import com.artemis.Entity;
import com.badlogic.gdx.math.Rectangle;

public class FrustumCullingComponent extends Component {
	
	public static final int type = ComponentTypeManager.getTypeFor(FrustumCullingComponent.class).getId();

	public static FrustumCullingComponent get(Entity e) {
		return (FrustumCullingComponent) e.getComponent(type);
	}

	public Rectangle bounds;

	public FrustumCullingComponent(Rectangle bounds) {
		this.bounds = bounds;
	}

}
