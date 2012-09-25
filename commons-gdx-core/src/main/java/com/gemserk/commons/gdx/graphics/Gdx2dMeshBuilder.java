package com.gemserk.commons.gdx.graphics;

/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Array;

/**
 * Libgdx mesh builder</p> *
 * 
 * code based on ImmediateModeRenderer20 from libgdx
 * 
 */
public class Gdx2dMeshBuilder {
	private int vertexIdx;
	private int numSetTexCoords;
	private final int maxVertices;
	private int numVertices;

	private final int vertexSize;
	private final int normalOffset;
	private final int colorOffset;
	private final int texCoordOffset;
	private final float[] vertices;
	private VertexAttributes vertexAttributes;

	public Gdx2dMeshBuilder(boolean hasNormals, boolean hasColors, int numTexCoords) {
		this(5000, hasNormals, hasColors, numTexCoords);
	}

	public Gdx2dMeshBuilder(int maxVertices, boolean hasNormals, boolean hasColors, int numTexCoords) {
		this.maxVertices = maxVertices;

		VertexAttribute[] attribs = buildVertexAttributes(hasNormals, hasColors, numTexCoords);
		vertexAttributes = new VertexAttributes(attribs);

		vertexSize = vertexAttributes.vertexSize / 4;

		vertices = new float[maxVertices * vertexSize];

		normalOffset = getOffset(vertexAttributes, Usage.Normal);
		colorOffset = getOffset(vertexAttributes, Usage.ColorPacked);
		texCoordOffset = getOffset(vertexAttributes, Usage.TextureCoordinates);
	}

	public static int getOffset(VertexAttributes vertexAttributes, int usage) {
		int len = vertexAttributes.size();
		for (int i = 0; i < len; i++)
			if (vertexAttributes.get(i).usage == usage)
				return vertexAttributes.get(i).offset / 4;
		return 0;
	}

	private VertexAttribute[] buildVertexAttributes(boolean hasNormals, boolean hasColor, int numTexCoords) {
		Array<VertexAttribute> attribs = new Array<VertexAttribute>();
		attribs.add(new VertexAttribute(Usage.Position, 3, ShaderProgram.POSITION_ATTRIBUTE));
		if (hasNormals)
			attribs.add(new VertexAttribute(Usage.Normal, 3, ShaderProgram.NORMAL_ATTRIBUTE));
		if (hasColor)
			attribs.add(new VertexAttribute(Usage.ColorPacked, 4, ShaderProgram.COLOR_ATTRIBUTE));
		for (int i = 0; i < numTexCoords; i++) {
			attribs.add(new VertexAttribute(Usage.TextureCoordinates, 2, ShaderProgram.TEXCOORD_ATTRIBUTE + i));
		}
		VertexAttribute[] array = new VertexAttribute[attribs.size];
		for (int i = 0; i < attribs.size; i++)
			array[i] = attribs.get(i);
		return array;
	}

	public Gdx2dMeshBuilder color(float r, float g, float b, float a) {
		vertices[vertexIdx + colorOffset] = Color.toFloatBits(r, g, b, a);
		return this;
	}
	
	public Gdx2dMeshBuilder color(Color color) {
		vertices[vertexIdx + colorOffset] = Color.toFloatBits(color.r, color.g, color.b, color.a);
		return this;
	}

	public Gdx2dMeshBuilder texCoord(float u, float v) {
		final int idx = vertexIdx + texCoordOffset + numSetTexCoords;
		vertices[idx] = u;
		vertices[idx + 1] = v;
		numSetTexCoords += 2;
		return this;
	}

	public Gdx2dMeshBuilder normal(float x, float y, float z) {
		final int idx = vertexIdx + normalOffset;
		vertices[idx] = x;
		vertices[idx + 1] = y;
		vertices[idx + 2] = z;
		return this;
	}

	public Gdx2dMeshBuilder vertex(float x, float y, float z) {
		final int idx = vertexIdx;
		vertices[idx] = x;
		vertices[idx + 1] = y;
		vertices[idx + 2] = z;

		numSetTexCoords = 0;
		vertexIdx += vertexSize;
		numVertices++;
		return this;
	}

	public Gdx2dMeshBuilder vertex(float x, float y) {
		vertex(x, y, 0);
		return this;
	}

	public Mesh build() {

		Mesh mesh = new Mesh(true, numVertices, 0, vertexAttributes);

		float realData[] = new float[numVertices * vertexSize];

		System.arraycopy(vertices, 0, realData, 0, realData.length);
		mesh.setVertices(realData);

		numSetTexCoords = 0;
		vertexIdx = 0;
		numVertices = 0;

		return mesh;
	}

	public int getNumVertices() {
		return numVertices;
	}

	public int getMaxVertices() {
		return maxVertices;
	}
}
