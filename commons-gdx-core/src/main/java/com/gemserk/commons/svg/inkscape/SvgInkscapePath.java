package com.gemserk.commons.svg.inkscape;

import com.gemserk.vecmath.Vector2f;


public interface SvgInkscapePath extends SvgElement {

	Vector2f[] getPoints();
	
	String getLabel();

}