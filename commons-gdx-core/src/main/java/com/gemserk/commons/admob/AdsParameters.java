package com.gemserk.commons.admob;

import java.util.ArrayList;

public class AdsParameters {

	public static final int VERTICAL_TOP = 1;
	public static final int VERTICAL_BOTTOM = 2;
	public static final int VERTICAL_CENTER = 3;

	public static final int HORIZONTAL_LEFT = 1;
	public static final int HORIZONTAL_RIGHT = 2;
	public static final int HORIZONTAL_CENTER = 3;

	public int verticalAlign;
	public int horizontalAlign;
	public long delay = 0L;

	public ArrayList<AdsAnimation> animations = new ArrayList<AdsAnimation>();

	public AdsParameters verticalAlign(int vertical) {
		this.verticalAlign = vertical;
		return this;
	}

	public AdsParameters horizontalAlign(int horizontal) {
		this.horizontalAlign = horizontal;
		return this;
	}

	public AdsParameters animation(AdsAnimation adsAnimation) {
		this.animations.add(adsAnimation);
		return this;
	}

	/**
	 * The delay to show the Ads in milliseconds.
	 * 
	 * @param delay
	 *            The delay in milliseconds.
	 */
	public AdsParameters delay(long delay) {
		this.delay = delay;
		return this;
	}

}