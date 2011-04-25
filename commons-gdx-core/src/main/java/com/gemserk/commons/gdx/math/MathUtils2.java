package com.gemserk.commons.gdx.math;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class MathUtils2 {

	/**
	 * Returns true if the point is inside the rectangle, false otherwise
	 */
	public static boolean inside(Rectangle r, Vector2 p) {

		if (p.x < r.x)
			return false;

		if (p.y < r.y)
			return false;

		if (p.x > r.x + r.width)
			return false;

		if (p.y > r.y + r.height)
			return false;

		return true;

	}
	
	public static float truncate(float a, float min, float max) {
		if (a < min)
			a = min;
		if (a > max)
			a = max;
		return a;
	}
	
}
