package com.gemserk.commons.svg.inkscape;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

import com.gemserk.vecmath.Matrix3f;

public class SvgInkscapeUtilsTest {

	@Test
	public void shouldParseScaleTransformWithTwoValues() {
		String transformAttribute = "   scale  (2, -3)  ";

		Matrix3f matrix = new Matrix3f();
		matrix.setIdentity();
		SvgInkscapeUtils.parseTransformAttribute(transformAttribute, matrix);

		Matrix3f expectedMatrix = new Matrix3f(new float[] { 2, 0, 0, 0, -3, 0, 0, 0, 1 });
		
		assertThat(matrix, IsEqual.equalTo(expectedMatrix));
	}
	
	@Test
	public void shouldParseScaleTransformWithTwoValues2() {
		String transformAttribute = "scale(-1, 1)";

		Matrix3f matrix = new Matrix3f();
		matrix.setIdentity();
		SvgInkscapeUtils.parseTransformAttribute(transformAttribute, matrix);

		Matrix3f expectedMatrix = new Matrix3f(new float[] { -1, 0, 0, 0, 1, 0, 0, 0, 1 });
		
		assertThat(matrix, IsEqual.equalTo(expectedMatrix));
	}

	@Test
	public void shouldParseScaleTransformWithOneValues() {
		String transformAttribute = "   scale  (2)  ";

		Matrix3f matrix = new Matrix3f();
		matrix.setIdentity();
		SvgInkscapeUtils.parseTransformAttribute(transformAttribute, matrix);

		Matrix3f expectedMatrix = new Matrix3f(new float[] { 2, 0, 0, 0, 2, 0, 0, 0, 1 });
		
		assertThat(matrix, IsEqual.equalTo(expectedMatrix));
	}
	
	@Test
	public void shouldParseAMatrixAttribute() {
		String transformAttribute = "matrix(-0.883294,-0.468819,-0.468819,0.883294,0,0)";
		Matrix3f matrix = new Matrix3f();
		matrix.setIdentity();
		SvgInkscapeUtils.parseTransformAttribute(transformAttribute, matrix);
		Matrix3f expectedMatrix = new Matrix3f(new float[] { -0.883294f, -0.468819f, 0, -0.468819f, 0.883294f, 0, 0, 0, 1 });
		assertThat(matrix, IsEqual.equalTo(expectedMatrix));
	}

	@Test
	public void shouldDetectRotateHorizontalFlip() {
		String transformAttribute = "matrix(-0.883294,-0.468819,-0.468819,0.883294,0,0)";
		Matrix3f matrix = new Matrix3f();
		matrix.setIdentity();
		SvgInkscapeUtils.parseTransformAttribute(transformAttribute, matrix);
		assertThat(SvgInkscapeUtils.isFlipped(matrix), IsEqual.equalTo(true));
	}
	
	@Test
	public void testMatrixToSvgAttribute() {
		Matrix3f matrix = new Matrix3f();
		matrix.setIdentity();
		
		matrix.m00 = 1f;
		matrix.m10 = 2f;
		matrix.m01 = 3f;
		matrix.m11 = 4f;
		matrix.m02 = 5f;
		matrix.m12 = 6f;
		
		String transformToAttribute = SvgInkscapeUtils.transformToAttribute(matrix);
		
		assertThat(transformToAttribute , IsEqual.equalTo("matrix(1.0,2.0,3.0,4.0,5.0,6.0)"));
	}
	
	// translate(-25,18.9375)
	
	@Test
	public void testParseTranslate() {
		Matrix3f matrix = new Matrix3f();
		matrix.setIdentity();
		SvgTransformUtils.parseTransform("translate(-25,18.9375)", matrix);
		System.out.println(matrix);
	}
	
}
