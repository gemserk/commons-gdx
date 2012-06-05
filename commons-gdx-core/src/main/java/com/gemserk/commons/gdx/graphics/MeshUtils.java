package com.gemserk.commons.gdx.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;

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

	public static void addRectangle(Gdx2dMeshBuilder builder, float x0, float y0, float x1, float y1, Color color) {

		builder //
				.color(color).vertex(x0, y0) //
				.color(color).vertex(x0, y1) //
				.color(color).vertex(x1, y1) //
				.color(color).vertex(x1, y1) //
				.color(color).vertex(x1, y0) //
				.color(color).vertex(x0, y0); //
	}

	/**
	 * Translates the Mesh the specified x and y coordinates.
	 * 
	 * @param mesh
	 *            The Mesh to be modified.
	 * @param x
	 *            The x coordinate to be translated.
	 * @param y
	 *            The y coordinate to be translated.
	 */
	public static Mesh translate(Mesh mesh, float x, float y) {
		VertexAttribute posAttr = mesh.getVertexAttribute(Usage.Position);
		int offset = posAttr.offset / 4;
		int numVertices = mesh.getNumVertices();
		int vertexSize = mesh.getVertexSize() / 4;

		float[] vertices = new float[numVertices * vertexSize];
		mesh.getVertices(vertices);

		int idx = offset;
		for (int i = 0; i < numVertices; i++) {
			vertices[idx] += x;
			vertices[idx + 1] += y;
			idx += vertexSize;
		}

		mesh.setVertices(vertices);

		return mesh;
	}

	/**
	 * Sets the colors of the vertices of the mesh.
	 * 
	 * @param mesh
	 *            The Mesh to be modified.
	 * @param colors
	 *            The new vertex colors.
	 * @param verticesCache
	 *            An array to contain all the vertices of the mesh size should be (mesh.getNumVertices()*mesh.getVertexSize()/4). This is to avoid GC
	 */
	public static Mesh changeColors(Mesh mesh, float[] colors, float[] verticesCache) {
		VertexAttribute posAttr = mesh.getVertexAttribute(Usage.ColorPacked);
		int offset = posAttr.offset / 4;
		int numVertices = mesh.getNumVertices();
		int vertexSize = mesh.getVertexSize() / 4;

		mesh.getVertices(verticesCache);

		int idx = offset;
		for (int i = 0; i < numVertices; i++) {
			verticesCache[idx] = colors[i];
			idx += vertexSize;
		}

		mesh.setVertices(verticesCache);

		return mesh;
	}

}
