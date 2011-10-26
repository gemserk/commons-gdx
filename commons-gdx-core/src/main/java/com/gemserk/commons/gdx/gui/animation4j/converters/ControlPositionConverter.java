package com.gemserk.commons.gdx.gui.animation4j.converters;

import com.gemserk.animation4j.converters.TypeConverter;
import com.gemserk.commons.gdx.gui.Control;

public class ControlPositionConverter implements TypeConverter<Control> {
	@Override
	public int variables() {
		return 2;
	}

	@Override
	public float[] copyFromObject(Control object, float[] x) {
		if (x == null)
			x = new float[variables()];
		x[0] = object.getX();
		x[1] = object.getY();
		return x;
	}

	@Override
	public Control copyToObject(Control object, float[] x) {
		object.setPosition(Math.round(x[0]), Math.round(x[1]));
		return object;
	}
}