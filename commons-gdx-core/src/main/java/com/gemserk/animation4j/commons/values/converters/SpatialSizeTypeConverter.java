package com.gemserk.animation4j.commons.values.converters;

import com.gemserk.animation4j.converters.TypeConverter;
import com.gemserk.commons.gdx.games.Spatial;

public class SpatialSizeTypeConverter implements TypeConverter<Spatial> {

	@Override
	public int variables() {
		return 2;
	}

	@Override
	public float[] copyFromObject(Spatial sprite, float[] x) {
		if (x == null)
			x = new float[variables()];
		x[0] = sprite.getWidth();
		x[1] = sprite.getHeight();
		return x;
	}

	@Override
	public Spatial copyToObject(Spatial sprite, float[] x) {
		sprite.setSize(x[0], x[1]);
		return sprite;
	}

}