package com.gemserk.commons.artemis.systems;

import com.artemis.Entity;

public class Renderable {

	int id;
	int layer;
	int subLayer;
	boolean visible;
	
	public boolean isVisible() {
		return visible;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public int getId() {
		return id;
	}
	
	public int getLayer() {
		return layer;
	}
	
	public int getSubLayer() {
		return subLayer;
	}
	
	public void setSubLayer(int subLayer) {
		this.subLayer = subLayer;
	}
	
	public void setLayer(int layer) {
		this.layer = layer;
	}
	
	public Renderable(int layer) {
		this(layer, true);
	}

	public Renderable(int layer, boolean visible) {
		this(layer, 0, visible);
	}
	
	public Renderable(int layer, int subLayer) {
		this(layer, subLayer, true);
	}

	public Renderable(int layer, int subLayer, boolean visible) {
		this.layer = layer;
		this.subLayer = subLayer;
		this.visible = visible;
	}
	
	///
	
	Entity entity;
	
	public Entity getEntity() {
		return entity;
	}
	
}
