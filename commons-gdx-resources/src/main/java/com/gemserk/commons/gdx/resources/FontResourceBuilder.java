package com.gemserk.commons.gdx.resources;

import java.util.ArrayList;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.gemserk.commons.gdx.graphics.BitmapFontUtils;
import com.gemserk.resources.ResourceManager;

public class FontResourceBuilder implements ResourceBuilder<BitmapFont> {

	// public static class ScalableBitmapFont extends BitmapFont {
	//
	// private float realScale;
	//
	// public void setRealScale(float realScale) {
	// this.realScale = realScale;
	// }
	//
	// public float getRealScale() {
	// return realScale;
	// }
	//
	// public ScalableBitmapFont() {
	// super();
	// }
	//
	// public ScalableBitmapFont(FileHandle fontFile, TextureRegion region, boolean flip) {
	// super(fontFile, region, flip);
	// }
	//
	// @Override
	// public void setScale(float scaleXY) {
	// super.setScale(scaleXY * realScale);
	// }
	//
	// @Override
	// public void setScale(float scaleX, float scaleY) {
	// super.setScale(scaleX * realScale, scaleY * realScale);
	// }
	//
	// }

	private ResourceManager<String> resourceManager;

	private FileHandle imageFile;
	private FileHandle fontFile;

	private TextureFilter minFilter = TextureFilter.Nearest;
	private TextureFilter magFilter = TextureFilter.Nearest;

	private boolean useIntegerPositions = true;
	private CharSequence fixedWidthGlyphs = null;

	private float scale = 1f;

	static class FontSpacing {

		CharSequence charSequence;
		int spacing;

		public FontSpacing(CharSequence charSequence, int spacing) {
			this.charSequence = charSequence;
			this.spacing = spacing;
		}

	}

	private ArrayList<FontSpacing> spacings = new ArrayList<FontResourceBuilder.FontSpacing>();

	private String textureAtlasId;
	private String regionId;

	public FontResourceBuilder(ResourceManager<String> resourceManager) {
		this.resourceManager = resourceManager;
	}

	public FontResourceBuilder region(String textureAtlasId, String regionId) {
		this.textureAtlasId = textureAtlasId;
		this.regionId = regionId;
		return this;
	}

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

	public FontResourceBuilder spacing(CharSequence charSequence, int spacing) {
		this.spacings.add(new FontSpacing(charSequence, spacing));
		return this;
	}

	public FontResourceBuilder scale(float scale) {
		this.scale = scale;
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
		} else if (textureAtlasId != null && regionId != null) {
			if (fontFile == null)
				throw new IllegalArgumentException("Can't build a font resource without specifying the fontFile.");
			TextureAtlas atlas = resourceManager.getResourceValue(textureAtlasId);
			AtlasRegion region = atlas.findRegion(regionId);
			if (region == null)
				throw new IllegalArgumentException("Can't build a font resource, region " + regionId + " not found in texture atlas");
			bitmapFont = new BitmapFont(fontFile, region, false);
		} else {
			// if image file and font file are not specified, it creates a new default bitmap font.
			bitmapFont = new BitmapFont();
			bitmapFont.getRegion().getTexture().setFilter(minFilter, magFilter);
		}

		bitmapFont.setUseIntegerPositions(useIntegerPositions);
		if (fixedWidthGlyphs != null)
			bitmapFont.setFixedWidthGlyphs(fixedWidthGlyphs);

		for (int i = 0; i < spacings.size(); i++) {
			FontSpacing fontSpacing = spacings.get(i);
			CharSequence charSequence = fontSpacing.charSequence;
			BitmapFontUtils.spacing(bitmapFont, charSequence, fontSpacing.spacing);
		}

		bitmapFont.getData().setScale(scale);
//		bitmapFont.setScale(scale);
		// bitmapFont.setRealScale(scale);

		return bitmapFont;
	}

}