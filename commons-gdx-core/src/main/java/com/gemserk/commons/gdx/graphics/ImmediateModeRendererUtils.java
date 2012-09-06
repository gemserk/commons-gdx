package com.gemserk.commons.gdx.graphics;

import java.nio.FloatBuffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class ImmediateModeRendererUtils {

	private static ImmediateModeRenderer renderer;

	private static ImmediateModeRenderer getRenderer() {
		// if (renderer == null)
		// renderer = Gdx.graphics.isGL20Available() ? new ImmediateModeRenderer20(false, true, 0) : new ImmediateModeRenderer10();
		return renderer;
	}

	private static final Vector2 tmp = new Vector2();
	private static final Vector2 angleTmp = new Vector2(1, 0);
	private static Matrix4 projectionMatrix = null;

	public static void setRenderer(ImmediateModeRenderer renderer) {
		ImmediateModeRendererUtils.renderer = renderer;
	}

	public static void disposeRenderer() {
		renderer.dispose();
	}

	public static void calculateProjectionMatrix(float x, float y, float zoom, float w, float h) {
		getProjectionMatrix().setToOrtho2D(x, y, w / zoom, h / zoom);
	}

	public static Matrix4 getProjectionMatrix() {
		if (projectionMatrix == null) {
			projectionMatrix = new Matrix4();
			// don't like the Gdx.graphics here
			projectionMatrix.setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		}
		return projectionMatrix;
	}

	public static void drawSolidCircle(Circle circle, float angle, Color color) {
		drawSolidCircle(circle.x, circle.y, circle.radius, angle, color);
	}

	public static void drawSolidCircle(Vector2 center, float radius, float axisAngle, Color color) {
		drawSolidCircle(center.x, center.y, radius, axisAngle, color);
	}

	public static void drawSolidCircle(float x, float y, float radius, float axisAngle, Color color) {
		angleTmp.set(1, 0);
		angleTmp.rotate(axisAngle);
		drawSolidCircle(x, y, radius, angleTmp, color);
	}

	public static void drawSolidCircle(Vector2 center, float radius, Vector2 axis, Color color) {
		drawSolidCircle(center.x, center.y, radius, axis, color);
	}

	public static void drawSolidCircle(float x, float y, float radius, Vector2 axis, Color color) {
		drawSolidCircle(x, y, radius, color);
		drawLine(x, y, x + axis.x * radius, y + axis.y * radius, color);
	}

	public static void drawSolidCircle(Vector2 center, float radius, Color color) {
		drawSolidCircle(center.x, center.y, radius, color);
	}

	public static void drawSolidCircle(float x, float y, float radius, Color color) {
		ImmediateModeRenderer renderer = getRenderer();
		renderer.begin(getProjectionMatrix(), GL10.GL_LINE_LOOP);
		{
			float angle = 0;
			float angleInc = 2 * (float) Math.PI / 20;
			for (int i = 0; i < 20; i++, angle += angleInc) {
				tmp.set((float) Math.cos(angle) * radius + x, (float) Math.sin(angle) * radius + y);
				renderer.color(color.r, color.g, color.b, color.a);
				renderer.vertex(tmp.x, tmp.y, 0);
			}
		}
		renderer.end();
	}

	public static void drawLine(Vector2 p0, Vector2 p1, Color color) {
		drawLine(p0.x, p0.y, p1.x, p1.y, color);
	}

	public static void drawLine(float x0, float y0, float x1, float y1, Color color) {
		ImmediateModeRenderer renderer = getRenderer();
		renderer.begin(getProjectionMatrix(), GL10.GL_LINES);
		{
			renderer.color(color.r, color.g, color.b, color.a);
			renderer.vertex(x0, y0, 0);
			renderer.color(color.r, color.g, color.b, color.a);
			renderer.vertex(x1, y1, 0);
		}
		renderer.end();
	}

	public static void drawHorizontalAxis(float y, Color color) {
		drawHorizontalAxis(y, 10000, color);
	}

	public static void drawVerticalAxis(float x, Color color) {
		drawVerticalAxis(x, 10000, color);
	}

	public static void drawHorizontalAxis(float y, float length, Color color) {
		drawLine(-length, y, length, y, color);
	}

	public static void drawVerticalAxis(float x, float length, Color color) {
		drawLine(x, -length, x, length, color);
	}

	public static void drawRectangle(Rectangle r, Color color) {
		drawRectangle(r, 0f, 0f, color);
	}

	public static void drawRectangle(Rectangle r, float cx, float cy, Color color) {
		float x = r.x - r.getWidth() * cx;
		float y = r.y - r.getHeight() * cy;
		drawRectangle(x, y, x + r.getWidth(), y + r.getHeight(), color);
	}

	public static void drawRectangle(float x0, float y0, float x1, float y1, Color color) {
		ImmediateModeRenderer renderer = getRenderer();
		renderer.begin(getProjectionMatrix(), GL10.GL_LINE_LOOP);
		{
			renderer.color(color.r, color.g, color.b, color.a);
			renderer.vertex(x0, y0, 0f);

			renderer.color(color.r, color.g, color.b, color.a);
			renderer.vertex(x0, y1, 0f);

			renderer.color(color.r, color.g, color.b, color.a);
			renderer.vertex(x1, y1, 0f);

			renderer.color(color.r, color.g, color.b, color.a);
			renderer.vertex(x1, y0, 0f);
		}
		renderer.end();
	}

	public static void fillRectangle(float x0, float y0, float x1, float y1, Color color) {
		ImmediateModeRenderer renderer = getRenderer();
		renderer.begin(getProjectionMatrix(), GL10.GL_TRIANGLES);
		{
			// first triangle
			renderer.color(color.r, color.g, color.b, color.a);
			renderer.vertex(x0, y0, 0f);

			renderer.color(color.r, color.g, color.b, color.a);
			renderer.vertex(x0, y1, 0f);

			renderer.color(color.r, color.g, color.b, color.a);
			renderer.vertex(x1, y1, 0f);

			// second triangle
			renderer.color(color.r, color.g, color.b, color.a);
			renderer.vertex(x1, y1, 0f);

			renderer.color(color.r, color.g, color.b, color.a);
			renderer.vertex(x1, y0, 0f);

			renderer.color(color.r, color.g, color.b, color.a);
			renderer.vertex(x0, y0, 0f);
		}
		renderer.end();
	}

	public static void drawPolygon(Vector2[] vertices, float x, float y, float angle, Color color) {
		GL10 gl = Gdx.graphics.getGL10();
		gl.glPushMatrix();

		gl.glTranslatef(x, y, 0f);
		gl.glRotatef(angle, 0f, 0f, 1f);

		ImmediateModeRenderer renderer = getRenderer();

		renderer.begin(getProjectionMatrix(), GL10.GL_LINE_LOOP);
		for (int i = 0; i < vertices.length; i++) {
			Vector2 v = vertices[i];
			renderer.color(color.r, color.g, color.b, color.a);
			renderer.vertex(v.x, v.y, 0);
		}
		renderer.end();

		gl.glPopMatrix();
	}

	public static void draw(Triangulator triangulator, float x, float y, float angle, Color color) {
		GL10 gl = Gdx.graphics.getGL10();

		gl.glPushMatrix();
		gl.glTranslatef(x, y, 0f);
		gl.glRotatef(angle, 0f, 0f, 1f);

		ImmediateModeRenderer renderer = getRenderer();

		renderer.begin(getProjectionMatrix(), GL10.GL_TRIANGLES);
		for (int i = 0; i < triangulator.getTriangleCount(); i++) {
			for (int p = 0; p < 3; p++) {
				float[] pt = triangulator.getTrianglePoint(i, p);
				renderer.color(color.r, color.g, color.b, color.a);
				renderer.vertex(pt[0], pt[1], 0f);
			}
		}
		renderer.end();

		gl.glPopMatrix();
	}

	public static void draw(int primitiveType, int count, FloatBuffer verticesBuffer) {
		Mesh2dRenderUtils.draw(primitiveType, count, verticesBuffer);
	}

	public static void draw(int primitiveType, int count, FloatBuffer verticesBuffer, FloatBuffer colorsBuffer) {
		Mesh2dRenderUtils.draw(primitiveType, count, verticesBuffer, colorsBuffer);
	}

	public static void draw(int primitiveType, FloatBuffer verticesBuffer, FloatBuffer colorsBuffer, FloatBuffer texCoordsBuffer) {
		Mesh2dRenderUtils.draw(primitiveType, verticesBuffer, colorsBuffer, texCoordsBuffer);
	}

	public static void draw(int primitiveType, Mesh2d mesh2d) {
		Mesh2dRenderUtils.draw(primitiveType, mesh2d);
	}

	public static void draw(int primitiveType, Mesh2d mesh2d, float x, float y, float angle) {
		Mesh2dRenderUtils.draw(primitiveType, mesh2d, x, y, angle);
	}

	public static void drawSpriteBounds(Sprite sprite, Color color) {
		float[] vertices = sprite.getVertices();

		float x1 = vertices[SpriteBatch.X1];
		float y1 = vertices[SpriteBatch.Y1];

		float x2 = vertices[SpriteBatch.X2];
		float y2 = vertices[SpriteBatch.Y2];

		float x3 = vertices[SpriteBatch.X3];
		float y3 = vertices[SpriteBatch.Y3];

		float x4 = vertices[SpriteBatch.X4];
		float y4 = vertices[SpriteBatch.Y4];

		drawLine(x1, y1, x2, y2, color);
		drawLine(x2, y2, x3, y3, color);
		drawLine(x3, y3, x4, y4, color);
		drawLine(x4, y4, x1, y1, color);
	}
}
