package com.gemserk.commons.svg.inkscape;

import com.gemserk.vecmath.Vector2f;

public interface SvgPath extends SvgElement {

	Vector2f[] getPoints();
	
}