package com.gemserk.commons.svg.inkscape;

import java.util.StringTokenizer;

import com.gemserk.vecmath.Matrix3f;

public class SvgTransformUtils {

	/**
	 * Parses a SVG Element transform attribute and applies each transform to the specified matrix.
	 * 
	 * @param transform
	 *            The SVG Element transform attribute.
	 * @param matrix
	 *            The matrix to be modified.
	 * @return The matrix with all transforms applied.
	 */
	public static Matrix3f parseTransform(String transform, Matrix3f matrix) {
		matrix.setIdentity();
		
		transform = transform.replace(" ", "");

		if (transform.startsWith("scale")) {
			transform = transform.substring(0, transform.length() - 1);
			transform = transform.substring("scale(".length());
			StringTokenizer tokens = new StringTokenizer(transform, ", ");

			float sx = Float.parseFloat(tokens.nextToken());
			float sy = sx;

			if (tokens.hasMoreTokens())
				sy = Float.parseFloat(tokens.nextToken());

			matrix.setM00(sx);
			matrix.setM11(sy);
		} else if (transform.startsWith("matrix")) {

			transform = transform.substring(0, transform.length() - 1);
			transform = transform.substring("matrix(".length());

			StringTokenizer tokens = new StringTokenizer(transform, ", ");

			float[] tr = new float[6];

			for (int j = 0; j < tr.length; j++) {
				tr[j] = Float.parseFloat(tokens.nextToken());
			}

			matrix.setM00(tr[0]);
			matrix.setM10(tr[1]);

			matrix.setM01(tr[2]);
			matrix.setM11(tr[3]);

			matrix.setM02(tr[4]);
			matrix.setM12(tr[5]);
		} else if (transform.startsWith("translate")) {
			transform = transform.substring(0, transform.length() - 1);
			transform = transform.substring("translate(".length());
			StringTokenizer tokens = new StringTokenizer(transform, ", ");

			float tx = Float.parseFloat(tokens.nextToken());
			float ty = Float.parseFloat(tokens.nextToken());

			matrix.setM02(tx);
			matrix.setM12(ty);
		}
		return matrix;
	}

	/**
	 * Serializes the matrix to be in SVG transform format.
	 */
	public static String serializeTransform(Matrix3f matrix) {
		return "matrix(" + matrix.m00 + "," + matrix.m10 + "," + matrix.m01 + "," + matrix.m11 + "," + matrix.m02 + "," + matrix.m12 + ")";
	}

	public static boolean isFlipped(Matrix3f matrix) {
		return matrix.getM00() != matrix.getM11();
	}

}
