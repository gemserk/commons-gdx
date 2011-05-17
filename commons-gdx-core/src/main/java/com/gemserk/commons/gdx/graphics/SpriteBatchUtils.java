package com.gemserk.commons.gdx.graphics;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SpriteBatchUtils {
	
	/**
	 * Draws a text centered in the specified coordinates
	 */
	public static void drawCentered(SpriteBatch spriteBatch, BitmapFont font, String text, float x, float y) {
		TextBounds bounds = font.getBounds(text);
		font.draw(spriteBatch, text, x - bounds.width * 0.5f, y + bounds.height * 0.5f);
	}

	/**
	 * Draws a multi line text centered.
	 */
	public static void drawMultilineTextCentered(SpriteBatch spriteBatch, BitmapFont font, String text, float x, float y) {
		TextBounds bounds = font.getMultiLineBounds(text);
		font.drawMultiLine(spriteBatch, text, x - bounds.width * 0.5f, y + bounds.height * 0.5f);
	}
	
	/**
	 * Draws a Sprite centered.
	 */
	public static void drawCentered(SpriteBatch spriteBatch, Sprite sprite, float x, float y, float w, float h, float angle) {
		sprite.setSize(w, h);
		sprite.setOrigin(w * 0.5f, h * 0.5f);
		sprite.setPosition(x - sprite.getOriginX(), y - sprite.getOriginY());
		sprite.setRotation(angle);
		sprite.draw(spriteBatch);
	}
	
	/**
	 * Draws a Sprite centered.
	 */
	public static void drawCentered(SpriteBatch spriteBatch, Sprite sprite, float x, float y, float angle) {
		drawCentered(spriteBatch, sprite, x, y, sprite.getWidth(), sprite.getHeight(), angle);
	}
}
