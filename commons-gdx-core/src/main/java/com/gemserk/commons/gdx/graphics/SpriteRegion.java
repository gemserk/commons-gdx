package com.gemserk.commons.gdx.graphics;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Sprite region is a helper class to hold some values to be used when modifying a sprite texture region. 
 * It holds a copy of the original texture region and four values specifying a inner region of the Sprite in relative values. 
 * For example, if you want the full Sprite region then (0, 0, 1, 1) should be used as the inner region values, if you want 
 * the first quarter of the Sprite then (0, 0, 0.5f, 0.5f) should be used. 
 * 
 * Note: Doesn't support AtlasSprite yet.
 * 
 * @author acoppes
 * 
 */
public class SpriteRegion {

	public TextureRegion textureRegion;
	public float u0, v0, u1, v1;
	public float width, height;

	public SpriteRegion(TextureRegion textureRegion) {
		this.textureRegion = textureRegion;
	}

	/**
	 * Sets the four relative values of the inner region of the Sprite.
	 */
	public void setRegion(float u0, float v0, float u1, float v1) {
		this.u0 = u0;
		this.v0 = v0;
		this.u1 = u1;
		this.v1 = v1;
	}

	/**
	 * Sets the desired width and height of the sprite region.
	 */
	public void setSize(float width, float height) {
		this.width = width;
		this.height = height;
	}
}