package com.gemserk.commons.gdx.graphics;

/**
 * A second triangulator that seems slightly more robust
 * 
 * @author kevglass
 * @author Online examples
 */
public class NeatTriangulator implements Triangulator {
	/** The error factor */
	static final float EPSILON = 1E-006F;

	/** The x coordinates */
	private float pointsX[];
	/** The y coordiantes */
	private float pointsY[];
	/** The number of points that have been added */
	private int numPoints;
	/** The edges defines by triangulation */
	private Edge edges[];
	/** Voroni */
	private int V[];
	/** The number of edges found */
	private int numEdges;
	/** The triangles that have been found */
	private Triangle triangles[];
	/** The number of triangles found */
	private int numTriangles;
	/** The current offset */
	private float offset = EPSILON;

	/**
	 * Create a new triangulator
	 */
	public NeatTriangulator() {
		pointsX = new float[100];
		pointsY = new float[100];
		numPoints = 0;
		edges = new Edge[100];
		numEdges = 0;
		triangles = new Triangle[100];
		numTriangles = 0;
	}

	/**
	 * Clear the triangulator status
	 */
	public void clear() {
		numPoints = 0;
		numEdges = 0;
		numTriangles = 0;
	}

	/**
	 * Find an edge between two verts
	 * 
	 * @param i
	 *            The index of the first vert
	 * @param j
	 *            The index of the second vert
	 * @return The index of the dge
	 */
	private int findEdge(int i, int j) {
		int k;
		int l;
		if (i < j) {
			k = i;
			l = j;
		} else {
			k = j;
			l = i;
		}
		for (int i1 = 0; i1 < numEdges; i1++)
			if (edges[i1].v0 == k && edges[i1].v1 == l)
				return i1;

		return -1;
	}

	/**
	 * Add a discovered edge
	 * 
	 * @param i
	 *            The index of the first vert
	 * @param j
	 *            The index of the second vert
	 * @param k
	 *            The index of the spread vert
	 */
	private void addEdge(int i, int j, int k) {
		int l1 = findEdge(i, j);
		int j1;
		int k1;
		Edge edge;
		if (l1 < 0) {
			if (numEdges == edges.length) {
				Edge aedge[] = new Edge[edges.length * 2];
				System.arraycopy(edges, 0, aedge, 0, numEdges);
				edges = aedge;
			}
			j1 = -1;
			k1 = -1;
			l1 = numEdges++;
			edge = edges[l1] = new Edge();
		} else {
			edge = edges[l1];
			j1 = edge.t0;
			k1 = edge.t1;
		}
		int l;
		int i1;
		if (i < j) {
			l = i;
			i1 = j;
			j1 = k;
		} else {
			l = j;
			i1 = i;
			k1 = k;
		}
		edge.v0 = l;
		edge.v1 = i1;
		edge.t0 = j1;
		edge.t1 = k1;
		edge.suspect = true;
	}

	/**
	 * Mark an edge as either a suspect or not
	 * 
	 * @param i
	 *            The index of the first vert
	 * @param j
	 *            The index of the second vert
	 * @param flag
	 *            True if the edge is a suspect
	 * @throws InternalException
	 *             Indicates the edge didn't exist
	 */
	void markSuspect(int i, int j, boolean flag) throws InternalException {
		int k;
		if (0 > (k = findEdge(i, j))) {
			throw new InternalException("Attempt to mark unknown edge");
		} else {
			edges[k].suspect = flag;
			return;
		}
	}

	/**
	 * Check if the point P is inside the triangle defined by the points A,B,C
	 * 
	 * @param f
	 *            Point A x-coordinate
	 * @param f1
	 *            Point A y-coordinate
	 * @param f2
	 *            Point B x-coordinate
	 * @param f3
	 *            Point B y-coordinate
	 * @param f4
	 *            Point C x-coordinate
	 * @param f5
	 *            Point C y-coordinate
	 * @param f6
	 *            Point P x-coordinate
	 * @param f7
	 *            Point P y-coordinate
	 * @return True if the point specified is within the triangle
	 */
	private static boolean insideTriangle(float f, float f1, float f2, float f3, float f4, float f5, float f6, float f7) {
		float f8 = f4 - f2;
		float f9 = f5 - f3;
		float f10 = f - f4;
		float f11 = f1 - f5;
		float f12 = f2 - f;
		float f13 = f3 - f1;
		float f14 = f6 - f;
		float f15 = f7 - f1;
		float f16 = f6 - f2;
		float f17 = f7 - f3;
		float f18 = f6 - f4;
		float f19 = f7 - f5;
		float f22 = f8 * f17 - f9 * f16;
		float f20 = f12 * f15 - f13 * f14;
		float f21 = f10 * f19 - f11 * f18;
		return f22 >= 0.0D && f21 >= 0.0D && f20 >= 0.0D;
	}

	/**
	 * Cut a the contour and add a triangle into V to describe the location of the cut
	 * 
	 * @param i
	 *            The index of the first point
	 * @param j
	 *            The index of the second point
	 * @param k
	 *            The index of the third point
	 * @param l
	 *            ?
	 * @return True if a triangle was found
	 */
	private boolean snip(int i, int j, int k, int l) {
		float f = pointsX[V[i]];
		float f1 = pointsY[V[i]];
		float f2 = pointsX[V[j]];
		float f3 = pointsY[V[j]];
		float f4 = pointsX[V[k]];
		float f5 = pointsY[V[k]];
		if (1E-006F > (f2 - f) * (f5 - f1) - (f3 - f1) * (f4 - f))
			return false;
		for (int i1 = 0; i1 < l; i1++)
			if (i1 != i && i1 != j && i1 != k) {
				float f6 = pointsX[V[i1]];
				float f7 = pointsY[V[i1]];
				if (insideTriangle(f, f1, f2, f3, f4, f5, f6, f7))
					return false;
			}

		return true;
	}

