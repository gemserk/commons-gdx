package com.gemserk.commons.applications;

import java.awt.Dimension;

public class DimensionWithDensity extends Dimension {

	private static final long serialVersionUID = -289814478620142718L;

	public static final int LowDensity = 120;
	public static final int MediumDensity = 160;
	public static final int HighDensity = 240;
	public static final int ExtraHighDensity = 320;

	private int density;

	public int getDensity() {
		return density;
	}

	public void setDensity(int density) {
		this.density = density;
	}

	public float getDensityFactor() {
		return (float) getDensity() / (float) MediumDensity;
	}

	public DimensionWithDensity(int width, int height) {
		this(width, height, MediumDensity);
	}

	public DimensionWithDensity(int width, int height, int density) {
		super(width, height);
		this.density = density;
	}

}