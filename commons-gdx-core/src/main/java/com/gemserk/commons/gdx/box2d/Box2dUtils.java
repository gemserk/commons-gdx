package com.gemserk.commons.gdx.box2d;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.Shape.Type;
import com.gemserk.commons.gdx.graphics.ShapeUtils;
import com.gemserk.commons.gdx.graphics.Triangulator;

public class Box2dUtils {
	
	static final Vector2[] triangleVertices = new Vector2[3];

	{
		for (int i = 0; i < triangleVertices.length; i++)
			triangleVertices[i] = new Vector2();
	}

	public static Vector2[] initArray(int count) {
		return initArray(new Vector2[count]);
	}

	public static Vector2[] initArray(Vector2[] array) {
		for (int i = 0; i < array.length; i++)
			array[i] = new Vector2();
		return array;
	}

	public static Vector2[] createRectangle(float width, float height) {
		return createRectangle(width, height, initArray(4));
	}

	public static Vector2[] createRectangle(float width, float height, Vector2[] vertices) {
		vertices[0].set(-width * 0.5f, -height * 0.5f);
		vertices[1].set(width * 0.5f, -height * 0.5f);
		vertices[2].set(width * 0.5f, height * 0.5f);
		vertices[3].set(-width * 0.5f, height * 0.5f);
		return vertices;
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
		// TODO: should be in a Box2dUtils
		for (int i = 0; i < fixtures.size(); i++) {
			Fixture fixture = fixtures.get(i);
			Shape shape = fixture.getShape();

			if (shape.getType() == Type.Polygon)
				ShapeUtils.translatePolygonShape((PolygonShape) shape, tx, ty);
			else if (shape.getType() == Type.Circle)
				ShapeUtils.translateCircleShape((CircleShape) shape, tx, ty);
		}
	}

	private static final Filter filter = new Filter();

	public static void setFilter(Body body, Filter filter) {
		ArrayList<Fixture> fixtureList = body.getFixtureList();
		for (int i = 0; i < fixtureList.size(); i++) {
			Fixture fixture = fixtureList.get(i);
			fixture.setFilterData(filter);
		}
	}

	public static void setFilter(Body body, short categoryBits, short maskBits, short groupIndex) {
		filter.categoryBits = categoryBits;
		filter.maskBits = maskBits;
		filter.groupIndex = groupIndex;
		setFilter(body, filter);
	}

	public static FixtureDef[] fixturesFromTriangulator(Triangulator triangulator) {
		FixtureDef[] fixtureDefs = new FixtureDef[triangulator.getTriangleCount()];
		FixtureDefBuilder fixtureDefBuilder = new FixtureDefBuilder();

		for (int j = 0; j < triangulator.getTriangleCount(); j++) {

			for (int p = 0; p < 3; p++) {
				float[] pt = triangulator.getTrianglePoint(j, p);
				triangleVertices[p].set(pt[0], pt[1]);
			}

			fixtureDefs[j] = fixtureDefBuilder //
					.polygonShape(triangleVertices) //
					.restitution(0f) //
					.build();

		}
		
		return fixtureDefs;
	}
}
