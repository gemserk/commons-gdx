package com.gemserk.commons.gdx.resources;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.gemserk.resources.ResourceManager;

public class SpriteResourceBuilder implements ResourceBuilder<Sprite> {

	private int x;
	private int y;
	private int width;
	private int height;
	private ResourceManager<String> resourceManager;
	private String textureId;
	private String textureAtlasId;
	private String regionId;

	private AtlasRegion region;
	
	private boolean flip = false;
	private boolean flop = false;

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
		this.textureAtlasId = textureAtlasId;
		this.regionId = region;
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
			Sprite sprite = new Sprite(texture, x, y, width != 0 ? width : texture.getWidth(), height != 0 ? height : texture.getHeight());
			sprite.flip(flop, flip);
			return sprite;
		} else if (textureAtlasId != null) {
			if (region == null) {
				TextureAtlas textureAtlas = resourceManager.getResourceValue(textureAtlasId);
				region = textureAtlas.findRegion(regionId);
			}
			// note that whis resource will not be updated if the resource of the texture atlas changed...
			Sprite sprite = new Sprite(region);
			sprite.flip(flop, flip);
			return sprite;
		}
		throw new RuntimeException("failed to create sprite neither textureId nor textureAtlasId specified");
	}

	@Override
	public boolean isVolatile() {
		return true;
	}

}