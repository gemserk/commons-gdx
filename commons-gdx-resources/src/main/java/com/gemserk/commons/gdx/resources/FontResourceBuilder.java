package com.gemserk.commons.gdx.resources;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class FontResourceBuilder implements ResourceBuilder<BitmapFont> {

	private FileHandle imageFile;
	private FileHandle fontFile;

	private TextureFilter minFilter = TextureFilter.Nearest;
	private TextureFilter magFilter = TextureFilter.Nearest;

	private boolean useIntegerPositions = true;
	private CharSequence fixedWidthGlyphs = null;

	public FontResourceBuilder filter(TextureFilter filter) {
		this.minFilter = filter;
		this.magFilter = filter;
		return this;
	}

	public FontResourceBuilder imageFile(FileHandle imageFile) {
		this.imageFile = imageFile;
		return this;
	}

	public FontResourceBuilder fontFile(FileHandle fontFile) {
		this.fontFile = fontFile;
		return this;
	}

	public FontResourceBuilder minFilter(TextureFilter minFilter) {
		this.minFilter = minFilter;
		return this;
	}

	public FontResourceBuilder magFilter(TextureFilter magFilter) {
		this.magFilter = magFilter;
		return this;
	}

	public FontResourceBuilder useIntegerPositions(boolean useIntegerPositions) {
		this.useIntegerPositions = useIntegerPositions;
		return this;
	}

	public FontResourceBuilder fixedWidthGlyphs(CharSequence fixedWidthGlyphs) {
		this.fixedWidthGlyphs = fixedWidthGlyphs;
		return this;
	}

	@Override
	public boolean isVolatile() {
		return false;
	}

	@Override
	public BitmapFont build() {
		BitmapFont bitmapFont;

		if (imageFile != null && fontFile != null) {
			Texture texture = new Texture(imageFile);
			texture.setFilter(minFilter, magFilter);
			bitmapFont = new BitmapFont(fontFile, new Sprite(texture), false);
		} else {
			// if image file and font file are not specified, it creates a new default bitmap font.
			bitmapFont = new BitmapFont();
			bitmapFont.getRegion().getTexture().setFilter(minFilter, magFilter);
		}

		bitmapFont.setUseIntegerPositions(useIntegerPositions);
		if (fixedWidthGlyphs != null)
			bitmapFont.setFixedWidthGlyphs(fixedWidthGlyphs);
		return bitmapFont;
	}

}