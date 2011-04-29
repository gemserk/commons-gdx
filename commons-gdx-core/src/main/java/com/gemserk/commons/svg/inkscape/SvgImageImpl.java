package com.gemserk.commons.svg.inkscape;

import javax.vecmath.Matrix3f;

public class SvgImageImpl implements SvgImage {

	String id;

	float x, y, width, height;

	Matrix3f transform;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public Matrix3f getTransform() {
		return transform;
	}

	public void setTransform(Matrix3f transform) {
		this.transform = transform;
	}

}