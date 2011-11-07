package com.gemserk.commons.gdx.resources;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class FontResourceBuilder implements ResourceBuilder<BitmapFont> {

	private final FileHandle imageFile;
	private final FileHandle fontFile;

	private TextureFilter filter = TextureFilter.Nearest;
	private boolean useIntegerPositions = true;

	public FontResourceBuilder filter(TextureFilter filter) {
		this.filter = filter;
		return this;
	}

	public FontResourceBuilder useIntegerPositions(boolean useIntegerPositions) {
		this.useIntegerPositions = useIntegerPositions;
		return this;
	}

	@Override
	public boolean isCached() {
		return true;
	}

	@Override
	public BitmapFont build() {
		Texture texture = new Texture(imageFile);
		texture.setFilter(filter, filter);
		BitmapFont bitmapFont = new BitmapFont(fontFile, new Sprite(texture), false);
		bitmapFont.setUseIntegerPositions(useIntegerPositions);
		return bitmapFont;
	}

	public FontResourceBuilder(FileHandle imageFile, FileHandle fontFile) {
		this.imageFile = imageFile;
		this.fontFile = fontFile;
	}

}