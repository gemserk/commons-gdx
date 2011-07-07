package com.gemserk.commons.gdx.graphics;

import com.badlogic.gdx.math.Vector2;

public class ShapeUtils {
	
	public static Triangulator triangulate(Vector2[] vertices) {
		NeatTriangulator triangulator = new NeatTriangulator();
		for (int i = 0; i < vertices.length; i++)
			triangulator.addPolyPoint(vertices[i].x, vertices[i].y);
		triangulator.triangulate();
		return triangulator;
	}

}
