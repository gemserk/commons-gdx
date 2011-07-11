package com.gemserk.commons.gdx.box2d;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
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

	FixtureDefBuilder fixtureDefBuilder;

	public BodyBuilder(World world) {
		this.world = world;
		this.fixtureDefBuilder = new FixtureDefBuilder();
		this.fixtureDefs = new ArrayList<FixtureDef>();
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
		fixtureDefs.clear();
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

	public BodyBuilder mass(float mass) {
		this.mass = mass;
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