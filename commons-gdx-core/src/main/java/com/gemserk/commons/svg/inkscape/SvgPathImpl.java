package com.gemserk.commons.svg.inkscape;

import com.gemserk.vecmath.Vector2f;


public class SvgPathImpl implements SvgInkscapePath {

	String id;
	Vector2f[] points;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Vector2f[] getPoints() {
		return points;
	}

	public void setPoints(Vector2f[] points) {
		this.points = points;
	}

}