package com.gemserk.commons.gdx.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gemserk.commons.gdx.graphics.SpriteBatchUtils;

public class Text implements Control {

	private String id;
	private float x, y;
	private CharSequence text;
	private float cx, cy;
	private boolean visible = true;
	private Color color = new Color(Color.WHITE);
	private BitmapFont font;

	public Text setVisible(boolean visible) {
		this.visible = visible;
		return this;
	}

	public boolean isVisible() {
		return visible;
	}

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

	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public void setX(float x) {
		this.x = x;
	}

	@Override
	public void setY(float y) {
		this.y = y;
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
		this.id = id;
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

	@Override
	public void update() {

	}

	@Override
	public void draw(SpriteBatch spriteBatch) {
		draw(spriteBatch, font);
	}

	@Override
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public float getX() {
		return x;
	}

	@Override
	public float getY() {
		return y;
	}

}