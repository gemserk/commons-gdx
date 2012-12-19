package com.gemserk.commons.gdx.resources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.gemserk.commons.gdx.resources.exceptions.OpenGLOutOfMemoryException;
import com.gemserk.util.GpuMemUtils;

public class TextureResourceBuilder implements ResourceBuilder<Texture> {

	FileHandle fileHandle;

	TextureFilter minFilter = TextureFilter.Nearest;
	TextureFilter magFilter = TextureFilter.Nearest;

	TextureWrap uTextureWrap = TextureWrap.ClampToEdge;
	TextureWrap vTextureWrap = TextureWrap.ClampToEdge;

	boolean useMipMaps;

	Format format = null;

	public TextureResourceBuilder minFilter(TextureFilter minFilter) {
		this.minFilter = minFilter;
		return this;
	}

	public TextureResourceBuilder magFilter(TextureFilter magFilter) {
		this.magFilter = magFilter;
		return this;
	}

	public TextureResourceBuilder textureWrap(TextureWrap u, TextureWrap v) {
		uTextureWrap = u;
		vTextureWrap = v;
		return this;
	}

	public TextureResourceBuilder useMipMaps(boolean useMipMaps) {
		this.useMipMaps = useMipMaps;
		return this;
	}

	public TextureResourceBuilder format(Format format) {
		this.format = format;
		return this;
	}

	@Override
	public boolean isVolatile() {
		return false;
	}

	public TextureResourceBuilder(FileHandle fileHandle) {
		this.fileHandle = fileHandle;
	}

	@Override
	public Texture build() {
		Texture texture = new Texture(fileHandle, format, useMipMaps);
		int glError = Gdx.gl.glGetError();

		// temporary to test the error...

//		{
//			float textureMem = GpuMemUtils.getTextureGpuSize().gpuMemSize / 1000000f;
//			if (textureMem > 20f) {
//				throw new OpenGLOutOfMemoryException("Error while loading texture " + fileHandle + " - TEXTUREMEM: " + textureMem);
//			}
//		}

		if (glError != 0) {
			float textureMem = GpuMemUtils.getTextureGpuSize().gpuMemSize / 1000000f;
			String duplicateTextureErrors = GpuMemUtils.checkDuplicateTextureErrors();
			if (glError == GL10.GL_OUT_OF_MEMORY)
				throw new OpenGLOutOfMemoryException("Error while loading texture " + fileHandle + " - TEXTUREMEM: " + textureMem + " - " + duplicateTextureErrors);
			else
				throw new RuntimeException("OpenGL error code while loading texture: " + glError + " - " + fileHandle + " - TEXTUREMEM: " + textureMem + " - " + duplicateTextureErrors);
		}

		texture.setFilter(minFilter, magFilter);
		texture.setWrap(uTextureWrap, vTextureWrap);
		return texture;
	}
}
