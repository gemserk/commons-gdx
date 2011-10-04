package com.gemserk.commons.gdx.graphics;

import com.badlogic.gdx.graphics.Color;

public class ColorUtils {

	/**
	 * Sets the Color components using the int value in the format RGBA8888.
	 * 
	 * @param color
	 *            The Color to be modified.
	 * @param value
	 *            The value in RGBA8888 format.
	 */
	public static void rgba8888ToColor(Color color, int value) {
		color.r = ((value & 0xff000000) >>> 24) / 255f;
		color.g = ((value & 0x00ff0000) >>> 16) / 255f;
		color.b = ((value & 0x0000ff00) >>> 8) / 255f;
		color.a = ((value & 0x000000ff)) / 255f;
	}
	
	
//	public static int rgb565 (float r, float g, float b) {
//		return ((int)(r * 31) << 11) | ((int)(g * 63) << 5) | (int)(b * 31);
//	}
//
//	public static int rgba4444 (float r, float g, float b, float a) {
//		return ((int)(r * 15) << 12) | ((int)(g * 15) << 8) | ((int)(b * 15) << 4) | (int)(a * 15);
//	}
//
	
	/**
	 * Sets the Color components using the int value in the format RGBA888.
	 * 
	 * @param color
	 *            The Color to be modified.
	 * @param value
	 *            The value in RGBA888 format.
	 */
	public static void rgba888ToColor(Color color, int value) {
		color.r = ((value & 0x00ff0000) >>> 16) / 255f;
		color.g = ((value & 0x0000ff00) >>> 8) / 255f;
		color.b = ((value & 0x000000ff)) / 255f;
	}

}
