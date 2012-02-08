package com.gemserk.commons.gdx.graphics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Implementation of Slick2D Triangulator using the <a href="http://www.ewjordan.com/earClip/">Ewjordan triangulation</a> algorithm. 
 */
public class EwjordanTriangulator implements Triangulator {

	private Array<Vector2> points = new Array<Vector2>(100);
	private Triangle[] triangles;
	
	public EwjordanTriangulator() {
		throw new UnsupportedOperationException("Not implemented correctly yet.");
	}

	@Override
	public boolean triangulate() {
		triangles = triangulatePolygon(points);
		return triangles != null;
	}

	@Override
	public void addPolyPoint(float x, float y) {
		points.add(new Vector2(x, y));
	}

	@Override
	public int getTriangleCount() {
		return triangles.length;
	}

	@Override
	public float[] getTrianglePoint(int tri, int i) {
		return new float[] {getTrianglePointX(tri, i), // 
				getTrianglePointY(tri, i)};
	}

	@Override
	public float getTrianglePointX(int tri, int i) {
		return triangles[i].x[i];
	}

	@Override
	public float getTrianglePointY(int tri, int i) {
		return triangles[i].y[i];
	}

	private static Triangle[] triangulatePolygon(Array<Vector2> points) {
		int vNum = points.size;
		
		if (vNum < 3)
			return null;

		Triangle[] buffer = new Triangle[vNum];
		int bufferSize = 0;
		float[] xrem = new float[vNum];
		float[] yrem = new float[vNum];
		
		for (int i = 0; i < vNum; ++i) {
			xrem[i] = points.get(i).x;
			yrem[i] = points.get(i).y;
		}

		while (vNum > 3) {
			int earIndex = -1;
			for (int i = 0; i < vNum; ++i) {
				if (isEar(i, xrem, yrem)) {
					earIndex = i;
					break;
				}
			}

			if (earIndex == -1)
				return null;

			--vNum;
			float[] newx = new float[vNum];
			float[] newy = new float[vNum];
			int currDest = 0;
			for (int i = 0; i < vNum; ++i) {
				if (currDest == earIndex) {
					++currDest;
				}
				newx[i] = xrem[currDest];
				newy[i] = yrem[currDest];
				++currDest;
			}

			int under = (earIndex == 0) ? (xrem.length - 1) : (earIndex - 1);
			int over = (earIndex == xrem.length - 1) ? 0 : (earIndex + 1);

			Triangle toAdd = new Triangle(xrem[earIndex], yrem[earIndex], xrem[over], yrem[over], xrem[under], yrem[under]);
			buffer[bufferSize] = toAdd;
			++bufferSize;

			xrem = newx;
			yrem = newy;
		}
		Triangle toAdd = new Triangle(xrem[1], yrem[1], xrem[2], yrem[2], xrem[0], yrem[0]);
		buffer[bufferSize] = toAdd;
		++bufferSize;

		Triangle[] res = new Triangle[bufferSize];
		System.arraycopy(buffer, 0, res, 0, bufferSize);
		return res;
	}

	private static boolean isEar(int i, float[] xv, float[] yv) {
		float dx0, dy0, dx1, dy1;
		dx0 = dy0 = dx1 = dy1 = 0;

		if (i >= xv.length || i < 0 || xv.length < 3)
			return false;

		int upper = i + 1;
		int lower = i - 1;

		if (i == 0) {
			dx0 = xv[0] - xv[xv.length - 1];
			dy0 = yv[0] - yv[yv.length - 1];
			dx1 = xv[1] - xv[0];
			dy1 = yv[1] - yv[0];
			lower = xv.length - 1;
		} else if (i == xv.length - 1) {
			dx0 = xv[i] - xv[i - 1];
			dy0 = yv[i] - yv[i - 1];
			dx1 = xv[0] - xv[i];
			dy1 = yv[0] - yv[i];
			upper = 0;
		} else {
			dx0 = xv[i] - xv[i - 1];
			dy0 = yv[i] - yv[i - 1];
			dx1 = xv[i + 1] - xv[i];
			dy1 = yv[i + 1] - yv[i];
		}

		float cross = dx0 * dy1 - dx1 * dy0;
		if (cross > 0)
			return false;

		Triangle myTri = new Triangle(xv[i], yv[i], xv[upper], yv[upper], xv[lower], yv[lower]);
		for (int j = 0; j < xv.length; ++j) {
			if (j == i || j == lower || j == upper)
				continue;
			if (myTri.isInside(xv[j], yv[j]))
				return false;
		}

		return true;
	}

	static class Triangle {

		float[] x;
		float[] y;

		Triangle(float x1, float y1, float x2, float y2, float x3, float y3) {
			x = new float[3];
			y = new float[3];
			float dx1 = x2 - x1;
			float dx2 = x3 - x1;
			float dy1 = y2 - y1;
			float dy2 = y3 - y1;
			float cross = dx1 * dy2 - dx2 * dy1;
			boolean ccw = (cross > 0);
			if (ccw) {
				x[0] = x1;
				x[1] = x2;
				x[2] = x3;
				y[0] = y1;
				y[1] = y2;
				y[2] = y3;
			} else {
				x[0] = x1;
				x[1] = x3;
				x[2] = x2;
				y[0] = y1;
				y[1] = y3;
				y[2] = y2;
			}
		}

		boolean isInside(float _x, float _y) {
			float vx2 = _x - x[0];
			float vy2 = _y - y[0];
			float vx1 = x[1] - x[0];
			float vy1 = y[1] - y[0];
			float vx0 = x[2] - x[0];
			float vy0 = y[2] - y[0];

			float dot00 = vx0 * vx0 + vy0 * vy0;
			float dot01 = vx0 * vx1 + vy0 * vy1;
			float dot02 = vx0 * vx2 + vy0 * vy2;
			float dot11 = vx1 * vx1 + vy1 * vy1;
			float dot12 = vx1 * vx2 + vy1 * vy2;
			float invDenom = 1f / (dot00 * dot11 - dot01 * dot01);
			float u = (dot11 * dot02 - dot01 * dot12) * invDenom;
			float v = (dot00 * dot12 - dot01 * dot02) * invDenom;

			return ((u > 0) && (v > 0) && (u + v < 1));
		}
	}
}
