package com.gemserk.commons.gdx.graphics;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.Shape.Type;

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
		for (int i = 0; i < fixtures.size(); i++) {
			Fixture fixture = fixtures.get(i);
			Shape shape = fixture.getShape();

			if (shape.getType() == Type.Polygon)
				translatePolygonShape((PolygonShape) shape, tx, ty);
			else if (shape.getType() == Type.Circle)
				translateCircleShape((CircleShape) shape, tx, ty);
		}
	}
}
