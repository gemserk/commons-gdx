package com.gemserk.commons.gdx.graphics;

import java.nio.FloatBuffer;

public class Mesh2dUtils {

	public static Mesh2d quad(float x1, float y1, float x2, float y2) {
		return new Mesh2dBuilder() //
				.color(1f, 1f, 1f, 1f) //
				.vertex(x1, y1) //
				.color(1f, 1f, 1f, 1f) //
				.vertex(x2, y1) //
				.color(1f, 1f, 1f, 1f) //
				.vertex(x2, y2) //
				.color(1f, 1f, 1f, 1f) //
				.vertex(x2, y2) //
				.color(1f, 1f, 1f, 1f) //
				.vertex(x1, y2) //
				.color(1f, 1f, 1f, 1f) //
				.vertex(x1, y1) //
				.build();
	}

	public static void setColor(Mesh2d mesh2d, float r, float g, float b, float a) {
		FloatBuffer colorArray = mesh2d.getColorArray();
		if (colorArray == null)
			return;
		colorArray.rewind();
		while (colorArray.hasRemaining()) {
			colorArray.put(r);
			colorArray.put(g);
			colorArray.put(b);
			colorArray.put(a);
		}
		colorArray.rewind();
	}
}