package com.gemserk.commons.gdx.resources.dataloaders;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class BitmapFontDataLoader extends DisposableDataLoader<BitmapFont> {

	private final TextureRegion fontImage;

	public BitmapFontDataLoader(FileHandle fontFile, TextureRegion fontImage) {
		super(fontFile);
		this.fontImage = fontImage;
	}

	@Override
	public BitmapFont load() {
		return new BitmapFont(fileHandle, fontImage, false);
	}

}