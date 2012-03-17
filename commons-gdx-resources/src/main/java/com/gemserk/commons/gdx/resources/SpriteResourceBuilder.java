package com.gemserk.commons.gdx.resources;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.gemserk.resources.ResourceManager;

public class SpriteResourceBuilder implements ResourceBuilder<Sprite> {

	static final int AtlasRegionNoIndex = -1;

	private int x;
	private int y;
	private int width;
	private int height;
	private float scale = 1f;
	private ResourceManager<String> resourceManager;
	private String textureId;
	private String textureAtlasId;
	private String regionId;

	private AtlasRegion region;

	private boolean flip = false;
	private boolean flop = false;
	private int regionIndex;

	public SpriteResourceBuilder x(int x) {
		this.x = x;
		return this;
	}

	public SpriteResourceBuilder y(int y) {
		this.y = y;
		return this;
	}

	public SpriteResourceBuilder width(int width) {
		this.width = width;
		return this;
	}

	public SpriteResourceBuilder height(int height) {
		this.height = height;
		return this;
	}

	public SpriteResourceBuilder scale(float scale) {
		this.scale = scale;
		return this;
	}

	public SpriteResourceBuilder flip(boolean flop, boolean flip) {
		this.flip = flip;
		this.flop = flop;
		return this;
	}

	public SpriteResourceBuilder texture(String textureId) {
		this.textureId = textureId;
		return this;
	}

	public SpriteResourceBuilder textureAtlas(String textureAtlasId, String region) {
		return textureAtlas(textureAtlasId, region, AtlasRegionNoIndex);
	}

	public SpriteResourceBuilder textureAtlas(String textureAtlasId, String region, int index) {
		this.textureAtlasId = textureAtlasId;
		this.regionId = region;
		this.regionIndex = index;
		return this;
	}

	public SpriteResourceBuilder(ResourceManager<String> resourceManager) {
		this.resourceManager = resourceManager;
		this.x = 0;
		this.y = 0;
		this.width = 0;
		this.height = 0;
	}

	@Override
	public Sprite build() {
		if (textureId != null) {
			Texture texture = resourceManager.getResourceValue(textureId);

			int w = width != 0 ? width : texture.getWidth();
			int h = height != 0 ? height : texture.getHeight();

			Sprite sprite = new Sprite(texture, x, y, w, h);
			sprite.setSize(sprite.getWidth() * scale, sprite.getHeight() * scale);
			sprite.flip(flop, flip);
			return sprite;
		} else if (textureAtlasId != null) {
			if (region == null) {
				TextureAtlas textureAtlas = resourceManager.getResourceValue(textureAtlasId);

				try {
					region = textureAtlas.findRegion(regionId, regionIndex);
				} catch (Exception e) {
					throw new RuntimeException("Failed to load AtlasRegion " + regionId + " with index " + regionIndex + " from TextureAtlas " + textureAtlasId, e);
				}

				if (region == null)
					throw new RuntimeException("AtlasRegion " + regionId + " with index " + regionIndex + " from TextureAtlas " + textureAtlasId + " not found");

			}
			// note that whis resource will not be updated if the resource of the texture atlas changed...
			Sprite sprite = new Sprite(region);
			sprite.flip(flop, flip);
			sprite.setSize(sprite.getWidth() * scale, sprite.getHeight() * scale);
			return sprite;
		}
		throw new RuntimeException("failed to create sprite neither textureId nor textureAtlasId specified");
	}

	@Override
	public boolean isVolatile() {
		return true;
	}

}