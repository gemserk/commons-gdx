package com.gemserk.commons.svg.inkscape;

import com.gemserk.vecmath.Matrix3f;

public class SvgInkscapeGroupImpl implements SvgInkscapeGroup {

	String id;

	String groupMode;

	String label;
	
	Matrix3f transform = new Matrix3f();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGroupMode() {
		return groupMode;
	}

	public void setGroupMode(String groupMode) {
		this.groupMode = groupMode;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public Matrix3f getTransform() {
		return transform;
	}

}