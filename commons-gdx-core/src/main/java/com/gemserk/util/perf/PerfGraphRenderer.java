package com.gemserk.util.perf;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.gemserk.commons.gdx.math.MathUtils2;
import com.gemserk.util.perf.PerfLogger.PerfData;

public class PerfGraphRenderer {
	public void renderGraph(PerfLogger perfLogger, ShapeRenderer shapeRenderer, PerfLoggerEvents eventsColors) {
		PerfData perfData = perfLogger.getPerfData("deltas");
		FloatSlidingWindowArray deltas = perfData.data;
		int width = Gdx.graphics.getWidth();
		int steps = deltas.size();
		float lastY = 0;
		float stepX = ((float) width) / deltas.getWindowSize();
		shapeRenderer.setColor(Color.RED);
		shapeRenderer.begin(ShapeType.Line);
		float averageDelta = (1 / 60f + 1 / 30f) / 2;
		for (int i = 0; i < steps; i++) {
			float nextY = (deltas.get(i) / averageDelta) * Gdx.graphics.getHeight() / 2;
			float x1 = stepX * (i - 1);
			float x2 = stepX * i;
			shapeRenderer.line(x1, lastY, x2, nextY);
			lastY = nextY;
		}
		shapeRenderer.end();
		// IntSlidingWindowArray events = perfLogger.getEvents();
		// shapeRenderer.begin(ShapeType.Line);
		// for (int i = 0; i < steps; i++) {
		// int eventId = events.get(i);
		// if(eventId!=-1){
		// shapeRenderer.setColor(eventsColors.getColor(eventId));
		// shapeRenderer.line(stepX*i, 0,stepX*i, Gdx.graphics.getHeight());
		// }
		// }
		// shapeRenderer.end();
	}

	public void renderGraph2(PerfData perfData, ShapeRenderer shapeRenderer, float x, float y, float width, float height, float minValue, float maxValue, Color color, boolean bounding, float[] guidelines) {
		FloatSlidingWindowArray deltas = perfData.data;
		int steps = deltas.size();
		float lastY = y + MathUtils2.inverseLinealInterpolation(deltas.get(0), minValue, maxValue) * height;
		float stepX = ((float) width) / deltas.getWindowSize();
		
		shapeRenderer.begin(ShapeType.Line);
		if(guidelines!=null){
			shapeRenderer.setColor(Color.YELLOW);
			for (int i = 0; i < guidelines.length; i++) {
				float percentHeight = MathUtils2.inverseLinealInterpolation(guidelines[i], minValue, maxValue);
				float guidelineY = y + height * percentHeight;
				shapeRenderer.line(x, guidelineY, x + width, guidelineY);
			}
		}
		shapeRenderer.setColor(color);
		for (int i = 1; i < steps; i++) {
			float percentHeight = MathUtils2.inverseLinealInterpolation(deltas.get(i), minValue, maxValue);
			float nextY = y + height * percentHeight;
			float x1 = x + stepX * (i - 1);
			float x2 = x + stepX * i;
			shapeRenderer.line(x1, lastY, x2, nextY);
			lastY = nextY;
		}
		if (bounding) {
			shapeRenderer.line(x, y, x + width, y);
			shapeRenderer.line(x + width, y, x + width, y + height);
			shapeRenderer.line(x + width, y + height, x, y + height);
			shapeRenderer.line(x, y + height, x, y);
		}
		
	
		shapeRenderer.end();

		// IntSlidingWindowArray events = perfLogger.getEvents();
		// shapeRenderer.begin(ShapeType.Line);
		// for (int i = 0; i < steps; i++) {
		// int eventId = events.get(i);
		// if(eventId!=-1){
		// shapeRenderer.setColor(eventsColors.getColor(eventId));
		// shapeRenderer.line(stepX*i, 0,stepX*i, Gdx.graphics.getHeight());
		// }
		// }
		// shapeRenderer.end();
	}
}