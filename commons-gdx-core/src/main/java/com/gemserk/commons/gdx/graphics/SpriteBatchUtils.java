package com.gemserk.commons.gdx.graphics;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class SpriteBatchUtils {

	/**
	 * Draws a text centered in the specified coordinates
	 */
	public static void drawCentered(SpriteBatch spriteBatch, BitmapFont font, CharSequence text, float x, float y) {
		TextBounds bounds = font.getBounds(text);
		font.draw(spriteBatch, text, x - bounds.width * 0.5f, y + bounds.height * 0.5f);
	}

	/**
	 * Draws a multi line text centered.
	 */
	public static void drawMultilineTextCentered(SpriteBatch spriteBatch, BitmapFont font, CharSequence text, float x, float y) {
		drawMultilineText(spriteBatch, font, text, x, y, 0.5f, 0.5f);
	}

	/**
	 * Draws a multi line text in the specified coordinates (x,y). It will use cx and cy to center the text over the coordinates (x,y).
	 * 
	 * @param spriteBatch
	 * @param font
	 * @param text
	 * @param x
	 * @param y
	 * @param cx
	 *            A value between 0 and 1 to center the text over the horizontal axis.
	 * @param cy
	 *            A value between 0 and 1 to center the text over the vertical axis.
	 */
	public static void drawMultilineText(SpriteBatch spriteBatch, BitmapFont font, CharSequence text, float x, float y, float cx, float cy) {
		// TextBounds bounds = font.getMultiLineBounds(text);
		// font.drawMultiLine(spriteBatch, text, x - bounds.width * cx, y + bounds.height * cy);
		drawMultilineTextWithAlignment(spriteBatch, font, text, x, y, cx, cy, HAlignment.LEFT);
	}

	public static void drawMultilineTextWithAlignment(SpriteBatch spriteBatch, BitmapFont font, CharSequence text, float x, float y, float cx, float cy, HAlignment alignment) {
		drawMultilineTextWithAlignment(spriteBatch, font, text, x, y, cx, cy, alignment, false);
	}

	public static void drawMultilineTextWithAlignment(SpriteBatch spriteBatch, BitmapFont font, CharSequence text, float x, float y, float cx, float cy, HAlignment alignment, boolean roundPosition) {
		TextBounds bounds = font.getMultiLineBounds(text);
		float centerx = getCenterForAlignment(cx, alignment, bounds);
		if (roundPosition)
			font.drawMultiLine(spriteBatch, text, Math.round(x + centerx), Math.round(y + bounds.height * cy), 0f, alignment);
		else
			font.drawMultiLine(spriteBatch, text, x + centerx, y + bounds.height * cy, 0f, alignment);
	}

	private static float getCenterForAlignment(float cx, HAlignment alignment, TextBounds bounds) {
		if (alignment == HAlignment.RIGHT)
			return bounds.width * (1f - cx);
		if (alignment == HAlignment.CENTER)
			return bounds.width * (0.5f - cx);
		return -bounds.width * cx;
	}

	public static Rectangle getBounds(BitmapFont font, CharSequence text, float x, float y) {
		return getBounds(font, text, x, y, 0f, 0f);
	}

	public static Rectangle getBounds(BitmapFont font, CharSequence text, float x, float y, float sx, float sy) {
		TextBounds bounds = font.getMultiLineBounds(text);
		float w = bounds.width;
		float h = bounds.height;
		return new Rectangle(x - sx - w * 0.5f, y - sy - h * 0.5f, w + 2 * sx, h + 2 * sy);
	}

	/**
	 * Draws a Sprite centered.
	 */
	public static void drawCentered(SpriteBatch spriteBatch, Sprite sprite, float x, float y, float w, float h, float angle) {
		drawCentered(spriteBatch, sprite, x, y, w, h, angle, 0.5f, 0.5f);
	}

	/**
	 * Draws a Sprite centered.
	 */
	public static void drawCentered(SpriteBatch spriteBatch, Sprite sprite, float x, float y, float w, float h, float angle, float cx, float cy) {
		sprite.setSize(w, h);
		sprite.setOrigin(w * cx, h * cy);
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

	/**
	 * If text width exceeds viewportWidth * limit, it returns the corresponding scale to make the textWidht be equal to viewportWidth * limit.
	 */
	public static float calculateScaleForText(float viewportWidth, float textWidth, float limit) {
		if (textWidth < viewportWidth * limit)
			return 1f;
		return viewportWidth / textWidth * limit;
	}

}
