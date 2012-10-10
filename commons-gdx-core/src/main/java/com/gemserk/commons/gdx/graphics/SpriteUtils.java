package com.gemserk.commons.gdx.graphics;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasSprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Libgdx Sprite utils
 * 
 * @author acoppes
 */
public class SpriteUtils {

	/**
	 * As Sprite is a TextureRegion, this method lets you calculate the original width of the sprite based on the internal texture coordinates.
	 * 
	 * @param sprite
	 *            The sprite to be calculated.
	 */
	public static int getOriginalWidth(Sprite sprite) {
		float width = (sprite.getU2() - sprite.getU()) * sprite.getTexture().getWidth();
		return (int) width;
	}

	/**
	 * As Sprite is a TextureRegion, this method lets you calculate the original height of the sprite based on the internal texture coordinates.
	 * 
	 * @param sprite
	 *            The sprite to be calculated.
	 */
	public static int getOriginalHeight(Sprite sprite) {
		float height = (sprite.getV2() - sprite.getV()) * sprite.getTexture().getHeight();
		return (int) height;
	}

	/**
	 * Resizes the sprite to the specified width maintaining the aspect ratio.
	 * 
	 * @param sprite
	 *            The Sprite to modify.
	 * @param width
	 *            The desired width of the Sprite.
	 */
	public static void resize(Sprite sprite, float width) {
		float aspect = (float) sprite.getHeight() / (float) sprite.getWidth();
		float height = width * aspect;
		sprite.setSize(width, height);
	}

	public static void resizeUsingHeightKeepAspectRatio(Sprite sprite, float height) {
		float aspect = (float) sprite.getHeight() / (float) sprite.getWidth();
		float width = height / aspect;
		sprite.setSize(width, height);
	}

	/**
	 * Centers the sprite on the given position.
	 */
	public static void centerOn(Sprite sprite, float x, float y) {
		centerOn(sprite, x, y, 0.5f, 0.5f);
	}

	/**
	 * Centers the sprite on the given position.
	 */
	public static void centerOn(Sprite sprite, float x, float y, float cx, float cy) {
		// sprite.setOrigin(sprite.getWidth() * cx, sprite.getHeight() * cy);
		center(sprite, cx, cy);
		sprite.setPosition(x - sprite.getOriginX(), y - sprite.getOriginY());
	}

	/**
	 * Sets the center of the sprite.
	 * 
	 * @param sprite
	 *            The Sprite to be centered.
	 * @param cx
	 *            A normalized value of the x coordinate of the center of the sprite (normally between [0,1])
	 * @param cy
	 *            A normalized value of the y coordinate of the center of the sprite (normally between [0,1])
	 */
	public static void center(Sprite sprite, float cx, float cy) {
		sprite.setOrigin(sprite.getWidth() * cx, sprite.getHeight() * cy);
	}

	/**
	 * Transform the specified sprite given the scale, center, flip and flop, and if the sprite should be rotated 90 clockwise or counterclockwise. Used mainly by the resource builders.
	 * 
	 * @param sprite
	 *            The sprite to transform.
	 * @param scale
	 *            The scale the sprite should be scaled by.
	 * @param cx
	 *            The center of x coordinate.
	 * @param cy
	 *            The center of the y coordinate.
	 * @param flop
	 *            If the sprite should be mirrored horizontally or not.
	 * @param flip
	 *            If the sprite should be mirrored vertically or not.
	 * @param rotate90
	 *            If it should be rotated 90 degrees or not.
	 * @param clockwise
	 *            The direciton of the rotation if it should be rotated.
	 */
	public static void transformSprite(Sprite sprite, float scale, float cx, float cy, boolean flop, boolean flip, boolean rotate90, boolean clockwise) {
		if (!rotate90) {
			sprite.setSize(sprite.getWidth() * scale, sprite.getHeight() * scale);
		} else {
			sprite.rotate90(clockwise);
			sprite.setSize(sprite.getHeight() * scale, sprite.getWidth() * scale);
		}

		SpriteUtils.center(sprite, cx, cy);
		sprite.flip(flop, flip);
	}

