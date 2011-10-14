package com.gemserk.commons.artemis.components;

import com.artemis.Component;
import com.badlogic.gdx.math.Vector2;
import com.gemserk.commons.gdx.games.Movement;

public class MovementComponent extends Component {

	private Movement movement;
	
	public Movement getMovement() {
		return movement;
	}
	
	public Vector2 getVelocity() {
		return movement.getLinearVelocity();
	}

	public float getAngularVelocity() {
		return movement.getAngularVelocity();
	}

	public void setAngularVelocity(float angularVelocity) {
		movement.setAngularVelocity(angularVelocity);
	}

	public MovementComponent(Vector2 velocity, float angularVelocity) {
		movement = new Movement();
		movement.setLinearVelocity(velocity.x, velocity.y);
		movement.setAngularVelocity(angularVelocity);
	}
	
	public MovementComponent(float vx, float vy, float angularVelocity) {
		movement = new Movement();
		movement.setLinearVelocity(vx, vy);
		movement.setAngularVelocity(angularVelocity);
	}
	
	public MovementComponent(Movement movement) {
		this.movement = movement;
	}

}
