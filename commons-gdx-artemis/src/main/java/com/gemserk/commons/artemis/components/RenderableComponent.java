package com.gemserk.commons.artemis.components;

import com.artemis.Component;
import com.artemis.ComponentTypeManager;
import com.artemis.Entity;
import com.gemserk.commons.artemis.systems.Renderable;

public class RenderableComponent extends Component {

	public static final int type = ComponentTypeManager.getTypeFor(RenderableComponent.class).getId();

	public static RenderableComponent get(Entity e) {
		return (RenderableComponent) e.getComponent(type);
	}

	public Renderable renderable;
	
	public RenderableComponent(int layer) {
		this(new Renderable(layer, 0, true));
	}

	public RenderableComponent(int layer, int subLayer) {
		this(new Renderable(layer, subLayer, true));
	}

	public RenderableComponent(int layer, int subLayer, boolean visible) {
		this(new Renderable(layer, subLayer, visible));
	}

	public RenderableComponent(Renderable renderable) {
		this.renderable = renderable;
	}

}