	/**
	 * Modifies the size of the Sprite using its current width and height and the specified scale.
	 * 
	 * @param sprite
	 *            The Sprite to modify.
	 * @param scale
	 *            The scale to use when changing the Sprite size.
	 */
	public static void scale(Sprite sprite, float scale) {
		sprite.setSize(sprite.getWidth() * scale, sprite.getHeight() * scale);
	}

	/**
	 * Returns a clone of a Sprite based on the type of the sprite.
	 */
	public static Sprite cloneSprite(Sprite sprite) {
		if (sprite instanceof AtlasSprite) {
			AtlasSprite atlasSprite = new AtlasSprite(((AtlasSprite) sprite).getAtlasRegion());
			atlasSprite.set(sprite);
			return atlasSprite;
		} else {
			return new Sprite(sprite);
		}
	}

	/**
	 * Returns true if both Sprites could be considered as alias by checking their regions values.
	 */
	public static boolean isAliasSprite(Sprite sprite1, Sprite sprite2) {
		boolean isAtlasSprite1 = sprite1 instanceof AtlasSprite;
		boolean isAtlasSprite2 = sprite2 instanceof AtlasSprite;

		if (isAtlasSprite1 != isAtlasSprite2)
			return false;

		if (!isAtlasSprite1) {
			return TextureRegionUtils.textureRegionEquals(sprite1, sprite2);
		} else {
			AtlasSprite atlasSprite1 = (AtlasSprite) sprite1;
			AtlasSprite atlasSprite2 = (AtlasSprite) sprite2;
			boolean regionEquals = TextureRegionUtils.atlasRegionEquals(atlasSprite1.getAtlasRegion(), atlasSprite2.getAtlasRegion());
			return regionEquals && TextureRegionUtils.textureRegionEquals(sprite1, sprite2);
		}
	}

	/**
	 * Modifies the Sprite region to the given TextureRegion by the SpriteRegion and its internal inner region relative values.
	 * 
	 * @param sprite
	 *            The Sprite to be modified.
	 * @param spriteRegion
	 *            The SpriteRegion with the values to be used when cutting the Sprite.
	 */
	public static void cut(Sprite sprite, SpriteRegion spriteRegion) {
		TextureRegion textureRegion = spriteRegion.textureRegion;
		float u0 = spriteRegion.u0;
		float v0 = spriteRegion.v0;
		float u1 = spriteRegion.u1;
		float v1 = spriteRegion.v1;
		float width = spriteRegion.width;
		float height = spriteRegion.height;
		cut(sprite, textureRegion, u0, v0, u1, v1, width, height);
	}

	/**
	 * Modifies the Sprite region to the given TextureRegion and inner region relative values.
	 * 
	 * @param sprite
	 *            The Sprite to be modified.
	 * @param textureRegion
	 *            The TextureRegion to be used as the original region.
	 */
	public static void cut(Sprite sprite, TextureRegion textureRegion, float u0, float v0, float u1, float v1, float width, float height) {
		int regionX = textureRegion.getRegionX();
		int regionY = textureRegion.getRegionY();
		int regionWidth = textureRegion.getRegionWidth();
		int regionHeight = textureRegion.getRegionHeight();

		float innerWidth = u1 - u0;
		float innerHeight = v1 - v0;

		int newRegionX = (int) (regionX + u0 * regionWidth);
		int newRegionY = (int) (regionY + v0 * regionHeight);

		int newRegionWidth = (int) (innerWidth * regionWidth);
		int newRegionHeight = (int) (innerHeight * regionHeight);

		sprite.setRegion(newRegionX, newRegionY, newRegionWidth, newRegionHeight);

		sprite.setSize(innerWidth * width, innerHeight * height);
	}

}
