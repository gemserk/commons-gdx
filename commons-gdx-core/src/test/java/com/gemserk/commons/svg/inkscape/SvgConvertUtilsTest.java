package com.gemserk.commons.svg.inkscape;

import org.junit.Assert;
import org.junit.Test;

import com.gemserk.vecmath.Vector2f;

public class SvgConvertUtilsTest {

	private void assertEquals(float x, float y, Vector2f actual) {
		Assert.assertEquals(x, actual.x, 0.01f);
		Assert.assertEquals(y, actual.y, 0.01f);
	}

	@Test
	public void testPathDataConversionUsingRelativeMoveTo() {
		String pathWithRelativeMove = "m 28.0,258.0 668.0,0 0,347.0 -668.0,0 z";
		Vector2f[] points = SvgConvertUtils.pathDataToVector2fList(pathWithRelativeMove);

		assertEquals(28f, 258f, points[0]);
		assertEquals(28f + 668f, 258f, points[1]);
		assertEquals(28f + 668f, 258f + 347f, points[2]);
		assertEquals(28f + 668f - 668f, 258f + 347f, points[3]);
	}

	@Test
	public void testPathDataConversionUsingAbsoluteMoveTo() {
		String pathWithRelativeMove = "M 28.0,258.0 668.0,0 0,347.0 -668.0,0 z";
		Vector2f[] points = SvgConvertUtils.pathDataToVector2fList(pathWithRelativeMove);

		assertEquals(28f, 258f, points[0]);
		assertEquals(668f, 0f, points[1]);
		assertEquals(0f, 347f, points[2]);
		assertEquals(-668f, 0f, points[3]);
	}
	
	@Test
	public void testComplexPathWithLinesAndMoveTo() {
		String pathWithRelativeMove = "M 100 100 L 300 100 L 200 300 z";
		Vector2f[] points = SvgConvertUtils.pathDataToVector2fList(pathWithRelativeMove);

		assertEquals(100f, 100f, points[0]);
		assertEquals(300f, 100f, points[1]);
		assertEquals(200f, 300f, points[2]);
	}
}
