package com.gemserk.commons.gdx.g2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Mesh.VertexDataType;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;

public class MeshSpriteBatch {

	public static final int POSITION_SIZE = 3;
	public static final int COLOR_SIZE = 1;
	public static final int TEXTURE_COORDINATE_SIZE = 2;
	public static final int VERTEX_SIZE = POSITION_SIZE + COLOR_SIZE + TEXTURE_COORDINATE_SIZE;

	private Mesh mesh;
	private Mesh[] buffers;

	private Texture lastTexture = null;

	private short idx = 0;
	private int indicesIndex = 0;
	private int currBufferIdx = 0;

	private final float[] vertices;
	private final short[] indices;

	private final Matrix4 transformMatrix = new Matrix4();
	private final Matrix4 projectionMatrix = new Matrix4();
	private final Matrix4 combinedMatrix = new Matrix4();

	private boolean drawing = false;

	private boolean blendingDisabled = false;
	private boolean depthTestEnabled;

	private int blendSrcFunc = GL11.GL_SRC_ALPHA;
	private int blendDstFunc = GL11.GL_ONE_MINUS_SRC_ALPHA;

	private final ShaderProgram shader;
	private boolean ownsShader;

	public int renderCalls = 0;

	public int totalRenderCalls = 0;

	public int maxVerticesInBatch = 0;

	private ShaderProgram customShader = null;

	// private ShortBuffer indicesBuffer;
	// private FloatBuffer verticesBuffer;

	public MeshSpriteBatch() {
		this(2000);
	}

	public MeshSpriteBatch(int size) {
		this(size, null);
	}

	public MeshSpriteBatch(int size, ShaderProgram defaultShader) {
		this(size, 1, defaultShader);
	}

	public MeshSpriteBatch(int size, int buffers) {
		this(size, buffers, null);
	}

	public MeshSpriteBatch(int size, int buffers, ShaderProgram defaultShader) {
		this.buffers = new Mesh[buffers];

		for (int i = 0; i < buffers; i++) {
			this.buffers[i] = new Mesh(VertexDataType.VertexArray, false, size, size, //
					VertexAttribute.Position(), //
					VertexAttribute.Color(),//
					VertexAttribute.TexCoords(0));
		}

		projectionMatrix.setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		vertices = new float[size * VERTEX_SIZE];
		indices = new short[size];

		mesh = this.buffers[0];

		if (Gdx.graphics.isGL20Available() && defaultShader == null) {
			shader = createDefaultShader();
			ownsShader = true;
		} else
			shader = defaultShader;

		depthTestEnabled = false;

		// verticesBuffer = mesh.getVerticesBuffer();
		// verticesBuffer.clear();
		//
		// indicesBuffer = mesh.getIndicesBuffer();
		// indicesBuffer.clear();
	}

	static public ShaderProgram createDefaultShader() {
		String vertexShader = "attribute vec4 " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
				+ "attribute vec4 " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
				+ "attribute vec2 " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
				+ "uniform mat4 u_projectionViewMatrix;\n" //
				+ "varying vec4 v_color;\n" //
				+ "varying vec2 v_texCoords;\n" //
				+ "\n" //
				+ "void main()\n" //
				+ "{\n" //
				+ "   v_color = " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
				+ "   v_texCoords = " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
				+ "   gl_Position =  u_projectionViewMatrix * " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
				+ "}\n";
		String fragmentShader = "#ifdef GL_ES\n" //
				+ "#define LOWP lowp\n" //
				+ "precision mediump float;\n" //
				+ "#else\n" //
				+ "#define LOWP \n" //
				+ "#endif\n" //
				+ "varying LOWP vec4 v_color;\n" //
				+ "varying vec2 v_texCoords;\n" //
				+ "uniform sampler2D u_texture;\n" //
				+ "void main()\n"//
				+ "{\n" //
				+ "  gl_FragColor = v_color * texture2D(u_texture, v_texCoords);\n" //
				+ "}";

		ShaderProgram shader = new ShaderProgram(vertexShader, fragmentShader);
		if (shader.isCompiled() == false)
			throw new IllegalArgumentException("couldn't compile shader: " + shader.getLog());
		return shader;
	}

