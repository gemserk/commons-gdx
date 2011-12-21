package com.gemserk.commons.gdx.resources;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;

public class TextureResourceBuilder implements ResourceBuilder<Texture> {

	FileHandle fileHandle;

	TextureFilter minFilter = TextureFilter.Nearest;
	TextureFilter magFilter = TextureFilter.Nearest;

	TextureWrap uTextureWrap = TextureWrap.ClampToEdge;
	TextureWrap vTextureWrap = TextureWrap.ClampToEdge;

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

	@Override
	public boolean isVolatile() {
		return false;
	}

	public TextureResourceBuilder(FileHandle fileHandle) {
		this.fileHandle = fileHandle;
	}

	@Override
	public Texture build() {
		Texture texture = new Texture(fileHandle);
		texture.setFilter(minFilter, magFilter);
		texture.setWrap(uTextureWrap, vTextureWrap);
		return texture;
	}

}