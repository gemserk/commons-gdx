package com.gemserk.commons.gdx.g2d;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.IntArray;

public class PolygonDefinition {

	private float[] texCoords;
	private float[] vertices;
	private short[] indices;

	public float[] getVertices() {
		return vertices;
	}

	public float[] getTextureCoordinates() {
		return texCoords;
	}

	public short[] getIndices() {
		return indices;
	}

	public PolygonDefinition(float[] vertices, float[] texCoords, short[] indices) {
		this.vertices = vertices;
		this.texCoords = texCoords;
		this.indices = indices;
	}

	public static int indexOf(float x, float y, float[] vertices) {
		for (int i = 0; i < vertices.length; i += 2) {
			float vx = vertices[i];
			float vy = vertices[i + 1];
			if (Float.compare(x, vx) == 0 && Float.compare(y, vy) == 0)
				return i / 2;
		}
		return -1;
	}

	private static int indexOf(int index, short[] indices) {
		for (int i = 0; i < indices.length; i++) {
			if (indices[i] == index)
				return i;
		}
		return -1;
	}

	public static int removeDuplicatedIndices(float[] vertices, float[] texCoords, short[] indices) {
		IntArray duplicatedIndices = new IntArray();
		for (int i = 0; i < vertices.length; i += 2) {
			int index = i / 2;
			if (indexOf(index, indices) == -1) {
				duplicatedIndices.add(index);
				// vertices[i] = -Float.MAX_VALUE;
				// vertices[i + 1] = -Float.MAX_VALUE;
			}
		}

		while (duplicatedIndices.size > 0) {
			int dupIndex = duplicatedIndices.pop();

			for (int i = 0; i < indices.length; i++) {
				short index = indices[i];
				if (index > dupIndex) {
					indices[i] = (short) (indices[i] - 1);

					vertices[indices[i] * 2] = vertices[index * 2];
					vertices[indices[i] * 2 + 1] = vertices[index * 2 + 1];

					texCoords[indices[i] * 2] = texCoords[index * 2];
					texCoords[indices[i] * 2 + 1] = texCoords[index * 2 + 1];
				}
			}
		}

		return duplicatedIndices.size;
	}

	public static short[] cleanIndices(float[] vertices, short[] indices) {
		// IntArray duplicatedIndices = new IntArray();
		for (int i = 0; i < indices.length; i++) {
			short index = indices[i];
			float x = vertices[2 * index];
			float y = vertices[2 * index + 1];
			int indexInVertices = indexOf(x, y, vertices);
			if (indexInVertices != index) {
				// duplicatedIndices.add(index);
				indices[i] = (short) indexInVertices;
			}
		}

		return indices;
	}

	public static PolygonDefinition loadPolygonDefinition(FileHandle file, TextureRegion region) {
		String line;
		BufferedReader reader = new BufferedReader(new InputStreamReader(file.read()), 64);

		try {
			float[] vertices = null;
			float[] texCoords = null;
			short[] indices = null;

			while (true) {
				line = reader.readLine();

				if (line == null)
					break;
				else if (line.startsWith("v")) {
					// read in vertices
					String[] verticesValues = line.substring(1).trim().split(",");
					vertices = new float[verticesValues.length];
					for (int i = 0; i < verticesValues.length; i += 2) {
						vertices[i] = Float.parseFloat(verticesValues[i]);
						vertices[i + 1] = Float.parseFloat(verticesValues[i + 1]);
					}

					indices = new short[vertices.length / 2];
					for (int i = 0; i < indices.length; i++) {
						indices[i] = (short) i;
					}
				} else if (line.startsWith("u")) {
					// read in uvs
					String[] texCoordsValues = line.substring(1).trim().split(",");
					texCoords = new float[texCoordsValues.length];
					for (int i = 0; i < texCoordsValues.length; i += 2) {
						texCoords[i] = Float.parseFloat(texCoordsValues[i]);
						texCoords[i + 1] = Float.parseFloat(texCoordsValues[i + 1]);
					}

					transformToRegionCoordinates(texCoords, region);
				}
			}

			cleanIndices(vertices, indices);
			return new PolygonDefinition(vertices, texCoords, indices);

			// int duplicatedSize = removeDuplicatedIndices(vertices, texCoords, indices);
			//
			// int newSize = vertices.length - duplicatedSize * 2;
			//
			// float[] newVertices = new float[newSize];
			// float[] newTexCoords = new float[newSize];
			//
			// System.arraycopy(vertices, 0, newVertices, 0, newSize);
			// System.arraycopy(texCoords, 0, newTexCoords, 0, newSize);
			//
			// return new PolygonDefinition(newVertices, newTexCoords, indices);
		} catch (IOException ex) {
			throw new GdxRuntimeException("Error reading polygon shape file: " + file);
		} finally {
			try {
				reader.close();
			} catch (IOException ignored) {
			}
		}
	}

	private static void transformToRegionCoordinates(float[] texCoords, TextureRegion region) {
		float uvWidth = region.getU2() - region.getU();
		float uvHeight = region.getV2() - region.getV();

		for (int i = 0; i < texCoords.length; i += 2) {
			texCoords[i] = region.getU() + texCoords[i] * uvWidth;
			texCoords[i + 1] = region.getV() + texCoords[i + 1] * uvHeight;
		}
	}
	
	public static final short[] spriteIndices = { 0, 1, 2, 2, 3, 0 };

	public static PolygonDefinition fromSprite(Sprite sprite) {
		short[] indices = new short[6];
		float[] vertices = new float[4 * 2];
		float[] texCoords = new float[4 * 2];

		vertices[0] = sprite.getX();
		vertices[1] = sprite.getY();

		texCoords[0] = sprite.getU();
		texCoords[1] = sprite.getV2();

		vertices[2] = sprite.getX();
		vertices[3] = sprite.getY() + sprite.getHeight();

		texCoords[2] = sprite.getU();
		texCoords[3] = sprite.getV();

		vertices[4] = sprite.getX() + sprite.getWidth();
		vertices[5] = sprite.getY() + sprite.getHeight();

		texCoords[4] = sprite.getU2();
		texCoords[5] = sprite.getV();

		vertices[6] = sprite.getX() + sprite.getWidth();
		vertices[7] = sprite.getY();

		texCoords[6] = sprite.getU2();
		texCoords[7] = sprite.getV2();

		System.arraycopy(spriteIndices, 0, indices, 0, spriteIndices.length);

		return new PolygonDefinition(vertices, texCoords, indices);
	}

}
