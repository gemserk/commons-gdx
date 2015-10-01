package com.gemserk.commons.gdx.graphics;

import java.nio.FloatBuffer;

import com.badlogic.gdx.Gdx;

public class Mesh2dRenderUtils {

	@Deprecated
	public static void draw(int primitiveType, int count, FloatBuffer verticesBuffer) {
		throw new UnsupportedOperationException("GL10 deprecated");
//		GL10 gl = Gdx.gl10;
//		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
//		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, verticesBuffer);
//		gl.glDrawArrays(primitiveType, 0, count);
	}

	@Deprecated
	public static void draw(int primitiveType, int count, FloatBuffer verticesBuffer, FloatBuffer colorsBuffer) {
		throw new UnsupportedOperationException("GL10 deprecated");
//		GL10 gl = Gdx.gl10;
//
//		// gl.glPolygonMode(GL10.GL_FRONT_AND_BACK, GL10.GL_LINE);
//
//		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
//		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, verticesBuffer);
//
//		if (colorsBuffer != null) {
//			gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
//			gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorsBuffer);
//		}
//
//		gl.glDrawArrays(primitiveType, 0, count);
//
//		if (colorsBuffer != null) {
//			gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
//			gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorsBuffer);
//		}
	}

	@Deprecated
	public static void draw(int primitiveType, FloatBuffer verticesBuffer, FloatBuffer colorsBuffer, FloatBuffer texCoordsBuffer) {
		throw new UnsupportedOperationException("GL10 deprecated");
//		GL10 gl = Gdx.gl10;
//
//		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
//		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, verticesBuffer);
//
//		if (colorsBuffer != null) {
//			gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
//			gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorsBuffer);
//		}
//
//		if (texCoordsBuffer != null) {
//			gl.glEnable(GL10.GL_TEXTURE_2D);
//			gl.glClientActiveTexture(GL10.GL_TEXTURE0);
//			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
//			gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, texCoordsBuffer);
//		}
//
//		gl.glDrawArrays(primitiveType, 0, verticesBuffer.limit());
//
//		if (colorsBuffer != null) {
//			gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
//		}
//
//		if (texCoordsBuffer != null) {
//			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
//			gl.glDisable(GL10.GL_TEXTURE_2D);
//		}
	}

	@Deprecated
	public static void draw(int primitiveType, Mesh2d mesh2d) {
		throw new UnsupportedOperationException("GL10 deprecated");
//		draw(primitiveType, mesh2d.getVertexArray(), mesh2d.getColorArray(), mesh2d.getTexCoordArray());
	}

	@Deprecated
	public static void draw(int primitiveType, Mesh2d mesh2d, float x, float y, float angle) {
		throw new UnsupportedOperationException("GL10 deprecated");
//		GL10 gl = Gdx.graphics.getGL10();
//
//		gl.glPushMatrix();
//		gl.glTranslatef(x, y, 0f);
//		gl.glRotatef(angle, 0f, 0f, 1f);
//
//		draw(primitiveType, mesh2d);
//
//		gl.glPopMatrix();
	}
	
}
