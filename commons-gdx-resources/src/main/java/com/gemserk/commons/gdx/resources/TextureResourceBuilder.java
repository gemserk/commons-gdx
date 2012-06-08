package com.gemserk.commons.gdx.resources;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;

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
	
	public TextureResourceBuilder useMipMaps(boolean useMipMaps){
		this.useMipMaps = useMipMaps;
		return this;
	}
	
	public TextureResourceBuilder format(Format format){
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
		texture.setFilter(minFilter, magFilter);
		texture.setWrap(uTextureWrap, vTextureWrap);
		return texture;
	}

}