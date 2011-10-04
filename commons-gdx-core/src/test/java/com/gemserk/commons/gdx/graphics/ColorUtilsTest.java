package com.gemserk.commons.gdx.graphics;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.badlogic.gdx.graphics.Color;

public class ColorUtilsTest {

	@Test
	public void testConvertRed() {
		Color color = new Color();
		int rgba8888 = Color.rgba8888(1f, 0f, 0f, 0f);
		ColorUtils.rgba8888ToColor(color, rgba8888);
		assertEquals(1f, color.r, 0.01f);
		assertEquals(0f, color.g, 0.01f);
		assertEquals(0f, color.b, 0.01f);
		assertEquals(0f, color.a, 0.01f);
	}

	@Test
	public void testConvertGreen() {
		Color color = new Color();
		int rgba8888 = Color.rgba8888(0f, 1f, 0f, 0f);
		ColorUtils.rgba8888ToColor(color, rgba8888);
		assertEquals(0f, color.r, 0.01f);
		assertEquals(1f, color.g, 0.01f);
		assertEquals(0f, color.b, 0.01f);
		assertEquals(0f, color.a, 0.01f);
	}

	@Test
	public void testConvertBlue() {
		Color color = new Color();
		int rgba8888 = Color.rgba8888(0f, 0f, 1f, 0f);
		ColorUtils.rgba8888ToColor(color, rgba8888);
		assertEquals(0f, color.r, 0.01f);
		assertEquals(0f, color.g, 0.01f);
		assertEquals(1f, color.b, 0.01f);
		assertEquals(0f, color.a, 0.01f);
	}

	@Test
	public void testConvertAlpha() {
		Color color = new Color();
		int rgba8888 = Color.rgba8888(0f, 0f, 0f, 1f);
		ColorUtils.rgba8888ToColor(color, rgba8888);
		assertEquals(0f, color.r, 0.01f);
		assertEquals(0f, color.g, 0.01f);
		assertEquals(0f, color.b, 0.01f);
		assertEquals(1f, color.a, 0.01f);
	}

	@Test
	public void testConvertAllComponents() {
		Color color = new Color();
		int rgba8888 = Color.rgba8888(0.2f, 0.7f, 0.45f, 0.66f);
		ColorUtils.rgba8888ToColor(color, rgba8888);
		assertEquals(0.2f, color.r, 0.01f);
		assertEquals(0.7f, color.g, 0.01f);
		assertEquals(0.45f, color.b, 0.01f);
		assertEquals(0.66f, color.a, 0.01f);
	}
	
	@Test
	public void testConvertRgb888() {
		Color color = new Color();
		int rgb888 = Color.rgb888(0.2f, 0.7f, 0.45f);
		ColorUtils.rgba888ToColor(color, rgb888);
		assertEquals(0.2f, color.r, 0.01f);
		assertEquals(0.7f, color.g, 0.01f);
		assertEquals(0.45f, color.b, 0.01f);
	}

}
