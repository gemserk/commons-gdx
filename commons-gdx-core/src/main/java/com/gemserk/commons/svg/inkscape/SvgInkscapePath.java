package com.gemserk.commons.svg.inkscape;

import javax.vecmath.Vector2f;

public interface SvgInkscapePath extends SvgElement {

	Vector2f[] getPoints();

}