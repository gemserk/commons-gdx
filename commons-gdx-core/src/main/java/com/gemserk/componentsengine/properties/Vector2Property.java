package com.gemserk.componentsengine.properties;

import com.badlogic.gdx.math.Vector2;

public class Vector2Property implements Property<Vector2> {
	
	Vector2 v = new Vector2();
	
	public Vector2Property(Vector2 v) {
		this.v.set(v);
	}
	
	public Vector2Property(float x, float y) {
		this.v.set(x, y);
	}

	@Override
	public Vector2 get() {
		return v;
	}

	@Override
	public void set(Vector2 v) {
		this.v.set(v);
	}

}
