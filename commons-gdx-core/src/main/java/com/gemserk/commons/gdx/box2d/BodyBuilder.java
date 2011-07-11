package com.gemserk.commons.gdx.box2d;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Provides an easier way to declare Box2D Bodies.
 * 
 * @author acoppes
 */
public class BodyBuilder {

	private BodyDef bodyDef;
	private ArrayList<FixtureDef> fixtureDefs;
	private float mass = 1f;
	private Object userData = null;
	private Vector2 position = new Vector2();
	private final World world;
	private float angle;

	public class FixtureBuilder {

		FixtureDef fixtureDef;

		Body body;

		void setBody(Body body) {
			this.body = body;
		}

		public FixtureBuilder() {
			reset();
		}

		public FixtureBuilder sensor() {
			fixtureDef.isSensor = true;
			return this;
		}

		public FixtureBuilder boxShape(float hx, float hy) {
			PolygonShape shape = new PolygonShape();
			shape.setAsBox(hx, hy);
			fixtureDef.shape = shape;
			return this;
		}

		public FixtureBuilder circleShape(float radius) {
			Shape shape = new CircleShape();
			shape.setRadius(radius);
			fixtureDef.shape = shape;
			return this;
		}

		public FixtureBuilder polygonShape(Vector2[] vertices) {
			PolygonShape shape = new PolygonShape();
			shape.set(vertices);
			fixtureDef.shape = shape;
			return this;
		}

		public FixtureBuilder density(float density) {
			fixtureDef.density = density;
			return this;
		}

		public FixtureBuilder friction(float friction) {
			fixtureDef.friction = friction;
			return this;
		}

		public FixtureBuilder restitution(float restitution) {
			fixtureDef.restitution = restitution;
			return this;
		}

		public FixtureBuilder categoryBits(short categoryBits) {
			fixtureDef.filter.categoryBits = categoryBits;
			return this;
		}

		public FixtureBuilder maskBits(short maskBits) {
			fixtureDef.filter.maskBits = maskBits;
			return this;
		}

		private void reset() {
			fixtureDef = new FixtureDef();
		}

		public Fixture build() {
			Fixture fixture = body.createFixture(fixtureDef);
			reset();
			return fixture;
		}

	}

	FixtureBuilder fixtureBuilder;
	
	FixtureDefBuilder fixtureDefBuilder;

	public FixtureBuilder fixtureBuilder(Body body) {
		fixtureBuilder.setBody(body);
		return fixtureBuilder;
	}

	public BodyBuilder(World world) {
		this.world = world;
		this.fixtureBuilder = new FixtureBuilder();
		this.fixtureDefBuilder = new FixtureDefBuilder();
		reset();
	}
	
	public FixtureDefBuilder fixtureDefBuilder() {
		return fixtureDefBuilder;
	}

	private void reset() {

		if (fixtureDefs != null) {
			for (int i = 0; i < fixtureDefs.size(); i++) {
				FixtureDef fixtureDef = fixtureDefs.get(i);
				fixtureDef.shape.dispose();
			}
		}

		bodyDef = new BodyDef();
		fixtureDefs = new ArrayList<FixtureDef>();
		mass = 1f;
		angle = 0f;
		userData = null;
		position.set(0f, 0f);
	}

	public BodyBuilder type(BodyType type) {
		bodyDef.type = type;
		return this;
	}

	public BodyBuilder bullet() {
		bodyDef.bullet = true;
		return this;
	}

	public BodyBuilder fixedRotation() {
		bodyDef.fixedRotation = true;
		return this;
	}

	public BodyBuilder fixture(FixtureDefBuilder fixtureDef) {
		fixtureDefs.add(fixtureDef.build());
		return this;
	}
	
	public BodyBuilder fixture(FixtureDef fixtureDef) {
		fixtureDefs.add(fixtureDef);
		return this;
	}
	
	public BodyBuilder fixtures(FixtureDef[] fixtureDefs) {
		for (int i = 0; i < fixtureDefs.length; i++) 
			this.fixtureDefs.add(fixtureDefs[i]);
		return this;
	}
	
	public BodyBuilder sensor() {
		getFixture().isSensor = true;
		return this;
	}

	private FixtureDef getFixture() {
		if (fixtureDefs.size() == 0)
			fixtureDefs.add(new FixtureDef());
		return fixtureDefs.get(0);
	}

	public BodyBuilder boxShape(float hx, float hy) {
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(hx, hy);
		getFixture().shape = shape;
		return this;
	}

	public BodyBuilder circleShape(float radius) {
		Shape shape = new CircleShape();
		shape.setRadius(radius);
		getFixture().shape = shape;
		return this;
	}

	public BodyBuilder polygonShape(Vector2[] vertices) {
		PolygonShape shape = new PolygonShape();
		shape.set(vertices);
		getFixture().shape = shape;
		return this;
	}

	public BodyBuilder mass(float mass) {
		this.mass = mass;
		return this;
	}

	public BodyBuilder density(float density) {
		getFixture().density = density;
		return this;
	}

	public BodyBuilder friction(float friction) {
		getFixture().friction = friction;
		return this;
	}

	public BodyBuilder restitution(float restitution) {
		getFixture().restitution = restitution;
		return this;
	}

	public BodyBuilder categoryBits(short categoryBits) {
		getFixture().filter.categoryBits = categoryBits;
		return this;
	}

	public BodyBuilder maskBits(short maskBits) {
		getFixture().filter.maskBits = maskBits;
		return this;
	}

	public BodyBuilder userData(Object userData) {
		this.userData = userData;
		return this;
	}

	public BodyBuilder position(float x, float y) {
		this.position.set(x, y);
		return this;
	}

	public BodyBuilder angle(float angle) {
		this.angle = angle;
		return this;
	}

	public Body build() {
		Body body = world.createBody(bodyDef);

		for (int i = 0; i < fixtureDefs.size(); i++) {
			FixtureDef fixtureDef = fixtureDefs.get(i);
			body.createFixture(fixtureDef);
		}

		MassData massData = body.getMassData();
		massData.mass = mass;
		body.setMassData(massData);
		body.setUserData(userData);
		body.setTransform(position, angle);
		reset();
		return body;
	}

}