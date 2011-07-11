package com.gemserk.commons.gdx.box2d;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;

public class FixtureDefBuilder {
	
	FixtureDef fixtureDef;
	
	public FixtureDefBuilder() {
		reset();
	}
	
	public FixtureDefBuilder sensor() {
		fixtureDef.isSensor = true;
		return this;
	}

	public FixtureDefBuilder boxShape(float hx, float hy) {
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(hx, hy);
		fixtureDef.shape = shape;
		return this;
	}

	public FixtureDefBuilder circleShape(float radius) {
		Shape shape = new CircleShape();
		shape.setRadius(radius);
		fixtureDef.shape = shape;
		return this;
	}

	public FixtureDefBuilder polygonShape(Vector2[] vertices) {
		PolygonShape shape = new PolygonShape();
		shape.set(vertices);
		fixtureDef.shape = shape;
		return this;
	}
	
	public FixtureDefBuilder density(float density) {
		fixtureDef.density = density;
		return this;
	}

	public FixtureDefBuilder friction(float friction) {
		fixtureDef.friction = friction;
		return this;
	}

	public FixtureDefBuilder restitution(float restitution) {
		fixtureDef.restitution = restitution;
		return this;
	}

	public FixtureDefBuilder categoryBits(short categoryBits) {
		fixtureDef.filter.categoryBits = categoryBits;
		return this;
	}

	public FixtureDefBuilder maskBits(short maskBits) {
		fixtureDef.filter.maskBits = maskBits;
		return this;
	}
	
	private void reset() {
		fixtureDef = new FixtureDef();
	}
	
	public FixtureDef build() {
		FixtureDef fixtureDef = this.fixtureDef;
		reset();
		return fixtureDef;
	}
	
}