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

	/**
	 * Converts the coordinates of the vector to inside the rectangle at the other side, don't know the correct name of this method.
	 */
	public static void truncateWithModule(Vector2 v, Rectangle r) {
		if (v.x > r.x + r.width)
			v.x = r.x;
		else if (v.x < r.x)
			v.x = r.x + r.width;

		if (v.y > r.y + r.height)
			v.y = r.y;
		else if (v.y < r.y)
			v.y = r.y + r.height;
	}

	public static void truncate(Vector2 v, Rectangle r) {
		if (v.x > r.x + r.width)
			v.x = r.x + r.width;
		else if (v.x < r.x)
			v.x = r.x;
		if (v.y > r.y + r.height)
			v.y = r.y + r.height;
		else if (v.y < r.y)
			v.y = r.y;
	}

	public static float distance(float x1, float y1, float x2, float y2) {
		final float x_d = x2 - x1;
		final float y_d = y2 - y1;
		return (float) Math.sqrt(x_d * x_d + y_d * y_d);
	}

	public static float diagonal(float x, float y) {
		return (float) Math.sqrt(x * x + y * y);
	}

	/**
	 * Grow the rectangle by the specified amount.
	 * 
	 * @param r
	 *            The Rectangle to be modified.
	 * @param width
	 *            The amount to grow in the x axis;
	 * @param height
	 *            The amount to grow in the y axis;
	 */
	public static void growRectangle(Rectangle r, float width, float height) {
		r.x -= width * 0.5f;
		r.y -= height * 0.5f;
		r.width += width;
		r.height += height;
	}
	
	public static void shrinkRectangle(Rectangle r, float width, float height) {
		growRectangle(r, -width, -height);
	}
	
	public static void multiplyBy(float[] array, float factor){
		for (int i = 0; i < array.length; i++) {
			array[i]*=factor;
		}
	}
}