	public void begin() {
		if (drawing)
			throw new IllegalStateException("you have to call end() first");
		renderCalls = 0;

		if (blendingDisabled)
			Gdx.gl.glDisable(GL20.GL_BLEND);
		else {
			Gdx.gl.glEnable(GL20.GL_BLEND);
			Gdx.gl.glBlendFunc(blendSrcFunc, blendDstFunc);
		}

		updateDepthTest();

		if (Gdx.graphics.isGL20Available()) {
			if (customShader != null)
				customShader.begin();
			else
				shader.begin();
		} else {
			Gdx.gl.glEnable(GL10.GL_TEXTURE_2D);
		}

		setupMatrices();

		idx = 0;
		indicesIndex = 0;

		lastTexture = null;
		drawing = true;
	}

	private void updateDepthTest() {
		if (depthTestEnabled) {
			Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
			Gdx.gl.glDepthMask(depthTestEnabled);
			Gdx.gl.glDepthFunc(GL10.GL_LESS);
		} else {
			Gdx.gl.glDisable(GL20.GL_DEPTH_TEST);
			Gdx.gl.glDepthMask(depthTestEnabled);
		}
	}

	public void end() {
		if (!drawing)
			throw new IllegalStateException("begin must be called before end.");
		if (idx > 0)
			flush();
		lastTexture = null;
		idx = 0;
		indicesIndex = 0;
		drawing = false;

		if (Gdx.graphics.isGL20Available()) {
			if (customShader != null)
				customShader.end();
			else
				shader.end();
		}
	}

	public void draw(Texture texture, float[] spriteVertices, short[] indices) {
		this.draw(texture, spriteVertices, 0, spriteVertices.length, indices, 0, indices.length);
	}

	public void draw(Texture texture, float[] spriteVertices, int offset, int length, short[] indices, int indicesOffset, int indicesLength) {
		if (!drawing)
			throw new IllegalStateException("begin must be called before draw.");

		switchTexture(texture);

		int capacity = vertices.length;
		// int capacity = verticesBuffer.capacity();

		if (idx + length > capacity || indicesIndex + indicesLength > this.indices.length)
			flush();

		if (length > capacity)
			throw new IllegalArgumentException("Can't handle " + length + " vertices in one call, increase batch vertices size, current size is " + capacity);

		System.arraycopy(spriteVertices, offset, vertices, idx, length);
		// verticesBuffer.put(spriteVertices, offset, length);

		int iOffset = idx / VERTEX_SIZE;

		for (int i = 0; i < indicesLength; i++) {
			this.indices[indicesIndex + i] = (short) (indices[i] + iOffset);
			// indicesBuffer.put(indicesIndex + i, (short) (indices[i] + iOffset));

		}

		indicesIndex += indicesLength;

		idx += length;
	}

	private void flush() {
		if (idx == 0)
			return;

		renderCalls++;
		totalRenderCalls++;
		int verticesInBatch = idx / VERTEX_SIZE;
		if (verticesInBatch > maxVerticesInBatch)
			maxVerticesInBatch = verticesInBatch;

		lastTexture.bind();

		mesh.setVertices(vertices, 0, idx);
		mesh.setIndices(indices, 0, indicesIndex);

		if (Gdx.graphics.isGL20Available()) {
			if (customShader != null)
				mesh.render(customShader, GL10.GL_TRIANGLES, 0, indicesIndex);
			else
				mesh.render(shader, GL10.GL_TRIANGLES, 0, indicesIndex);
		} else {
			mesh.render(GL10.GL_TRIANGLES, 0, indicesIndex);
		}

		// verticesBuffer.clear();
		// indicesBuffer.clear();

		idx = 0;
		indicesIndex = 0;

		currBufferIdx++;
		if (currBufferIdx == buffers.length)
			currBufferIdx = 0;
		mesh = buffers[currBufferIdx];
	}

	public void disableBlending() {
		if (blendingDisabled)
			return;

		if (drawing) {
			flush();
			Gdx.gl.glDisable(GL20.GL_BLEND);
		}

		blendingDisabled = true;
	}

	public void enableBlending() {
		if (!blendingDisabled)
			return;

		if (drawing) {
			flush();
			Gdx.gl.glEnable(GL20.GL_BLEND);
			Gdx.gl.glBlendFunc(blendSrcFunc, blendDstFunc);
		}

		blendingDisabled = false;
	}

	public void setDepthTestEnabled(boolean enabled) {
		if (this.depthTestEnabled && enabled)
			return;

		if (!this.depthTestEnabled && !enabled)
			return;

		this.depthTestEnabled = enabled;

		if (drawing) {
			flush();
			updateDepthTest();
		}
	}