	/**
	 * Get the area defined by the points
	 * 
	 * @return The area defined by the points
	 */
	private float area() {
		float f = 0.0F;
		int i = numPoints - 1;
		for (int j = 0; j < numPoints;) {
			f += pointsX[i] * pointsY[j] - pointsY[i] * pointsX[j];
			i = j++;
		}

		return f * 0.5F;
	}

	/**
	 * Perform simple triangulation
	 * 
	 * @throws InternalException
	 *             Indicates a polygon that can't be triangulated
	 */
	public void basicTriangulation() throws InternalException {
		int i = numPoints;
		if (i < 3)
			return;
		numEdges = 0;
		numTriangles = 0;
		V = new int[i];

		if (0.0D < area()) {
			for (int k = 0; k < i; k++)
				V[k] = k;

		} else {
			for (int l = 0; l < i; l++)
				V[l] = numPoints - 1 - l;

		}
		int k1 = 2 * i;
		int i1 = i - 1;
		while (i > 2) {
			if (0 >= k1--) {
				throw new InternalException("Bad polygon");
			}

			int j = i1;
			if (i <= j)
				j = 0;
			i1 = j + 1;
			if (i <= i1)
				i1 = 0;
			int j1 = i1 + 1;
			if (i <= j1)
				j1 = 0;
			if (snip(j, i1, j1, i)) {
				int l1 = V[j];
				int i2 = V[i1];
				int j2 = V[j1];
				if (numTriangles == triangles.length) {
					Triangle atriangle[] = new Triangle[triangles.length * 2];
					System.arraycopy(triangles, 0, atriangle, 0, numTriangles);
					triangles = atriangle;
				}
				triangles[numTriangles] = new Triangle(l1, i2, j2);
				addEdge(l1, i2, numTriangles);
				addEdge(i2, j2, numTriangles);
				addEdge(j2, l1, numTriangles);
				numTriangles++;
				int k2 = i1;
				for (int l2 = i1 + 1; l2 < i; l2++) {
					V[k2] = V[l2];
					k2++;
				}

				i--;
				k1 = 2 * i;
			}
		}
		V = null;
	}

	/* (non-Javadoc)
	 * @see com.gemserk.commons.gdx.graphics.Triangulator#triangulate()
	 */
	@Override
	public boolean triangulate() {
		try {
			basicTriangulation();
			// optimize();
			return true;
		} catch (InternalException e) {
			numEdges = 0;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.gemserk.commons.gdx.graphics.Triangulator#addPolyPoint(float, float)
	 */
	@Override
	public void addPolyPoint(float x, float y) {
		for (int i = 0; i < numPoints; i++) {
			if ((pointsX[i] == x) && (pointsY[i] == y)) {
				// return;
				y += offset;
				offset += EPSILON;
			}
		}

		if (numPoints == pointsX.length) {
			float af[] = new float[numPoints * 2];
			System.arraycopy(pointsX, 0, af, 0, numPoints);
			pointsX = af;
			af = new float[numPoints * 2];
			System.arraycopy(pointsY, 0, af, 0, numPoints);
			pointsY = af;
		}

		pointsX[numPoints] = x;
		pointsY[numPoints] = y;
		numPoints++;
	}

	/**
	 * A single triangle
	 * 
	 * @author Online Source
	 */
	class Triangle {
		/** The verticies index */
		int v[];

		/**
		 * Create a new triangle
		 * 
		 * @param i
		 *            The index of vert 1
		 * @param j
		 *            The index of vert 2
		 * @param k
		 *            The index of vert 3
		 */
		Triangle(int i, int j, int k) {
			v = new int[3];
			v[0] = i;
			v[1] = j;
			v[2] = k;
		}
	}

	/**
	 * A single edge between two points
	 * 
	 * @author Online Source
	 */
	class Edge {
		/** The start vert */
		int v0;
		/** The end vert */
		int v1;
		/** The start tangent vert */
		int t0;
		/** The end tangent vert */
		int t1;
		/** True if the edge is marked as a suspect */
		boolean suspect;

		/**
		 * Create a new empty edge
		 */
		Edge() {
			v0 = -1;
			v1 = -1;
			t0 = -1;
			t1 = -1;
		}
	}

	/**
	 * A failure to triangulate, hidden from outside and handled
	 * 
	 * @author Online Source
	 */
	class InternalException extends Exception {
		/**
		 * 
		 */
		private static final long serialVersionUID = 3229408924537252039L;

		/**
		 * Create an internal exception
		 * 
		 * @param msg
		 *            The message describing the exception
		 */
		public InternalException(String msg) {
			super(msg);
		}
	}

	/* (non-Javadoc)
	 * @see com.gemserk.commons.gdx.graphics.Triangulator#getTriangleCount()
	 */
	@Override
	public int getTriangleCount() {
		return numTriangles;
	}

	/* (non-Javadoc)
	 * @see com.gemserk.commons.gdx.graphics.Triangulator#getTrianglePoint(int, int)
	 */
	@Override
	public float[] getTrianglePoint(int tri, int i) {
		float xp = pointsX[triangles[tri].v[i]];
		float yp = pointsY[triangles[tri].v[i]];

		return new float[] { xp, yp };
	}

	/**
	 * @see org.newdawn.slick.geom.Triangulator#startHole()
	 */
	public void startHole() {
	}
}
