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

}
