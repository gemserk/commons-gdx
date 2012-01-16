package com.gemserk.commons.gdx.graphics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Basic implementation of a Convex Hull calculation based on <a href="http://www.cse.unsw.edu.au/~lambert/java/3d/ConvexHull.html">here</a>.
 */
public class ConvexHull2dCalculationImpl implements ConvexHull2d {

	Array<Vector2> points;
	Array<Vector2> convexHullPoints;

	int size;

	public ConvexHull2dCalculationImpl(int initialCapacity) {
		points = new Array<Vector2>(initialCapacity);
		for (int i = 0; i < initialCapacity; i++)
			points.add(new Vector2());
		convexHullPoints = new Array<Vector2>();
		size = 0;
	}

	@Override
	public void add(float x, float y) {
		if (size >= points.size)
			points.add(new Vector2());
		points.get(size++).set(x, y);
	}

	@Override
	public void recalculate() {

		convexHullPoints.clear();

		if (size <= 1) {
			size = 0;
			return;
		}

		Vector2 p;
		Vector2 bot = points.get(0);

		for (int i = 1; i < size; i++) {
			Vector2 point = points.get(i);
			if (point.y < bot.y)
				bot = point;
		}

		convexHullPoints.add(bot);

		p = bot;

		do {
			int i;
			i = points.get(0) == p ? 1 : 0;
			Vector2 cand = points.get(i);

			for (i = i + 1; i < size; i++) {
				Vector2 point = points.get(i);
				if (point != p && area(p, cand, point) > 0)
					cand = points.get(i);
			}

			convexHullPoints.add(cand);
			p = cand;
		} while (p != bot);

		size = 0;
	}

	private float area(Vector2 a, Vector2 b, Vector2 c) {
		return b.x * c.y - b.y * c.x + c.x * a.y - c.y * a.x + a.x * b.y - a.y * b.x;
	}

	@Override
	public int getPointsCount() {
		return convexHullPoints.size;
	}

	@Override
	public float getX(int index) {
		return convexHullPoints.get(index).x;
	}

	@Override
	public float getY(int index) {
		return convexHullPoints.get(index).y;
	}

	@Override
	public boolean inside(float x, float y) {
		
		// using algorithm from here -> http://www.ecse.rpi.edu/Homepages/wrf/Research/Short_Notes/pnpoly.html
		
		int pointsCount = getPointsCount();
		boolean c = false;
		
		for (int i = 0, j = pointsCount - 1; i < pointsCount; j = i++) {
			
			float x0 = getX(i);
			float y0 = getY(i);

			float x1 = getX(j);
			float y1 = getY(j);

			if ( (y0 > y) != (y1 > y) && (x < (x1 - x0) * (y - y0) / (y1-y0) + x0 ) )
				c = !c;
			
		}
		
		return c;
	}
}