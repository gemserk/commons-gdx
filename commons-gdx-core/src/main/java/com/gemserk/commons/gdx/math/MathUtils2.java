package com.gemserk.commons.gdx.math;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class MathUtils2 {

	/**
	 * Returns true if the point is inside the rectangle, false otherwise
	 */
	public static boolean inside(Rectangle r, Vector2 p) {
		return inside(r, p.x, p.y);
	}
	
	/**
	 * Returns true whenever x,y is inside the rectangle, false otherwise
	 */
	public static boolean inside(Rectangle r, float x, float y) {

		if (x < r.x)
			return false;

		if (y < r.y)
			return false;

		if (x > r.x + r.width)
			return false;

		if (y > r.y + r.height)
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
