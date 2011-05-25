package com.gemserk.commons.gdx.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gemserk.commons.gdx.graphics.SpriteBatchUtils;

public class Text {

	private float x;

	private float y;

	private String text;

	private float cx, cy;

	private boolean visible = true;
	
	private Color color = new Color(Color.WHITE);

	public Text setVisible(boolean visible) {
		this.visible = visible;
		return this;
	}

	public boolean isVisible() {
		return visible;
	}

	public Text setText(String text) {
		this.text = text;
		return this;
	}
	
	public Text setColor(Color color) {
		this.color.set(color);
		return this;
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

	public String getText() {
		return text;
	}

	public Text(String text, float x, float y) {
		this(text, x, y, 0.5f, 0.5f);
	}

	public Text(String text, float x, float y, float cx, float cy) {
		this.text = text;
		this.x = x;
		this.y = y;
		this.cx = cx;
		this.cy = cy;
	}
	
	public void draw(SpriteBatch spriteBatch, BitmapFont font) {
		if (!isVisible())
			return;
		font.setColor(color);
		SpriteBatchUtils.drawMultilineText(spriteBatch, font, text, x, y, cx, cy);
	}

}