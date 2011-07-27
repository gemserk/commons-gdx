package com.gemserk.commons.artemis.components;

import com.artemis.Component;

public class RenderableComponent extends Component {

	private int layer;
	
	public int getLayer() {
		return layer;
	}
	
	public RenderableComponent(int layer) {
		this.layer = layer;
	}

}
