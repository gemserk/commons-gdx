package com.gemserk.animation4j.gdx.scenes.scene2d;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.gemserk.animation4j.converters.TypeConverter;

public class Scene2dConverters {
	
	public static final TypeConverter<Actor> actorPositionTypeConverter = new TypeConverter<Actor>() {
		@Override
		public int variables() {
			return 2;
		}

		@Override
		public float[] copyFromObject(Actor object, float[] x) {
			if (x == null)
				x = new float[variables()];
			x[0] = object.x;
			x[1] = object.y;
			return x;
		}

		@Override
		public Actor copyToObject(Actor object, float[] x) {
			object.x = x[0];
			object.y = x[1];
			return object;
		}
	};

}
