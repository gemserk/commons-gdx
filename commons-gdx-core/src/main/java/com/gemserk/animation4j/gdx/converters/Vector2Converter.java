package com.gemserk.animation4j.gdx.converters;

import com.badlogic.gdx.math.Vector2;
import com.gemserk.animation4j.converters.TypeConverter;

public class Vector2Converter implements TypeConverter<Vector2> {
	@Override
	public float[] copyFromObject(Vector2 object, float[] x) {
		if (x == null) 
			x = new float[variables()];
		x[0] = object.x;
		x[1] = object.y;
		return x;
	}

	@Override
	public Vector2 copyToObject(Vector2 object, float[] x) {
		if (object == null) 
			object = new Vector2();
		object.set(x[0], x[1]);
		return object;
	}

	@Override
	public int variables() {
		return 2;
	}
}