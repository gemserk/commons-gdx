package com.gemserk.commons.gdx.graphics;

import java.util.ArrayList;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.gemserk.commons.gdx.box2d.Box2dUtils;

public class ShapeUtils {

	private static Vector2 tmp = new Vector2();
	private static Vector2[] vertices = new Vector2[8];

	static {
		for (int i = 0; i < vertices.length; i++)
			vertices[i] = new Vector2();
	}

	public static Triangulator triangulate(Vector2[] vertices) {
		NeatTriangulator triangulator = new NeatTriangulator();
		for (int i = 0; i < vertices.length; i++)
			triangulator.addPolyPoint(vertices[i].x, vertices[i].y);
		triangulator.triangulate();
		return triangulator;
	}

	public static void calculateCenter(Vector2[] vertices, Vector2 center) {
		center.x = 0f;
		center.y = 0f;
		for (int i = 0; i < vertices.length; i++) {
			center.x += vertices[i].x;
			center.y += vertices[i].y;
		}
		center.x /= vertices.length;
		center.y /= vertices.length;
	}

	public static void translateVertices(Vector2[] vertices, Vector2 tx) {
		for (int i = 0; i < vertices.length; i++)
			vertices[i].add(tx.x, tx.y);
	}

	public static void calculateBounds(Vector2[] vertices, Rectangle bounds) {
		bounds.x = Float.MAX_VALUE;
		bounds.y = Float.MAX_VALUE;

		bounds.width = -Float.MAX_VALUE;
		bounds.height = -Float.MAX_VALUE;

		for (int i = 0; i < vertices.length; i++) {
			Vector2 v = vertices[i];

			if (v.x < bounds.x)
				bounds.x = v.x;

			if (v.y < bounds.y)
				bounds.y = v.y;

			if (v.x > bounds.x + bounds.width)
				bounds.width = v.x - bounds.x;

			if (v.y > bounds.y + bounds.height)
				bounds.height = v.y - bounds.y;
		}
	}

	public void rotate(Vector2[] vertices, float angle) {
		for (int i = 0; i < vertices.length; i++)
			vertices[i].rotate(angle);
	}

	/**
	 * Returns the center of a given PolygonShape.
	 * 
	 * @param polygonShape
	 *            The PolygonShape to calculate the center.
	 * @param center
	 *            A Vector2 where the center will be stored.
	 */
	public static void calculateShapeCenter(PolygonShape polygonShape, Vector2 center) {
		center.x = 0f;
		center.y = 0f;

		int vertexCount = polygonShape.getVertexCount();

		if (vertexCount == 0)
			return;

		for (int i = 0; i < vertexCount; i++) {
			polygonShape.getVertex(i, tmp);
			center.x += tmp.x;
			center.y += tmp.y;
		}

		center.x /= vertexCount;
		center.y /= vertexCount;
	}

	/**
	 * Translates each vertex of a PolygonShape the specified translation. For now, it generates garbage so should be used only once.
	 * 
	 * @param polygonShape
	 *            The PolygonShape to be moved.
	 * @param tx
	 *            The translation in x coordinate.
	 * @param ty
	 *            The translation in y coordinate.
	 */
	public static void translatePolygonShape(PolygonShape polygonShape, float tx, float ty) {
		if (polygonShape.getVertexCount() > vertices.length)
			throw new RuntimeException("unexpected polygon shape length, should be less than " + vertices.length);
		Vector2[] newVertices = new Vector2[polygonShape.getVertexCount()];
		for (int i = 0; i < polygonShape.getVertexCount(); i++) {
			polygonShape.getVertex(i, vertices[i]);
			vertices[i].add(tx, ty);
			newVertices[i] = vertices[i];
		}
		polygonShape.set(newVertices);
	}

	/**
	 * Translates the CircleShape the specified translation.
	 * 
	 * @param circleShape
	 *            The CircleShape to be translated.
	 * @param tx
	 *            The translation in x coordinate.
	 * @param ty
	 *            The translation in y coordinate.
	 */
	public static void translateCircleShape(CircleShape circleShape, float tx, float ty) {
		Vector2 position = circleShape.getPosition();
		position.add(tx, ty);
		circleShape.setPosition(position);
	}

	/**
	 * Internally translates all the Body's fixtures the specified translation, without moving the Body's center. For now, it generates garbage so should be used only once.
	 * 
	 * @param body
	 *            The body to work on.
	 * @param tx
	 *            The translation in x coordinate.
	 * @param ty
	 *            The translation in y coordinate.
	 */
	public static void translateFixtures(ArrayList<Fixture> fixtures, float tx, float ty) {
		Box2dUtils.translateFixtures(fixtures, tx, ty);
		// TODO: remove this method by inlining...
	}

	/**
	 * Calculates the Convex Hull of a collection of points, extracted from <a href="http://www.cse.unsw.edu.au/~lambert/java/3d/ConvexHull.html">here</a>
	 * 
	 * @param points
	 *            An array with the points to be used when building the Convex Hull
	 * @param convexHullPoints
	 *            An array where the Convex Hull points will be stored.
	 */
	public static void calculateConvexHull(Array<Vector2> points, Array<Vector2> convexHullPoints) {

		if (points.size <= 1)
			return;

		Vector2 p;
		Vector2 bot = points.get(0);

		for (int i = 1; i < points.size; i++) {
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

			for (i = i + 1; i < points.size; i++) {
				Vector2 point = points.get(i);
				if (point != p && area(p, cand, point) > 0)
					cand = points.get(i);
			}

			convexHullPoints.add(cand);
			p = cand;
		} while (p != bot);

	}

	/* signed area of a triangle */
	public static float area(Vector2 a, Vector2 b, Vector2 c) {
		return b.x * c.y - b.y * c.x + c.x * a.y - c.y * a.x + a.x * b.y - a.y * b.x;
	}
}
