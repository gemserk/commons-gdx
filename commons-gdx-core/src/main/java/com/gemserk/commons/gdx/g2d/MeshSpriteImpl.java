package com.gemserk.commons.gdx.g2d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class MeshSpriteImpl implements MeshSprite {

	private static final int POSITION_SIZE_2D = 2;

	public Texture texture;

	/**
	 * a float[] with 2 floats per vertex specifying x, y.
	 */
	public final float[] vertices;

	/**
	 * a float[] with 2 floats per vertex specifying the texture coordinates u, v.
	 */
	public final float[] texCoords;

	/**
	 * a short[] with the indices of each vertex index, can be reused between different meshsprites.
	 */
	public final short[] indices;

	/**
	 * a float[] with the processed vertices located after transforming the sprite.
	 */
	public float[] meshVertices;

	public Rectangle bounds;

	/**
	 * Stores the z location of the mesh sprite, to be used with zBuffer.
	 */
	public float z;

	public float ox, oy;
	public float x, y;
	public float angle;
	public float sx, sy;

	public float width, height;

	public Color color;

	private boolean dirty;

	private final int verticesCount;

	private final int positionSize;
	private final int colorSize;
	private final int textureCoordinateSize;
	private final int vertexSize;

	final Matrix4 matrix4 = new Matrix4();
	final Vector3 vector3 = new Vector3();

	public MeshSpriteImpl(float[] vertices, float[] texCoords, short[] indices, Texture texture) {
		this.vertices = vertices;
		this.texCoords = texCoords;
		this.indices = indices;

		// these arrays will never be modified so they could be reused from outside maybe, instead of copying them inside here
		// so we save memory and speed when creating the meshes if they are based in the same element.

		// this.indices = new short[indices.length];
		// this.vertices = new float[vertices.length];
		// this.texCoords = new float[texCoords.length];
		// System.arraycopy(vertices, 0, this.vertices, 0, vertices.length);
		// System.arraycopy(texCoords, 0, this.texCoords, 0, texCoords.length);
		// System.arraycopy(indices, 0, this.indices, 0, indices.length);

		this.texture = texture;
		this.bounds = new Rectangle();
		this.color = new Color(1f, 1f, 1f, 1f);

		// calculate mesh center

		setPosition(0, 0);
		setScale(1, 1);
		setRotation(0);

		positionSize = MeshSpriteBatch.POSITION_SIZE;
		colorSize = MeshSpriteBatch.COLOR_SIZE;
		textureCoordinateSize = MeshSpriteBatch.TEXTURE_COORDINATE_SIZE;

		vertexSize = positionSize + colorSize + textureCoordinateSize;

		verticesCount = vertices.length / 2;

		meshVertices = new float[verticesCount * vertexSize];

		getVertices();

		width = Math.round(bounds.getWidth());
		height = Math.round(bounds.getHeight());
	}

	@Override
	public void setBounds(float x, float y, float width, float height) {
		setSize(width, height);
		setPosition(x, y);
	}

	@Override
	public void setSize(float width, float height) {
		if (Float.compare(this.width, width) == 0 && Float.compare(this.height, height) == 0)
			return;
		setScale(width / this.width, height / this.height);
	}

	@Override
	public void setPosition(float x, float y) {
		setX(x);
		setY(y);
	}

	@Override
	public void setZ(float z) {
		if (Float.compare(this.z, z) == 0)
			return;
		this.z = z;
		dirty = true;
	}

	@Override
	public void setX(float x) {
		if (Float.compare(this.x, x) == 0)
			return;
		this.x = x;
		dirty = true;
	}

	@Override
	public void setY(float y) {
		if (Float.compare(this.y, y) == 0)
			return;
		this.y = y;
		dirty = true;
	}

	@Override
	public void translateX(float xAmount) {
		if (Float.compare(xAmount, 0f) == 0)
			return;
		this.x += xAmount;
		dirty = true;
	}

	@Override
	public void translateY(float yAmount) {
		if (Float.compare(yAmount, 0f) == 0)
			return;
		this.y += yAmount;
		dirty = true;
	}

	@Override
	public void translate(float xAmount, float yAmount) {
		translateX(xAmount);
		translateY(yAmount);
	}

	@Override
	public void setColor(Color tint) {
		if (color.equals(tint))
			return;
		color.set(tint);
		dirty = true;
	}

	@Override
	public void setColor(float r, float g, float b, float a) {
		if (Float.compare(color.r, r) == 0 //
				&& Float.compare(color.g, g) == 0 //
				&& Float.compare(color.b, b) == 0 //
				&& Float.compare(color.a, a) == 0)
			return;
		color.set(r, g, b, a);
		dirty = true;
	}

	@Override
	public void setOrigin(float originX, float originY) {
		if (Float.compare(ox, originX) == 0 && Float.compare(oy, originY) == 0)
			return;
		this.ox = originX;
		this.oy = originY;
		dirty = true;
	}

	@Override
	public void setRotation(float degrees) {
		if (Float.compare(angle, degrees) == 0)
			return;
		this.angle = degrees;
		dirty = true;
	}

	@Override
	public void rotate(float degrees) {
		if (Float.compare(0f, degrees) == 0)
			return;
		this.angle += degrees;
		dirty = true;
	}

	@Override
	public void setScale(float scaleXY) {
		setScale(scaleXY, scaleXY);
	}

	@Override
	public void setScale(float scaleX, float scaleY) {
		if (Float.compare(scaleX, sx) == 0 && Float.compare(scaleY, sy) == 0)
			return;
		this.sx = scaleX;
		this.sy = scaleY;
		dirty = true;
	}

	@Override
	public void scale(float amount) {
		if (Float.compare(amount, 1f) == 0)
			return;
		setScale(sx * amount, sy * amount);
	}

	@Override
	public short[] getIndices() {
		return indices;
	}

	@Override
	public float[] getVertices() {
		if (dirty)
			updateVertices();
		return meshVertices;
	}

	private void updateVertices() {
		int vertexIndex = 0;
		int textureIndex = 0;

		matrix4.idt();
		matrix4.translate(x, y, 0f);
		matrix4.rotate(Vector3.Z, angle);
		matrix4.scale(sx, sy, 1f);
		matrix4.translate(ox, oy, 0f);

		float minx = Float.MAX_VALUE;
		float miny = Float.MAX_VALUE;
		float maxx = -Float.MAX_VALUE;
		float maxy = -Float.MAX_VALUE;

		for (int i = 0; i < meshVertices.length; i += vertexSize) {
			vector3.set(vertices[vertexIndex], vertices[vertexIndex + 1], 0f);
			vector3.mul(matrix4);

			float vx = vector3.x;
			float vy = vector3.y;

			meshVertices[i + 0] = vx;
			meshVertices[i + 1] = vy;
			meshVertices[i + 2] = z;

			if (minx > vx)
				minx = vx;
			if (miny > vy)
				miny = vy;
			if (maxx < vx)
				maxx = vx;
			if (maxy < vy)
				maxy = vy;

			meshVertices[i + positionSize] = color.toFloatBits();
			meshVertices[i + positionSize + colorSize] = texCoords[textureIndex];
			meshVertices[i + positionSize + colorSize + 1] = texCoords[textureIndex + 1];

			vertexIndex += POSITION_SIZE_2D;
			textureIndex += textureCoordinateSize;
		}

		bounds.x = minx;
		bounds.y = miny;
		bounds.width = maxx - minx;
		bounds.height = maxy - miny;

		dirty = false;
	}

	@Override
	public Rectangle getBoundingRectangle() {
		if (dirty)
			getVertices();
		return bounds;
	}

	@Override
	public float getZ() {
		return z;
	}

	@Override
	public float getX() {
		return x;
	}

	@Override
	public float getY() {
		return y;
	}

	@Override
	public float getWidth() {
		return width;
	}

	@Override
	public float getHeight() {
		return height;
	}

	@Override
	public float getOriginX() {
		return ox;
	}

	@Override
	public float getOriginY() {
		return oy;
	}

	@Override
	public float getRotation() {
		return angle;
	}

	@Override
	public float getScaleX() {
		return sx;
	}

	@Override
	public float getScaleY() {
		return sy;
	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public Texture getTexture() {
		return texture;
	}

}
