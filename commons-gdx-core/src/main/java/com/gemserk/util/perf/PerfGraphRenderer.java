package com.gemserk.util.perf;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class PerfGraphRenderer {
	public void renderGraph(PerfLogger perfLogger, ShapeRenderer shapeRenderer, PerfLoggerEvents eventsColors) {
		FloatSlidingWindowArray deltas = perfLogger.getDeltas();
		int width = Gdx.graphics.getWidth();
		int steps = deltas.size();
		float lastY = 0;
		float stepX = ((float)width)/deltas.getWindowSize();
		shapeRenderer.setColor(Color.RED);
		shapeRenderer.begin(ShapeType.Line);
		float averageDelta = perfLogger.getAverageDelta();
		for (int i = 0; i < steps; i++) {
			float nextY = (deltas.get(i)/averageDelta)*Gdx.graphics.getHeight()/2;
			float x1 = stepX * (i-1);
			float x2 = stepX * i;
			shapeRenderer.line(x1, lastY,x2, nextY );
			lastY = nextY;
		}
		shapeRenderer.end();
		IntSlidingWindowArray events = perfLogger.getEvents();	
		shapeRenderer.begin(ShapeType.Line);
		for (int i = 0; i < steps; i++) {
			int eventId = events.get(i);
			if(eventId!=-1){
				shapeRenderer.setColor(eventsColors.getColor(eventId));
				shapeRenderer.line(stepX*i, 0,stepX*i, Gdx.graphics.getHeight());
			}
		}
		shapeRenderer.end();
	}
}