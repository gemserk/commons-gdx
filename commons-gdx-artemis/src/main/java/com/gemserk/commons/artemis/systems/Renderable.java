package com.gemserk.commons.artemis.systems;

import com.artemis.Entity;

public class Renderable {

	// if it has owner it uses the id of the owner...
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
	
	///
	
	Entity entity;
	
	public Entity getEntity() {
		return entity;
	}
	
}
