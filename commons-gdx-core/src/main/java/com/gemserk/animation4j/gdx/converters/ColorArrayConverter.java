package com.gemserk.animation4j.gdx.converters;

import com.badlogic.gdx.graphics.Color;
import com.gemserk.animation4j.converters.TypeConverter;

public class ColorArrayConverter implements TypeConverter<Color[]> {

	private final int arraySize;

	public ColorArrayConverter(int arraySize) {
		this.arraySize = arraySize;
	}

	@Override
	public int variables() {
		return 4 * arraySize;
	}

	@Override
	public float[] copyFromObject(Color[] object, float[] x) {
		if (x == null)
			x = new float[variables()];

		for (int i = 0; i < arraySize; i++) {
			x[i + 0] = object[i].r;
			x[i + 1] = object[i].g;
			x[i + 2] = object[i].b;
			x[i + 3] = object[i].a;
		}
		return x;
	}

	@Override
	public Color[] copyToObject(Color object[], float[] x) {
		if (object == null) {
			object = new Color[arraySize];
			for (int i = 0; i < arraySize; i++) {
				object[i] = new Color();
			}
		}

		for (int i = 0; i < arraySize; i++) {
			Color color = object[i];
			color.r = x[i + 0];
			color.g = x[i + 1];
			color.b = x[i + 2];
			color.a = x[i + 3];
		}

		return object;
	}

}
