package com.gemserk.commons.gdx.graphics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * 
 * Triangulator implementation using the algorithm presented by John W. Ratcliff <a href="http://www.flipcode.com/archives/Efficient_Polygon_Triangulation.shtml">here</a> although he is not the owner of the algorithm.
 * 
 */
public class EfficientPolygonTriangulator implements Triangulator {

	private static final float EPSILON = 0.0000000001f;

	Array<Vector2> points = new Array<Vector2>(100);
	Array<Vector2> triangles;
	
	private float[] point = new float[2];

	@Override
	public boolean triangulate() {
		triangles = new Array<Vector2>();
		return process(points, triangles);
	}

	@Override
	public void addPolyPoint(float x, float y) {
		points.add(new Vector2(x, y));
	}

	@Override
	public int getTriangleCount() {
		return triangles.size / 3;
	}

	@Override
	public float[] getTrianglePoint(int tri, int i) {
		point[0] = getTrianglePointX(tri, i);
		point[1] = getTrianglePointY(tri, i);
		return point;
	}

	@Override
	public float getTrianglePointX(int tri, int i) {
		return triangles.get(tri * 3 + i).x;
	}

	@Override
	public float getTrianglePointY(int tri, int i) {
		return triangles.get(tri * 3 + i).y;
	}

	private static boolean process(Array<Vector2> contour, Array<Vector2> result) {
		/* allocate and initialize list of Vertices in polygon */

		int n = contour.size;
		if (n < 3)
			return false;

		int[] V = new int[n];

		/* we want a counter-clockwise polygon in V */

		if (0.0f < area(contour))
			for (int v = 0; v < n; v++)
				V[v] = v;
		else
			for (int v = 0; v < n; v++)
				V[v] = (n - 1) - v;

		int nv = n;

		/* remove nv-2 Vertices, creating 1 triangle every time */
		int count = 2 * nv; /* error detection */

		for (int m = 0, v = nv - 1; nv > 2;) {
			/* if we loop, it is probably a non-simple polygon */
			if (0 >= (count--)) {
				// ** Triangulate: ERROR - probable bad polygon!
				return false;
			}

			/* three consecutive vertices in current polygon, <u,v,w> */
			int u = v;
			if (nv <= u)
				u = 0; /* previous */
			v = u + 1;
			if (nv <= v)
				v = 0; /* new v */
			int w = v + 1;
			if (nv <= w)
				w = 0; /* next */

			if (snip(contour, u, v, w, nv, V)) {
				int a, b, c, s, t;

				/* true names of the vertices */
				a = V[u];
				b = V[v];
				c = V[w];

				/* output Triangle */
				
				// I am reusing the Vector2 here, not sure if that could lead to problems.
				result.add(contour.get(a));
				result.add(contour.get(b));
				result.add(contour.get(c));

				m++;

				/* remove v from remaining polygon */
				for (s = v, t = v + 1; t < nv; s++, t++)
					V[s] = V[t];
				nv--;

				/* resest error detection counter */
				count = 2 * nv;
			}
		}

		return true;
	}

	static float area(Array<Vector2> contour) {
		int n = contour.size;

		float A = 0.0f;

		for (int p = n - 1, q = 0; q < n; p = q++) {
			Vector2 p0 = contour.get(p);
			Vector2 p1 = contour.get(q);
			A += p0.x * p1.y - p1.x * p0.y;
		}

		return A * 0.5f;
	}

	static boolean snip(Array<Vector2> contour, int u, int v, int w, int n, int[] V) {
		int p;
		float Ax, Ay, Bx, By, Cx, Cy, Px, Py;

		Ax = contour.get(V[u]).x;
		Ay = contour.get(V[u]).y;

		Bx = contour.get(V[v]).x;
		By = contour.get(V[v]).y;

		Cx = contour.get(V[w]).x;
		Cy = contour.get(V[w]).y;

		if (EPSILON > (((Bx - Ax) * (Cy - Ay)) - ((By - Ay) * (Cx - Ax))))
			return false;

		for (p = 0; p < n; p++) {
			if ((p == u) || (p == v) || (p == w))
				continue;
			Px = contour.get(V[p]).x;
			Py = contour.get(V[p]).y;

			if (insideTriangle(Ax, Ay, Bx, By, Cx, Cy, Px, Py))
				return false;
		}

		return true;
	}

	static boolean insideTriangle(float Ax, float Ay, float Bx, float By, float Cx, float Cy, float Px, float Py) {
		float ax, ay, bx, by, cx, cy, apx, apy, bpx, bpy, cpx, cpy;
		float cCROSSap, bCROSScp, aCROSSbp;

		ax = Cx - Bx;
		ay = Cy - By;
		bx = Ax - Cx;
		by = Ay - Cy;
		cx = Bx - Ax;
		cy = By - Ay;
		apx = Px - Ax;
		apy = Py - Ay;
		bpx = Px - Bx;
		bpy = Py - By;
		cpx = Px - Cx;
		cpy = Py - Cy;

		aCROSSbp = ax * bpy - ay * bpx;
		cCROSSap = cx * apy - cy * apx;
		bCROSScp = bx * cpy - by * cpx;

		return ((aCROSSbp >= 0.0f) && (bCROSScp >= 0.0f) && (cCROSSap >= 0.0f));
	}
}
