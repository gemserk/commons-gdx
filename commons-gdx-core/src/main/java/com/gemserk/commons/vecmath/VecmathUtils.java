package com.gemserk.commons.vecmath;

import com.gemserk.vecmath.Matrix3f;
import com.gemserk.vecmath.Vector2f;
import com.gemserk.vecmath.Vector3f;

public class VecmathUtils {

	private static final Vector3f tmp = new Vector3f();

	public static void setToTranslation(Matrix3f m, float tx, float ty) {
		m.setIdentity();
		m.setM02(tx);
		m.setM12(ty);
	}

	/**
	 * Transforms a Vector2f using the specified transform Matrix3f using an internal temporary Vector3f.
	 * 
	 * @param m
	 *            The transformation matrix.
	 * @param v
	 *            The Vector2f to be transformed by the matrix.
	 */
	public static void transform(Matrix3f m, Vector2f v) {
		tmp.set(v.x, v.y, 1f);
		m.transform(tmp);
		v.set(tmp.x, tmp.y);
	}

}