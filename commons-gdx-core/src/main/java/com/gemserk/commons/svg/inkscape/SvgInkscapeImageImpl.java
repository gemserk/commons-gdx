package com.gemserk.commons.svg.inkscape;

import com.gemserk.vecmath.Matrix3f;

public class SvgInkscapeImageImpl implements SvgInkscapeImage {

	String label;

	SvgImage svgImage;

	public SvgInkscapeImageImpl(SvgImage svgImage) {
		this.svgImage = svgImage;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getId() {
		return svgImage.getId();
	}

	public float getX() {
		return svgImage.getX();
	}

	public float getY() {
		return svgImage.getY();
	}

	public float getWidth() {
		return svgImage.getWidth();
	}

	public float getHeight() {
		return svgImage.getHeight();
	}

	public Matrix3f getTransform() {
		return svgImage.getTransform();
	}

}