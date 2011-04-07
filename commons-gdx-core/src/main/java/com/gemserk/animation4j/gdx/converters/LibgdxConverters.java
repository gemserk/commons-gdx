package com.gemserk.animation4j.gdx.converters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.gemserk.animation4j.converters.TypeConverter;

public class LibgdxConverters {
	
	private static final TypeConverter<Color> colorConverter = new ColorConverter();
	
	private static final TypeConverter<Vector2> vector2Converter = new Vector2Converter();
	
	public static TypeConverter<Color> color() {
		return colorConverter;
	}

	public static TypeConverter<Vector2> vector2() {
		return vector2Converter;
	}

}
