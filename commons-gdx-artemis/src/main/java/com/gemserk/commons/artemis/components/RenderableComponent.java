package com.gemserk.commons.artemis.components;

import com.artemis.Component;

public class RenderableComponent extends Component {

	private boolean visible;
	private int layer;
	
	public int getLayer() {
		return layer;
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public RenderableComponent(int layer) {
		this(layer, true);
	}

	public RenderableComponent(int layer, boolean visible) {
		this.layer = layer;
		this.visible = visible;
	}

}
