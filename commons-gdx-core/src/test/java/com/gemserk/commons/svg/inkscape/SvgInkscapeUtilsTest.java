package com.gemserk.commons.svg.inkscape;

import static org.junit.Assert.assertThat;

import javax.vecmath.Matrix3f;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

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
	
}