	/**
	 * Sets the blending function to be used when rendering sprites.
	 * 
	 * @param srcFunc
	 *            the source function, e.g. GL11.GL_SRC_ALPHA
	 * @param dstFunc
	 *            the destination function, e.g. GL11.GL_ONE_MINUS_SRC_ALPHA
	 */
	public void setBlendFunction(int srcFunc, int dstFunc) {

		if (blendSrcFunc == srcFunc && blendDstFunc == dstFunc)
			return;

		blendSrcFunc = srcFunc;
		blendDstFunc = dstFunc;

		if (drawing) {
			Gdx.gl.glBlendFunc(blendSrcFunc, blendDstFunc);
			flush();
		}

	}

	/** Disposes all resources associated with this SpriteBatch */
	public void dispose() {
		for (int i = 0; i < buffers.length; i++)
			buffers[i].dispose();
		if (ownsShader && shader != null)
			shader.dispose();
	}

	/**
	 * Returns the current projection matrix. Changing this will result in undefined behaviour.
	 * 
	 * @return the currently set projection matrix
	 */
	public Matrix4 getProjectionMatrix() {
		return projectionMatrix;
	}

	/**
	 * Returns the current transform matrix. Changing this will result in undefined behaviour.
	 * 
	 * @return the currently set transform matrix
	 */
	public Matrix4 getTransformMatrix() {
		return transformMatrix;
	}

	/**
	 * Sets the projection matrix to be used by this SpriteBatch. If this is called inside a {@link #begin()}/{@link #end()} block. the current batch is flushed to the gpu.
	 * 
	 * @param projection
	 *            the projection matrix
	 */
	public void setProjectionMatrix(Matrix4 projection) {
		if (drawing)
			flush();
		projectionMatrix.set(projection);
		if (drawing)
			setupMatrices();
	}

	/**
	 * Sets the transform matrix to be used by this SpriteBatch. If this is called inside a {@link #begin()}/{@link #end()} block. the current batch is flushed to the gpu.
	 * 
	 * @param transform
	 *            the transform matrix
	 */
	public void setTransformMatrix(Matrix4 transform) {
		if (drawing)
			flush();
		transformMatrix.set(transform);
		if (drawing)
			setupMatrices();
	}

	private void setupMatrices() {
		if (!Gdx.graphics.isGL20Available()) {
			GL10 gl = Gdx.gl10;
			gl.glMatrixMode(GL10.GL_PROJECTION);
			gl.glLoadMatrixf(projectionMatrix.val, 0);
			gl.glMatrixMode(GL10.GL_MODELVIEW);
			gl.glLoadMatrixf(transformMatrix.val, 0);
		} else {
			combinedMatrix.set(projectionMatrix).mul(transformMatrix);
			if (customShader != null) {
				customShader.setUniformMatrix("u_proj", projectionMatrix);
				customShader.setUniformMatrix("u_trans", transformMatrix);
				customShader.setUniformMatrix("u_projTrans", combinedMatrix);
				customShader.setUniformi("u_texture", 0);
			} else {
				shader.setUniformMatrix("u_projectionViewMatrix", combinedMatrix);
				shader.setUniformi("u_texture", 0);
			}
		}
	}

	private void switchTexture(Texture texture) {
		if (texture == lastTexture)
			return;
		flush();
		lastTexture = texture;
	}

	/**
	 * Sets the shader to be used in a GLES 2.0 environment. Vertex position attribute is called "a_position", the texture coordinates attribute is called called "a_texCoords0", the color attribute is called "a_color". See {@link ShaderProgram#POSITION_ATTRIBUTE}, {@link ShaderProgram#COLOR_ATTRIBUTE} and {@link ShaderProgram#TEXCOORD_ATTRIBUTE} which gets "0" appened to indicate the use of the first texture unit. The projection matrix is uploaded via a mat4 uniform called "u_proj", the transform matrix is uploaded via a uniform called "u_trans", the combined transform and projection matrx is is uploaded via a mat4 uniform called "u_projTrans". The texture sampler is passed via a uniform called "u_texture".</p>
	 * 
	 * Call this method with a null argument to use the default shader.</p>
	 * 
	 * This method will flush the batch before setting the new shader, you can call it in between {@link #begin()} and {@link #end()}.
	 * 
	 * @param shader
	 *            the {@link ShaderProgram} or null to use the default shader.
	 */
	public void setShader(ShaderProgram shader) {
		if (drawing) {
			flush();
			if (customShader != null)
				customShader.end();
			else
				this.shader.end();
		}
		customShader = shader;
		if (drawing) {
			if (customShader != null)
				customShader.begin();
			else
				this.shader.begin();
			setupMatrices();
		}
	}

	/** @return whether blending for sprites is enabled */
	public boolean isBlendingEnabled() {
		return !blendingDisabled;
	}
}
