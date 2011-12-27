package com.gemserk.commons.artemis.components;

import com.artemis.Component;
import com.badlogic.gdx.math.Rectangle;

public class FrustumCullingComponent extends Component {

	public Rectangle bounds;

	public FrustumCullingComponent(Rectangle bounds) {
		this.bounds = bounds;
	}

}
