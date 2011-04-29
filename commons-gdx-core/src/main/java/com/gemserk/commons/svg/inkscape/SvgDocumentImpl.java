package com.gemserk.commons.svg.inkscape;

public class SvgDocumentImpl implements SvgDocument {

	String id;

	float width;

	float height;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

}