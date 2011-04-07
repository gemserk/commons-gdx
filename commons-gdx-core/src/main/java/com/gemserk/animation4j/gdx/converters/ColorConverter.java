package com.gemserk.animation4j.gdx.converters;

import com.badlogic.gdx.graphics.Color;
import com.gemserk.animation4j.converters.TypeConverter;

public class ColorConverter implements TypeConverter<Color> {

	@Override
	public int variables() {
		return 4;
	}

	@Override
	public float[] copyFromObject(Color object, float[] x) {
		if (x == null)
			x = new float[variables()];
		x[0] = object.r;
		x[1] = object.g;
		x[2] = object.b;
		x[3] = object.a;
		return x;
	}

	@Override
	public Color copyToObject(Color object, float[] x) {
		if (object == null)
			object = new Color();
		object.r = x[0];
		object.g = x[1];
		object.b = x[2];
		object.a = x[3];
		return object;
	}

}
