package com.gemserk.commons.gdx.resources;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class SpriteResourceBuilder implements ResourceBuilder<Sprite> {

	// TODO: maybe if we create it specifying a texture atlas region then it doesnt need to specify other values.

	private int x;

	private int y;

	private int width;

	private int height;

	private Texture texture;

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

	public SpriteResourceBuilder(Texture texture) {
		this.texture = texture;
		this.x = 0;
		this.y = 0;
		this.width = texture.getWidth();
		this.height = texture.getHeight();
	}

	@Override
	public Sprite build() {
		return new Sprite(texture, x, y, width, height);
	}

	@Override
	public boolean isCached() {
		return false;
	}

}