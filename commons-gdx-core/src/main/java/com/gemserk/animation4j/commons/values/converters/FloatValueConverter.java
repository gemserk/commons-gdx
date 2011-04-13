package com.gemserk.animation4j.commons.values.converters;

import com.gemserk.animation4j.converters.TypeConverter;
import com.gemserk.commons.values.FloatValue;

public class FloatValueConverter implements TypeConverter<FloatValue> {
	@Override
	public int variables() {
		return 1;
	}

	@Override
	public FloatValue copyToObject(FloatValue object, float[] x) {
		if (object == null)
			object = new FloatValue(0);
		object.value = x[0];
		return object;
	}

	@Override
	public float[] copyFromObject(FloatValue object, float[] x) {
		if (x== null)
			x = new float[variables()];
		x[0] = object.value;
		return x;
	}
}