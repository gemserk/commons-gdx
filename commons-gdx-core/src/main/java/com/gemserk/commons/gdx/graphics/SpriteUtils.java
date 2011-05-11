package com.gemserk.commons.gdx.graphics;

import com.badlogic.gdx.graphics.g2d.Sprite;

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
	 * Resizes the sprite to the specified width maintaining the aspect ration.
	 */
	public static void resize(Sprite sprite, float width) {
		float aspect = (float) sprite.getHeight() / (float) sprite.getWidth();
		float height = width * aspect;
		sprite.setSize(width, height);
	}

	/**
	 * Centers the sprite on the given position.
	 */
	public static void centerOn(Sprite sprite, float x, float y) {
		sprite.setOrigin(sprite.getWidth() * 0.5f, sprite.getHeight() * 0.5f);
		sprite.setPosition(x - sprite.getOriginX(), y - sprite.getOriginY());
	}

}
