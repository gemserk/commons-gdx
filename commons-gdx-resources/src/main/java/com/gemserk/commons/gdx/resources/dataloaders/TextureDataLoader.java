package com.gemserk.commons.gdx.resources.dataloaders;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;

public class TextureDataLoader extends DisposableDataLoader<Texture> {
	
	private boolean linearFilter = true;

	public TextureDataLoader(FileHandle fileHandle) {
		super(fileHandle);
	}

	public TextureDataLoader(FileHandle fileHandle, boolean linearFilter) {
		super(fileHandle);
		this.linearFilter = linearFilter;
	}

	@Override
	public Texture load() {
		Texture texture = new Texture(fileHandle);
		if (linearFilter)
			texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		return texture;
	}

}