package com.gemserk.commons.gdx.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Mesh;

public class MeshUtils {
	
	public static Mesh createVerticalGradient(final float width, final float height, Color[] gradientColors, float[] gradientStops) {

		if (gradientStops.length == 1) {
			Color color = gradientColors[0];
			Gdx2dMeshBuilder meshBuilder = new Gdx2dMeshBuilder(6, false, true, 0);
			meshBuilder //
					.color(color).vertex(0f, 1f) //
					.color(color).vertex(1f, 1f) //
					.color(color).vertex(0f, 0f) //
					.color(color).vertex(1f, 1f) //
					.color(color).vertex(0f, 0f) //
					.color(color).vertex(1f, 0f);
			Mesh mesh = meshBuilder.build();
			mesh.scale(width, height, 1f);
			return mesh;
		}

		Gdx2dMeshBuilder meshBuilder = new Gdx2dMeshBuilder((gradientStops.length - 1) * 6, false, true, 0);

		for (int i = 0; i < gradientStops.length - 1; i++) {

			float y0 = gradientStops[i];
			float y1 = gradientStops[i + 1];

			Color c0 = gradientColors[i];
			Color c1 = gradientColors[i + 1];

			meshBuilder //
					.color(c0).vertex(0f, y0) //
					.color(c0).vertex(1f, y0) //
					.color(c1).vertex(0f, y1) //
					.color(c0).vertex(1f, y0) //
					.color(c1).vertex(0f, y1) //
					.color(c1).vertex(1f, y1);
		}

		Mesh mesh = meshBuilder.build();
		mesh.scale(width, height, 1f);
		return mesh;
	}

}
