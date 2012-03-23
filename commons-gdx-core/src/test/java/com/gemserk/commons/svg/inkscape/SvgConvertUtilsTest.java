package com.gemserk.commons.svg.inkscape;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Assert;
import org.junit.Test;

import com.badlogic.gdx.graphics.Color;
import com.gemserk.vecmath.Vector2f;

public class SvgConvertUtilsTest {

	private void assertVectorEquals(float x, float y, Vector2f actual) {
		Assert.assertEquals(x, actual.x, 0.01f);
		Assert.assertEquals(y, actual.y, 0.01f);
	}
	
	private void assertColorEquals(float r, float g, float b, float a, Color color) {
		assertNotNull(color);
		Assert.assertEquals(r, color.r, 0.01f);
		Assert.assertEquals(g, color.g, 0.01f);		
		Assert.assertEquals(b, color.b, 0.01f);
		Assert.assertEquals(a, color.a, 0.01f);		
	}

	@Test
	public void testPathDataConversionUsingRelativeMoveTo() {
		String pathWithRelativeMove = "m 28.0,258.0 668.0,0 0,347.0 -668.0,0 z";
		Vector2f[] points = SvgConvertUtils.pathDataToVector2fList(pathWithRelativeMove);

		assertVectorEquals(28f, 258f, points[0]);
		assertVectorEquals(28f + 668f, 258f, points[1]);
		assertVectorEquals(28f + 668f, 258f + 347f, points[2]);
		assertVectorEquals(28f + 668f - 668f, 258f + 347f, points[3]);
	}

	@Test
	public void testPathDataConversionUsingAbsoluteMoveTo() {
		String pathWithRelativeMove = "M 28.0,258.0 668.0,0 0,347.0 -668.0,0 z";
		Vector2f[] points = SvgConvertUtils.pathDataToVector2fList(pathWithRelativeMove);

		assertVectorEquals(28f, 258f, points[0]);
		assertVectorEquals(668f, 0f, points[1]);
		assertVectorEquals(0f, 347f, points[2]);
		assertVectorEquals(-668f, 0f, points[3]);
	}
	
	@Test
	public void testComplexPathWithLinesAndMoveTo() {
		String pathWithRelativeMove = "M 100 100 L 300 100 L 200 300 z";
		Vector2f[] points = SvgConvertUtils.pathDataToVector2fList(pathWithRelativeMove);

		assertVectorEquals(100f, 100f, points[0]);
		assertVectorEquals(300f, 100f, points[1]);
		assertVectorEquals(200f, 300f, points[2]);
	}
	
	@Test
	public void naada(){ 
		
		Integer v = Integer.valueOf("FFF", 16);
		System.out.println(v);
		
		Color color = new Color();
		Color.rgb888ToColor(color, v);
		
		System.out.println(color);
		
	}
	
	@Test
	public void testReplicateHexNotation() {
		String replicateHexNotation = SvgConvertUtils.replicateHexNotation("FFF");
		assertEquals("FFFFFF", replicateHexNotation);
	}
	
	@Test
	public void testColorValue() {
		Color color = SvgConvertUtils.colorValue("#FFF");
		assertColorEquals(1f, 1f, 1f, 0f, color);
	}

	@Test
	public void testColorValue2() {
		Color color = SvgConvertUtils.colorValue("#FFFFFF");
		assertColorEquals(1f, 1f, 1f, 0f, color);
	}
	
	@Test
	public void testColorFromStyle() {
		Color color = SvgConvertUtils.fillColorFromStyle("fill:#ffff00;fill-opacity:0.8;display:inline");
		assertColorEquals(1f, 1f, 0f, 0.8f, color);
	}
	
	@Test
	public void testColorFromStyle2() {
		Color color = new Color(0f, 0f, 0f, 1f);
		SvgConvertUtils.fillColorFromStyle(color, "display:inline;fill:#00ff00");
		assertColorEquals(0f, 1f, 0f, 1f, color);
	}
	
}
