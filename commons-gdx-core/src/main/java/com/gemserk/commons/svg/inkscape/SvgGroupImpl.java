package com.gemserk.commons.svg.inkscape;

import com.gemserk.vecmath.Matrix3f;

public class SvgGroupImpl implements SvgGroup {
	
	String id;
	Matrix3f transform;
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setTransform(Matrix3f transform) {
		this.transform = transform;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public Matrix3f getTransform() {
		return transform;
	}

}
