package com.gemserk.util.perf;

import com.badlogic.gdx.graphics.Color;
import com.gemserk.util.perf.PerfLogger.PerfData;

public class PerfGraphRenderDefinition {
	public PerfData perfData;
	public float x;
	public float y;
	public float width;
	public float height;
	public float minValue;
	public float maxValue;
	public Color color;
	public boolean bounding;
	public float[] guidelines;
	public final Color guidelinesColor;

	public PerfGraphRenderDefinition(PerfData perfData, float x, float y, float width, float height, float minValue, float maxValue, Color color, boolean bounding, float[] guidelines, Color guidelinesColor) {
		this.perfData = perfData;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.color = color;
		this.bounding = bounding;
		this.guidelines = guidelines;
		this.guidelinesColor = guidelinesColor;
	}
}