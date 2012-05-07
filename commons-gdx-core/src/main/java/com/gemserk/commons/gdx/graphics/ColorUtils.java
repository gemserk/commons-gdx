package com.gemserk.commons.gdx.graphics;

import com.badlogic.gdx.graphics.Color;

public class ColorUtils {

	private static final float intToFloat = 1f / 256f;
	private static final String hexNotationPrefix = "#";
	private static final int hexRadix = 16;

	/**
	 * Sets the Color components using the specified integer value in the format RGB565. This is inverse to the rgb565(r, g, b) method.
	 * 
	 * @param color
	 *            The Color to be modified.
	 * @param value
	 *            An integer color value in RGB565 format.
	 */
	public static void rgb565ToColor(Color color, int value) {
		color.r = ((value & 0x0000F800) >>> 11) / 31f;
		color.g = ((value & 0x000007E0) >>> 5) / 63f;
		color.b = ((value & 0x0000001F) >>> 0) / 31f;
	}

	/**
	 * Sets the Color components using the specified integer value in the format RGBA4444. This is inverse to the rgba4444(r, g, b, a) method.
	 * 
	 * @param color
	 *            The Color to be modified.
	 * @param value
	 *            An integer color value in RGBA4444 format.
	 */
	public static void rgba4444ToColor(Color color, int value) {
		color.r = ((value & 0x0000f000) >>> 12) / 15f;
		color.g = ((value & 0x00000f00) >>> 8) / 15f;
		color.b = ((value & 0x000000f0) >>> 4) / 15f;
		color.a = ((value & 0x0000000f)) / 15f;
	}

	/**
	 * Sets the Color components using the specified integer value in the format RGB888. This is inverse to the rgb888(r, g, b) method.
	 * 
	 * @param color
	 *            The Color to be modified.
	 * @param value
	 *            An integer color value in RGB888 format.
	 */
	public static void rgb888ToColor(Color color, int value) {
		color.r = ((value & 0x00ff0000) >>> 16) / 255f;
		color.g = ((value & 0x0000ff00) >>> 8) / 255f;
		color.b = ((value & 0x000000ff)) / 255f;
	}

	/**
	 * Sets the Color components using the specified integer value in the format RGBA8888. This is inverse to the rgb8888(r, g, b, a) method.
	 * 
	 * @param color
	 *            The Color to be modified.
	 * @param value
	 *            An integer color value in RGBA8888 format.
	 */
	public static void rgba8888ToColor(Color color, int value) {
		color.r = ((value & 0xff000000) >>> 24) / 255f;
		color.g = ((value & 0x00ff0000) >>> 16) / 255f;
		color.b = ((value & 0x0000ff00) >>> 8) / 255f;
		color.a = ((value & 0x000000ff)) / 255f;
	}

	public static Color hexRGBToColor(Color color, String string) {
		if (string.startsWith(hexNotationPrefix)) {
			try {
				Integer red = Integer.valueOf(string.substring(1, 3), hexRadix);
				Integer green = Integer.valueOf(string.substring(3, 5), hexRadix);
				Integer blue = Integer.valueOf(string.substring(5, 7), hexRadix);
				color.set(red.floatValue() * intToFloat, green.floatValue() * intToFloat, blue.floatValue() * intToFloat, color.a);
				return color;
			} catch (NumberFormatException nbf) {
				throw new IllegalArgumentException("Invalid color hex string " + string, nbf);
			}
		}
		throw new IllegalArgumentException("Invalid color hex string " + string);
	}

}
