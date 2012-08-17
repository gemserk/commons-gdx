package com.gemserk.util.perf;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Mesh.VertexDataType;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Disposable;
import com.gemserk.commons.gdx.graphics.MeshRenderer;
import com.gemserk.commons.gdx.math.MathUtils2;

public class PerfGraphRendererMesh implements Disposable {

	public Camera camera;
	Mesh mesh;
	private int vertexSize;
	private int colorOffset;
	private float[] vertices;

	public PerfGraphRendererMesh() {

		VertexAttribute[] attribs = new VertexAttribute[2];
		attribs[0] = new VertexAttribute(Usage.Position, 3, ShaderProgram.POSITION_ATTRIBUTE);
		attribs[1] = new VertexAttribute(Usage.ColorPacked, 4, ShaderProgram.COLOR_ATTRIBUTE);
		int maxVertices = 5000;
		mesh = new Mesh(VertexDataType.VertexArray, false, maxVertices, 0, attribs);

		vertexSize = mesh.getVertexAttributes().vertexSize / 4;
		vertices = new float[maxVertices * (vertexSize)];
		colorOffset = mesh.getVertexAttribute(Usage.ColorPacked) != null ? mesh.getVertexAttribute(Usage.ColorPacked).offset / 4 : 0;
	}

	public void renderGraph(MeshRenderer meshRenderer, PerfGraphRenderDefinition[] renderDefinitions) {

		int vertexIndex = 0;

		for (int renderDefinitionIndex = 0; renderDefinitionIndex < renderDefinitions.length; renderDefinitionIndex++) {
			PerfGraphRenderDefinition renderDefinition = renderDefinitions[renderDefinitionIndex];

			FloatSlidingWindowArray deltas = renderDefinition.perfData.data;
			float x = renderDefinition.x;
			float y = renderDefinition.y;
			float minValue = renderDefinition.minValue;
			float maxValue = renderDefinition.maxValue;
			float height = renderDefinition.height;
			float[] guidelines = renderDefinition.guidelines;
			float width = renderDefinition.width;

			int steps = deltas.size();
			float lastY = y + MathUtils2.inverseLinealInterpolation(deltas.get(0), minValue, maxValue) * height;
			float stepX = ((float) width) / deltas.getWindowSize();
			if (guidelines != null) {
				float colorGuidelines = renderDefinition.guidelinesColor.toFloatBits();
				for (int i = 0; i < guidelines.length; i++) {
					float percentHeight = MathUtils2.inverseLinealInterpolation(guidelines[i], minValue, maxValue);
					float guidelineY = y + height * percentHeight;

					vertices[vertexIndex + 0] = x;
					vertices[vertexIndex + 1] = guidelineY;
					vertices[vertexIndex + 2] = 0;
					vertices[vertexIndex + colorOffset] = colorGuidelines;
					vertexIndex += vertexSize;

					vertices[vertexIndex + 0] = x + width;
					vertices[vertexIndex + 1] = guidelineY;
					vertices[vertexIndex + 2] = 0;
					vertices[vertexIndex + colorOffset] = colorGuidelines;
					vertexIndex += vertexSize;
				}
			}
			float colorFloat = renderDefinition.color.toFloatBits();
			for (int i = 1; i < steps; i++) {
				float percentHeight = MathUtils2.inverseLinealInterpolation(deltas.get(i), minValue, maxValue);
				float nextY = y + height * percentHeight;
				float x1 = x + stepX * (i - 1);
				float x2 = x + stepX * i;

				vertices[vertexIndex + 0] = x1;
				vertices[vertexIndex + 1] = lastY;
				vertices[vertexIndex + 2] = 0;
				vertices[vertexIndex + colorOffset] = colorFloat;
				vertexIndex += vertexSize;

				vertices[vertexIndex + 0] = x2;
				vertices[vertexIndex + 1] = nextY;
				vertices[vertexIndex + 2] = 0;
				vertices[vertexIndex + colorOffset] = colorFloat;
				vertexIndex += vertexSize;

				lastY = nextY;
			}
			if (renderDefinition.bounding) {

				vertices[vertexIndex + 0] = x;
				vertices[vertexIndex + 1] = y;
				vertices[vertexIndex + 2] = 0;
				vertices[vertexIndex + colorOffset] = colorFloat;
				vertexIndex += vertexSize;

				vertices[vertexIndex + 0] = x + width;
				vertices[vertexIndex + 1] = y;
				vertices[vertexIndex + 2] = 0;
				vertices[vertexIndex + colorOffset] = colorFloat;
				vertexIndex += vertexSize;

				vertices[vertexIndex + 0] = x + width;
				vertices[vertexIndex + 1] = y;
				vertices[vertexIndex + 2] = 0;
				vertices[vertexIndex + colorOffset] = colorFloat;
				vertexIndex += vertexSize;

				vertices[vertexIndex + 0] = x + width;
				vertices[vertexIndex + 1] = y + height;
				vertices[vertexIndex + 2] = 0;
				vertices[vertexIndex + colorOffset] = colorFloat;
				vertexIndex += vertexSize;

				vertices[vertexIndex + 0] = x + width;
				vertices[vertexIndex + 1] = y + height;
				vertices[vertexIndex + 2] = 0;
				vertices[vertexIndex + colorOffset] = colorFloat;
				vertexIndex += vertexSize;

				vertices[vertexIndex + 0] = x;
				vertices[vertexIndex + 1] = y + height;
				vertices[vertexIndex + 2] = 0;
				vertices[vertexIndex + colorOffset] = colorFloat;
				vertexIndex += vertexSize;

				vertices[vertexIndex + 0] = x;
				vertices[vertexIndex + 1] = y + height;
				vertices[vertexIndex + 2] = 0;
				vertices[vertexIndex + colorOffset] = colorFloat;
				vertexIndex += vertexSize;

				vertices[vertexIndex + 0] = x;
				vertices[vertexIndex + 1] = y;
				vertices[vertexIndex + 2] = 0;
				vertices[vertexIndex + colorOffset] = colorFloat;
				vertexIndex += vertexSize;

			}
		}

		int cantVertices = vertexIndex / vertexSize;
		if (cantVertices == 0)
			return;

		mesh.setVertices(vertices, 0, vertexIndex);
		meshRenderer.render(camera, mesh, GL10.GL_LINES, 0, cantVertices);

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

	@Override
	public void dispose() {
		mesh.dispose();
	}
}