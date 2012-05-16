package com.gemserk.animation4j.gdx.converters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.gemserk.animation4j.converters.TypeConverter;
import com.gemserk.commons.gdx.graphics.SpriteUtils;

public class LibgdxConverters {

	public static final TypeConverter<Color> colorConverter = new ColorConverter();
	public static final TypeConverter<Vector2> vector2Converter = new Vector2Converter();
	
	// there is no need for these methods
	
	public static TypeConverter<Color> color() {
		return colorConverter;
	}

	public static TypeConverter<Vector2> vector2() {
		return vector2Converter;
	}
	
	public static final TypeConverter<Color> colorOpacityConverter = new TypeConverter<Color>() {
		@Override
		public int variables() {
			return 1;
		}

		@Override
		public Color copyToObject(Color color, float[] x) {
			color.a = x[0];
			return color;
		}

		@Override
		public float[] copyFromObject(Color color, float[] x) {
			if (x == null)
				x = new float[variables()];
			x[0] = color.a;
			return x;
		}
	};
	
	public static final TypeConverter<Sprite> spriteColorConverter = new TypeConverter<Sprite>() {

		@Override
		public float[] copyFromObject(Sprite object, float[] x) {
			if (x == null)
				x = new float[variables()];
			Color color = object.getColor();
			x[0] = color.r;
			x[1] = color.g;
			x[2] = color.b;
			x[3] = color.a;
			return x;
		}

		@Override
		public Sprite copyToObject(Sprite object, float[] x) {
			object.setColor(x[0], x[1], x[2], x[3]);
			return object;
		}

		@Override
		public int variables() {
			return 4;
		}

	};
	
	public static final TypeConverter<Sprite> spriteOpacityConverter = new TypeConverter<Sprite>() {

		@Override
		public float[] copyFromObject(Sprite object, float[] x) {
			if (x == null)
				x = new float[variables()];
			Color color = object.getColor();
			x[0] = color.a;
			return x;
		}

		@Override
		public Sprite copyToObject(Sprite object, float[] x) {
			Color color = object.getColor();
			object.setColor(color.r, color.g, color.b, x[0]);
			return object;
		}

		@Override
		public int variables() {
			return 1;
		}

	};

	public static final TypeConverter<Sprite> spritePositionConverter = new TypeConverter<Sprite>() {

		@Override
		public float[] copyFromObject(Sprite object, float[] x) {
			if (x == null)
				x = new float[variables()];
			x[0] = object.getX();
			x[1] = object.getY();
			return x;
		}

		@Override
		public Sprite copyToObject(Sprite object, float[] x) {
			object.setPosition(x[0] - object.getOriginX(), x[1] - object.getOriginY());
			return object;
		}

		@Override
		public int variables() {
			return 2;
		}

	};

	public static final TypeConverter<Sprite> spriteSizeConverter = new TypeConverter<Sprite>() {

		@Override
		public float[] copyFromObject(Sprite object, float[] x) {
			if (x == null)
				x = new float[variables()];
			x[0] = object.getWidth();
			x[1] = object.getHeight();
			return x;
		}

		@Override
		public Sprite copyToObject(Sprite object, float[] x) {
			object.setSize(x[0], x[1]);
			SpriteUtils.center(object, 0.5f, 0.5f);
			return object;
		}

		@Override
		public int variables() {
			return 2;
		}

	};

	public static final TypeConverter<Sprite> spriteScaleConverter = new TypeConverter<Sprite>() {

		@Override
		public float[] copyFromObject(Sprite object, float[] x) {
			if (x == null)
				x = new float[variables()];
			x[0] = object.getScaleX();
			x[1] = object.getScaleY();
			return x;
		}

		@Override
		public Sprite copyToObject(Sprite object, float[] x) {
			object.setScale(x[0], x[1]);
			return object;
		}

		@Override
		public int variables() {
			return 2;
		}

	};

	public static final TypeConverter<Sprite> spriteRotationConverter = new TypeConverter<Sprite>() {

		@Override
		public float[] copyFromObject(Sprite object, float[] x) {
			if (x == null)
				x = new float[variables()];
			x[0] = object.getRotation();
			return x;
		}

		@Override
		public Sprite copyToObject(Sprite object, float[] x) {
			object.setRotation(x[0]);
			return object;
		}

		@Override
		public int variables() {
			return 1;
		}

	};

	public static final TypeConverter<OrthographicCamera> orthographicCameraPositionConverter = new TypeConverter<OrthographicCamera>() {

		@Override
		public float[] copyFromObject(OrthographicCamera object, float[] x) {
			if (x == null)
				x = new float[variables()];
			x[0] = object.position.x;
			x[1] = object.position.y;
			return x;
		}

		@Override
		public OrthographicCamera copyToObject(OrthographicCamera object, float[] x) {
			object.position.x = x[0];
			object.position.y = x[1];
			return object;
		}

		@Override
		public int variables() {
			return 2;
		}

	};
	
	public static final TypeConverter<OrthographicCamera> orthographicCameraZoomConverter = new TypeConverter<OrthographicCamera>() {

		@Override
		public float[] copyFromObject(OrthographicCamera object, float[] x) {
			if (x == null)
				x = new float[variables()];
			x[0] = object.zoom;
			return x;
		}

		@Override
		public OrthographicCamera copyToObject(OrthographicCamera object, float[] x) {
			object.zoom = x[0];
			return object;
		}

		@Override
		public int variables() {
			return 1;
		}

	};

}
