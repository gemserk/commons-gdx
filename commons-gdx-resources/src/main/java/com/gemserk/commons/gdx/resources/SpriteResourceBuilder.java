package com.gemserk.commons.gdx.resources;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasSprite;
import com.gemserk.commons.gdx.graphics.SpriteUtils;
import com.gemserk.resources.ResourceManager;

public class SpriteResourceBuilder implements ResourceBuilder<Sprite> {

	static final int AtlasRegionNoIndex = -1;

	private int x, y, width, height;

	private float cx, cy;
	private float scale = 1f;

	private ResourceManager<String> resourceManager;
	private String textureId;
	private String textureAtlasId;
	private String regionId;

	private Sprite spriteRegion = null;

	private boolean flip = false;
	private boolean flop = false;

	private boolean rotate90 = false;
	private boolean clockwise = false;

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

	/**
	 * Sets the relative center of the Sprite.
	 */
	public SpriteResourceBuilder center(float cx, float cy) {
		this.cx = cx;
		this.cy = cy;
		return this;
	}

	public SpriteResourceBuilder flip(boolean flop, boolean flip) {
		this.flip = flip;
		this.flop = flop;
		return this;
	}

	public SpriteResourceBuilder rotate90(boolean clockwise) {
		this.rotate90 = true;
		this.clockwise = clockwise;
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
		this.cx = 0.5f;
		this.cy = 0.5f;
	}

	@Override
	public Sprite build() {
		if (textureId != null) {
			Texture texture = resourceManager.getResourceValue(textureId);

			int w = width != 0 ? width : texture.getWidth();
			int h = height != 0 ? height : texture.getHeight();

			Sprite sprite = new Sprite(texture, x, y, w, h);

			SpriteUtils.transformSprite(sprite, scale, cx, cy, flop, flip, rotate90, clockwise);

			return sprite;
		} else if (textureAtlasId != null) {
			if (spriteRegion == null) {
				TextureAtlas textureAtlas = resourceManager.getResourceValue(textureAtlasId);

				try {
					spriteRegion = textureAtlas.createSprite(regionId, regionIndex);
				} catch (Exception e) {
					throw new RuntimeException("Failed to load AtlasRegion " + regionId + " with index " + regionIndex + " from TextureAtlas " + textureAtlasId, e);
				}

				if (spriteRegion == null)
					throw new RuntimeException("AtlasRegion " + regionId + " with index " + regionIndex + " from TextureAtlas " + textureAtlasId + " not found");

			}
			// note that whis resource will not be updated if the resource of the texture atlas changed...
			Sprite sprite = null;

			if (sprite instanceof AtlasSprite)
				sprite = new AtlasSprite(((AtlasSprite) spriteRegion).getAtlasRegion());
			else
				sprite = new Sprite(spriteRegion);

			SpriteUtils.transformSprite(sprite, scale, cx, cy, flop, flip, rotate90, clockwise);

			return sprite;
		}
		throw new RuntimeException("failed to create sprite neither textureId nor textureAtlasId specified");
	}

	@Override
	public boolean isVolatile() {
		return true;
	}

}