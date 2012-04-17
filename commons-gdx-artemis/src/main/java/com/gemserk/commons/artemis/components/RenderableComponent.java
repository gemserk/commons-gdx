package com.gemserk.commons.artemis.components;

import com.artemis.Component;
import com.artemis.ComponentType;
import com.artemis.ComponentTypeManager;
import com.artemis.Entity;

public class RenderableComponent extends Component {
	
	public static final ComponentType type = ComponentTypeManager.getTypeFor(RenderableComponent.class);

	public static RenderableComponent get(Entity e) {
		return (RenderableComponent) e.getComponent(type);
	}

	public boolean visible;
	public int layer;
	public int subLayer;
	
	public int getLayer() {
		return layer;
	}
	
	public int getSubLayer() {
		return subLayer;
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public void setLayer(int layer) {
		this.layer = layer;
	}
	
	public void setLayer(int layer, int subLayer) {
		this.layer = layer;
		this.subLayer = subLayer;
	}
	
	public RenderableComponent(int layer) {
		this(layer, true);
	}

	public RenderableComponent(int layer, boolean visible) {
		this(layer, 0, visible);
	}
	
	public RenderableComponent(int layer, int subLayer) {
		this(layer, subLayer, true);
	}


	public RenderableComponent(int layer, int subLayer, boolean visible) {
		this.layer = layer;
		this.subLayer = subLayer;
		this.visible = visible;
	}

}
