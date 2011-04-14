package com.gemserk.commons.artemis;

import com.artemis.Component;

public class Transform extends Component {

	private float x;

	private float y;

	private float rotation;

	public Transform() {
	}

	public Transform(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public Transform(float x, float y, float rotation) {
		this(x, y);
		this.rotation = rotation;
	}

	public void addX(float x) {
		this.x += x;
	}

	public void addY(float y) {
		this.y += y;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void setLocation(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public float getRotation() {
		return rotation;
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

	public void addRotation(float angle) {
		rotation = (rotation + angle) % 360;
	}

	public float getRotationAsRadians() {
		return (float) Math.toRadians(rotation);
	}

}