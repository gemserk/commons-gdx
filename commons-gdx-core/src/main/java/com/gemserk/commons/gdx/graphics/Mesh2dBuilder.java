package com.gemserk.commons.gdx.graphics;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import com.badlogic.gdx.utils.BufferUtils;

public class Mesh2dBuilder {

	float vertices[];
	float colors[];
	float texCoords[];

	int idxVertices;
	int idxColors;
	int idxTexCoords;

	boolean hasColors;
	boolean hasTexCoords;

	public Mesh2dBuilder() {
		this(1000);
	}

	public Mesh2dBuilder(int maxCount) {
		vertices = new float[maxCount * 3];
		colors = new float[maxCount * 4];
		texCoords = new float[maxCount * 2];
		idxVertices = 0;
		idxColors = 0;
		idxTexCoords = 0;
	}

	public Mesh2dBuilder vertex(float x, float y) {
		vertices[idxVertices++] = x;
		vertices[idxVertices++] = y;
		vertices[idxVertices++] = 0;
		idxColors += 4;
		idxTexCoords += 2;
		return this;
	}

	public Mesh2dBuilder color(float r, float g, float b, float a) {
		colors[idxColors] = r;
		colors[idxColors + 1] = g;
		colors[idxColors + 2] = b;
		colors[idxColors + 3] = a;
		hasColors = true;
		return this;
	}

	public Mesh2dBuilder texCoord(float u, float v) {
		texCoords[idxTexCoords] = u;
		texCoords[idxTexCoords + 1] = v;
		hasTexCoords = true;
		return this;
	}

	public Mesh2d build() {
		Mesh2d mesh2d = new Mesh2d();

		mesh2d.setVertexArray(allocateBuffer(3 * idxVertices));
		BufferUtils.copy(vertices, mesh2d.getVertexArray(), idxVertices, 0);

		if (hasColors) {
			mesh2d.setColorArray(allocateBuffer(4 * idxColors));
			BufferUtils.copy(colors, mesh2d.getColorArray(), idxColors, 0);
		}

		if (hasTexCoords) {
			mesh2d.setTexCoordArray(allocateBuffer(2 * idxTexCoords));
			BufferUtils.copy(texCoords, mesh2d.getTexCoordArray(), idxTexCoords, 0);
		}

		idxVertices = 0;
		idxColors = 0;
		idxTexCoords = 0;

		hasColors = false;
		hasTexCoords = false;

		return mesh2d;
	}

	private FloatBuffer allocateBuffer(int numFloats) {
		ByteBuffer buffer = ByteBuffer.allocateDirect(numFloats * 4);
		buffer.order(ByteOrder.nativeOrder());
		return buffer.asFloatBuffer();
	}

}