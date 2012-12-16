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
		Color.rgb565ToColor(color, value);
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
		Color.rgba4444ToColor(color, value);
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
		Color.rgb888ToColor(color, value);
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
		Color.rgba8888ToColor(color, value);
	}

	public static Color hexRGBToColor(String hexColor) {
		return hexRGBToColor(new Color(1f, 1f, 1f, 1f), hexColor);
	}

	public static Color hexRGBToColor(Color color, String hexColor) {
		int offset = hexColor.startsWith(hexNotationPrefix) ? 1 : 0;
		try {
			Integer red = Integer.valueOf(hexColor.substring(offset + 0, offset + 2), hexRadix);
			Integer green = Integer.valueOf(hexColor.substring(offset + 2, offset + 4), hexRadix);
			Integer blue = Integer.valueOf(hexColor.substring(offset + 4, offset + 6), hexRadix);
			color.set(red.floatValue() * intToFloat, green.floatValue() * intToFloat, blue.floatValue() * intToFloat, color.a);
			return color;
		} catch (NumberFormatException nbf) {
			throw new IllegalArgumentException("Invalid color hex string " + hexColor, nbf);
		}
	}

}
