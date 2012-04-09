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
