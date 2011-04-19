package com.gemserk.componentsengine.properties;

import com.badlogic.gdx.math.Vector2;

public class PropertyBuilder {
	
	public static <T> Property<T> property(T t) {
		return new SimpleProperty<T>(t);
	}
	
	public static Vector2Property vector2(Vector2 v) {
		return new Vector2Property(v);
	}

	public static Vector2Property vector2(float x, float y) {
		return new Vector2Property(x, y);
	}

}
