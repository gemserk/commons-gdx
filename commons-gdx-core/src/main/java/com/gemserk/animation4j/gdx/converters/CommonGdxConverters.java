package com.gemserk.animation4j.gdx.converters;

import com.gemserk.animation4j.converters.TypeConverter;
import com.gemserk.commons.gdx.camera.Camera;
import com.gemserk.commons.gdx.games.Spatial;

public class CommonGdxConverters {

	public static final TypeConverter<Camera> cameraPositionConverter = new TypeConverter<Camera>() {

		@Override
		public int variables() {
			return 2;
		}

		@Override
		public Camera copyToObject(Camera camera, float[] x) {
			camera.setPosition(x[0], x[1]);
			return camera;
		}

		@Override
		public float[] copyFromObject(Camera camera, float[] x) {
			if (x == null)
				x = new float[variables()];
			x[0] = camera.getX();
			x[1] = camera.getY();
			return x;

		}
	};

	public static final TypeConverter<Camera> cameraZoomConverter = new TypeConverter<Camera>() {

		@Override
		public int variables() {
			return 1;
		}

		@Override
		public Camera copyToObject(Camera camera, float[] x) {
			camera.setZoom(x[0]);
			return camera;
		}

		@Override
		public float[] copyFromObject(Camera camera, float[] x) {
			if (x == null)
				x = new float[variables()];
			x[0] = camera.getZoom();
			return x;

		}
	};

	public static final TypeConverter<Spatial> spatialConverter = new TypeConverter<Spatial>() {

		@Override
		public int variables() {
			return 3;
		}

		@Override
		public float[] copyFromObject(Spatial spatial, float[] x) {
			if (x == null)
				x = new float[variables()];
			x[0] = spatial.getX();
			x[1] = spatial.getY();
			x[2] = spatial.getAngle();
			return x;
		}

		@Override
		public Spatial copyToObject(Spatial spatial, float[] x) {
			spatial.setPosition(x[0], x[1]);
			spatial.setAngle(x[2]);
			return spatial;
		}

	};

}
