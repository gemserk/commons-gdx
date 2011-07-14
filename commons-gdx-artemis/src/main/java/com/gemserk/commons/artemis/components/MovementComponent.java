package com.gemserk.commons.artemis.components;

import com.artemis.Component;
import com.badlogic.gdx.math.Vector2;

public class MovementComponent extends Component {

	private final Vector2 velocity;

	private float angularVelocity;

	public Vector2 getVelocity() {
		return velocity;
	}

	public float getAngularVelocity() {
		return angularVelocity;
	}

	public void setAngularVelocity(float angularVelocity) {
		this.angularVelocity = angularVelocity;
	}

	public MovementComponent(Vector2 velocity, float angularVelocity) {
		this.velocity = velocity;
		this.angularVelocity = angularVelocity;
	}

}
