package com.gemserk.commons.admob;

public class AdViewLocation {

	public static final int VERTICAL_TOP = 1;
	public static final int VERTICAL_BOTTOM = 2;
	public static final int VERTICAL_CENTER = 3;

	public static final int HORIZONTAL_LEFT = 1;
	public static final int HORIZONTAL_RIGHT = 2;
	public static final int HORIZONTAL_CENTER = 3;

	public int verticalAlign;
	public int horizontalAlign;

	public AdViewLocation(int vertical, int horizontal) {
		this.verticalAlign = vertical;
		this.horizontalAlign = horizontal;
	}

}