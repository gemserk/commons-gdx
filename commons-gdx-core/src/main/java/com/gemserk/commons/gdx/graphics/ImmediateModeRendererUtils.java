package com.gemserk.commons.gdx.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer;
import com.badlogic.gdx.math.Vector2;

public class ImmediateModeRendererUtils {

	private static final ImmediateModeRenderer renderer = new ImmediateModeRenderer();

	private static final Vector2 tmp = new Vector2();

	public static void drawSolidCircle(Vector2 center, float radius, Vector2 axis, Color color) {

		renderer.begin(GL10.GL_LINE_LOOP);
		{
			float angle = 0;
			float angleInc = 2 * (float) Math.PI / 20;
			for (int i = 0; i < 20; i++, angle += angleInc) {
				tmp.set((float) Math.cos(angle) * radius + center.x, (float) Math.sin(angle) * radius + center.y);
				renderer.color(color.r, color.g, color.b, color.a);
				renderer.vertex(tmp.x, tmp.y, 0);
			}
		}
		renderer.end();

		renderer.begin(GL10.GL_LINES);
		{
			renderer.color(color.r, color.g, color.b, color.a);
			renderer.vertex(center.x, center.y, 0);
			renderer.color(color.r, color.g, color.b, color.a);
			renderer.vertex(center.x + axis.x * radius, center.y + axis.y * radius, 0);
		}
		renderer.end();
		
	}

}
