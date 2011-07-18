package com.gemserk.commons.gdx.graphics;

import java.nio.FloatBuffer;

public class Mesh2d {

	private FloatBuffer vertexArray, colorArray, texCoordArray;

	public void setVertexArray(FloatBuffer vertexArray) {
		this.vertexArray = vertexArray;
	}

	public FloatBuffer getVertexArray() {
		return vertexArray;
	}

	public void setColorArray(FloatBuffer colorArray) {
		this.colorArray = colorArray;
	}

	public FloatBuffer getColorArray() {
		return colorArray;
	}

	public void setTexCoordArray(FloatBuffer texCoordArray) {
		this.texCoordArray = texCoordArray;
	}

	public FloatBuffer getTexCoordArray() {
		return texCoordArray;
	}

}