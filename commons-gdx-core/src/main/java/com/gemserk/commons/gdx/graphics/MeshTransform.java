package com.gemserk.commons.gdx.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.math.Vector2;

public class MeshTransform {

	private float[] vertices;
	private Mesh mesh;

	private float[] transformedVertices;

	private boolean dirty;

	private float x, y;
	private float sx, sy;
	private float angle;

	private final Color color = new Color(1f, 1f, 1f, 1f);

	private final Vector2 tmp = new Vector2();
	private int vertexSize;
	private int colorOffset;

	public Mesh getMesh() {
		return mesh;
	}

	public float getAngle() {
		return angle;
	}
	
	public boolean isVisible() {
		return color.a > 0f;
	}

	public void setColor(float r, float g, float b, float a) {
		color.set(r, g, b, a);
		dirty = true;
	}

	public void setColor(Color color) {
		this.color.set(color);
		dirty = true;
	}

	public MeshTransform(Mesh mesh, float[] vertices) {
		this.mesh = mesh;
		this.vertices = vertices;

		VertexAttributes vertexAttributes = mesh.getVertexAttributes();
		vertexSize = vertexAttributes.vertexSize / 4;
		
		colorOffset = Gdx2dMeshBuilder.getOffset(vertexAttributes, Usage.ColorPacked);
		this.transformedVertices = new float[mesh.getNumVertices() * vertexSize];
	}

	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
		dirty = true;
	}

	public void setScale(float sx, float sy) {
		this.sx = sx;
		this.sy = sy;
		dirty = true;
	}

	public void setAngle(float angle) {
		this.angle = angle;
		dirty = true;
	}

	public void update() {
		if (!dirty)
			return;
		mesh.getVertices(transformedVertices);
		int idx = 0;
		for (int i = 0; i < vertices.length; i += 2) {
			tmp.set(vertices[i], vertices[i + 1]).mul(sx, sy).rotate(angle).add(x, y);
			transformedVertices[idx] = tmp.x;
			transformedVertices[idx + 1] = tmp.y;
			transformedVertices[idx + 2] = 0f;

			transformedVertices[idx + colorOffset] = Color.toFloatBits(color.r, color.g, color.b, color.a);

			idx += vertexSize;
		}
		mesh.setVertices(transformedVertices);
		dirty = false;
	}

}