package com.gemserk.commons.gdx.resources.dataloaders;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;

public class TextureDataLoader extends DisposableDataLoader<Texture> {

	public TextureDataLoader(FileHandle fileHandle) {
		super(fileHandle);
	}

	@Override
	public Texture load() {
		Texture texture = new Texture(fileHandle);
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		return texture;
	}

}