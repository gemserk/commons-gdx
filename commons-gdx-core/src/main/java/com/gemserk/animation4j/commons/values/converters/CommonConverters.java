package com.gemserk.animation4j.commons.values.converters;

import com.gemserk.animation4j.converters.TypeConverter;
import com.gemserk.commons.gdx.games.Spatial;
import com.gemserk.commons.values.FloatValue;

public class CommonConverters {
	
	public static final FloatValueConverter floatValueConverter = new FloatValueConverter();

	public static TypeConverter<FloatValue> floatValue() {
		return floatValueConverter;
	}
	
	public static final SpatialSizeTypeConverter spatialSizeTypeConverter = new SpatialSizeTypeConverter();

	public static final TypeConverter<Spatial> spatialPositionConverter = new TypeConverter<Spatial>() {

		@Override
		public int variables() {
			return 2;
		}

		@Override
		public float[] copyFromObject(Spatial spatial, float[] x) {
			if (x == null)
				x = new float[variables()];
			x[0] = spatial.getX();
			x[1] = spatial.getY();
			return x;
		}

		@Override
		public Spatial copyToObject(Spatial spatial, float[] x) {
			spatial.setPosition(x[0], x[1]);
			return null;
		}
		
	};
}
