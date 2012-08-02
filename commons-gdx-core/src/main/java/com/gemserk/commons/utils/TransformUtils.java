package com.gemserk.commons.utils;

import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;

public class TransformUtils {
	
	private static final Vector2 tmp = new Vector2();
	
	public static void transformVertices(float[][] vertices, Matrix3 transform) {
		for (int i = 0; i < vertices.length; i++) {
			float[] quad = vertices[i];
			transformVertex(quad, 0, 1, transform);
			transformVertex(quad, 2, 3, transform);
			transformVertex(quad, 4, 5, transform);
			transformVertex(quad, 6, 7, transform);
		}
	}
	
	/**
	 * Transforms the quad vertex by the given transformation.
	 * 
	 * @param quad
	 *            The quad as a float[] of 4 vertex of 2 coordinates each one.
	 * @param vx
	 *            The index of the vertex x component inside the array.
	 * @param vy
	 *            The index of the vertex y component inside the array.
	 * @param transform
	 *            The transforamation to apply.
	 */
	public static void transformVertex(float[] quad, int vx, int vy, Matrix3 transform) {
		tmp.set(quad[vx], quad[vy]).mul(transform);
		quad[vx] = tmp.x;
		quad[vy] = tmp.y;
	}
	
	public static void translateVertices(float[][] vertices, float tx, float ty) {
		for (int i = 0; i < vertices.length; i++) {
			float[] quad = vertices[i];
			translateVertices(quad, tx, ty);
		}
	}

	public static void translateVertices(float[] vertices, float tx, float ty) {
		vertices[0] += tx;
		vertices[1] += ty;
		vertices[2] += tx;
		vertices[3] += ty;
		vertices[4] += tx;
		vertices[5] += ty;
		vertices[6] += tx;
		vertices[7] += ty;
	}
	
	public static void scaleVertices(float[][] vertices, float sx, float sy) {
		for (int i = 0; i < vertices.length; i++) {
			float[] quad = vertices[i];
			scaleVertices(quad, sx, sy);
		}
	}

	public static void scaleVertices(float[] vertices, float sx, float sy) {
		vertices[0] *= sx;
		vertices[1] *= sy;
		vertices[2] *= sx;
		vertices[3] *= sy;
		vertices[4] *= sx;
		vertices[5] *= sy;
		vertices[6] *= sx;
		vertices[7] *= sy;
	}

	public static void setPosition(float[][] vertices, float[][] transformedVertices, float x, float y) {
		for (int i = 0; i < vertices.length; i++) {
			float[] sourceQuad = vertices[i];
			float[] transformedQuad = transformedVertices[i];
			if (sourceQuad == null)
				continue;
			transformedQuad[0] = sourceQuad[0] + x;
			transformedQuad[1] = sourceQuad[1] + y;
			transformedQuad[2] = sourceQuad[2] + x;
			transformedQuad[3] = sourceQuad[3] + y;
			transformedQuad[4] = sourceQuad[4] + x;
			transformedQuad[5] = sourceQuad[5] + y;
			transformedQuad[6] = sourceQuad[6] + x;
			transformedQuad[7] = sourceQuad[7] + y;
		}
	}

	public static void setTransform(float[][] vertices, float[][] transformedVertices, Matrix3 transform) {
		for (int i = 0; i < vertices.length; i++) {
			float[] sourceQuad = vertices[i];
			float[] transformedQuad = transformedVertices[i];
			if (sourceQuad == null)
				continue;
			setTransform(sourceQuad, transformedQuad, transform, 0, 1);
			setTransform(sourceQuad, transformedQuad, transform, 2, 3);
			setTransform(sourceQuad, transformedQuad, transform, 4, 5);
			setTransform(sourceQuad, transformedQuad, transform, 6, 7);
		}
	}

	public static void setTransform(float[] quad, float[] transformedQuad, Matrix3 transform, int vx, int vy) {
		tmp.set(quad[vx], quad[vy]).mul(transform);
		transformedQuad[vx] = tmp.x;
		transformedQuad[vy] = tmp.y;
	}

}
