package com.gemserk.animation4j.commons.values.converters;

import com.gemserk.animation4j.converters.TypeConverter;
import com.gemserk.commons.values.FloatValue;

public class CommonConverters {
	
	private static final FloatValueConverter floatValueConverter = new FloatValueConverter();

	public static TypeConverter<FloatValue> floatValue() {
		return floatValueConverter;
	}
	
	public static final SpatialSizeTypeConverter spatialSizeTypeConverter = new SpatialSizeTypeConverter();

}
