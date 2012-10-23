package com.gemserk.commons.gdx.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.gemserk.commons.gdx.graphics.SpriteBatchUtils;

public class Text extends ControlImpl {

	private CharSequence text;
	private float cx, cy;
	private Color color = new Color(Color.WHITE);
	private BitmapFont font;
	private float scale = 1f;

	private boolean roundPosition = true;

	public Text setText(CharSequence text) {
		this.text = text;
		return this;
	}

	public Text setColor(Color color) {
		this.color.set(color);
		return this;
	}

	public void setColor(float r, float g, float b, float a) {
		this.color.set(r, g, b, a);
	}

	public void setFont(BitmapFont font) {
		this.font = font;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public void setRoundPosition(boolean roundPosition) {
		this.roundPosition = roundPosition;
	}

	public boolean isRoundPosition() {
		return roundPosition;
	}

	/**
	 * Used to center the text over (x,y) coordinates.
	 * 
	 * @param cx
	 *            A value between 0 and 1 to center over horizontal axis.
	 * @param cy
	 *            A value between 0 and 1 to center over vertical axis.
	 */
	public void setCenter(float cx, float cy) {
		this.cx = cx;
		this.cy = cy;
	}

	public CharSequence getText() {
		return text;
	}

	public Color getColor() {
		return color;
	}

	public BitmapFont getFont() {
		return font;
	}

	public Text(CharSequence text, float x, float y) {
		this(text, x, y, 0.5f, 0.5f);
	}

	public Text(CharSequence text, float x, float y, float cx, float cy) {
		this("", text, x, y, cx, cy);
	}

	public Text(CharSequence text) {
		this(text, 0f, 0f, 0.5f, 0.5f);
	}

	public Text(String id, CharSequence text, float x, float y, float cx, float cy) {
		setId(id);
		this.text = text;
		setX(x);
		setY(y);
		this.cx = cx;
		this.cy = cy;
	}

	public void draw(SpriteBatch spriteBatch, BitmapFont font) {
		if (!isVisible())
			return;
		font.setColor(color);
		float x = getX();
		float y = getY();

		SpriteBatchUtils.drawMultilineTextWithAlignment(spriteBatch, font, text, x, y, cx, cy, HAlignment.LEFT, roundPosition);
	}

	@Override
	public void update() {

	}

	@Override
	public void draw(SpriteBatch spriteBatch) {
		if (font.getScaleX() != this.scale)
			font.setScale(this.scale);
		draw(spriteBatch, font);
	}

	@Override
	public void setParent(Control parent) {
		this.parent = parent;
	}

}