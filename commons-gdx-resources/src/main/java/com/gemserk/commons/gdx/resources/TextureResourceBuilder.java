package com.gemserk.commons.gdx.resources;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;

public class TextureResourceBuilder implements ResourceBuilder<Texture> {
	
	FileHandle fileHandle;
	
	TextureFilter minFilter = TextureFilter.Nearest;
	TextureFilter magFilter = TextureFilter.Nearest;
	
	public TextureResourceBuilder minFilter(TextureFilter minFilter) {
		this.minFilter = minFilter;
		return this;
	}

	public TextureResourceBuilder magFilter(TextureFilter magFilter) {
		this.magFilter = magFilter;
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
		return texture;
	}

}