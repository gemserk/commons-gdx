package com.gemserk.commons.gdx.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Disposable;

public class MeshRenderer implements Disposable {
	
	public int totalRenderCalls = 0;

	private ShaderProgram shader = null;
	private boolean blendingEnabled;
	private int blendSrcFunc = GL11.GL_SRC_ALPHA;
	private int blendDstFunc = GL11.GL_ONE_MINUS_SRC_ALPHA;

	public void setBlendingEnabled(boolean blending) {
		this.blendingEnabled = blending;
	}

	public boolean isBlendingEnabled() {
		return blendingEnabled;
	}

	public int getBlendSrcFunc() {
		return blendSrcFunc;
	}

	public void setBlendSrcFunc(int blendSrcFunc) {
		this.blendSrcFunc = blendSrcFunc;
	}

	public int getBlendDstFunc() {
		return blendDstFunc;
	}

	public void setBlendDstFunc(int blendDstFunc) {
		this.blendDstFunc = blendDstFunc;
	}

	public MeshRenderer() {
		if (Gdx.graphics.isGL20Available()) {
			shader = createDefaultShader();
		}
	}

	public MeshRenderer(ShaderProgram shader) {
		this.shader = shader;
		this.blendingEnabled = true;
	}

	/** Returns a new instance of the default shader used by SpriteBatch for GL2 when no shader is specified. */
	static public ShaderProgram createDefaultShader() {
		String vertexShader = "attribute vec4 " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
				+ "attribute vec4 " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
				+ "uniform mat4 u_projectionViewMatrix;\n" //
				+ "varying vec4 v_color;\n" //
				+ "\n" //
				+ "void main()\n" //
				+ "{\n" //
				+ "   v_color = " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
				+ "   gl_Position =  u_projectionViewMatrix * " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
				+ "}\n";
		String fragmentShader = "#ifdef GL_ES\n" //
				+ "#define LOWP lowp\n" //
				+ "precision mediump float;\n" //
				+ "#else\n" //
				+ "#define LOWP \n" //
				+ "#endif\n" //
				+ "varying LOWP vec4 v_color;\n" //
				+ "void main()\n"//
				+ "{\n" //
				+ "  gl_FragColor = v_color;\n" //
				// + "  gl_FragColor = vec4 ( 0.0, 1.0, 0.0, 1.0 );\n"//
				+ "}";

		ShaderProgram shader = new ShaderProgram(vertexShader, fragmentShader);
		if (shader.isCompiled() == false)
			throw new IllegalArgumentException("couldn't compile shader: " + shader.getLog());
		return shader;
	}

	public void render(Camera camera, Mesh mesh) {
		render(camera,mesh,GL10.GL_TRIANGLES,0, mesh.getMaxIndices() > 0 ? mesh.getNumIndices() : mesh.getNumVertices());
	}
	
	
	public void render(Camera camera, Mesh mesh, int primitive, int offset, int cantElements) {

		if (!blendingEnabled) {
			Gdx.gl.glDisable(GL20.GL_BLEND);
		} else {
			Gdx.gl.glEnable(GL20.GL_BLEND);
			Gdx.gl.glBlendFunc(blendSrcFunc, blendDstFunc);
		}

		if (shader == null) {
			camera.apply(Gdx.gl10);
			mesh.render(primitive, offset, cantElements);
			totalRenderCalls++;
		} else {
			shader.begin();
			shader.setUniformMatrix("u_projectionViewMatrix", camera.combined);
			mesh.render(shader, primitive, offset, cantElements);
			shader.end();
			totalRenderCalls++;
		}
	}

	@Override
	public void dispose() {
		if (shader != null)
			shader.dispose();
	}

}
