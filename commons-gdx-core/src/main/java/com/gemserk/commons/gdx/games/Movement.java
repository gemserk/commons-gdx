package com.gemserk.commons.gdx.games;

import com.badlogic.gdx.math.Vector2;

public class Movement {
	
	private final Vector2 linearVelocity = new Vector2();
	private float angularVelocity;
	
	public Vector2 getLinearVelocity() {
		return linearVelocity;
	}
	
	public void setLinearVelocity(float x, float y) {
		this.linearVelocity.set(x, y);
	}
	
	public void setAngularVelocity(float angularVelocity) {
		this.angularVelocity = angularVelocity;
	}
	
	public float getAngularVelocity() {
		return angularVelocity;
	}

}
