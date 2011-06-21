package com.gemserk.commons.svg.inkscape;

import com.gemserk.vecmath.Vector2f;


public class SvgInkscapePathImpl implements SvgInkscapePath {

	String id;

	String label;

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

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}