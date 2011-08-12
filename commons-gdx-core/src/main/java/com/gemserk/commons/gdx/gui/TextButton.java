package com.gemserk.commons.gdx.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.gemserk.animation4j.transitions.Transitions;
import com.gemserk.animation4j.transitions.sync.Synchronizers;
import com.gemserk.commons.gdx.graphics.SpriteBatchUtils;
import com.gemserk.commons.gdx.input.LibgdxPointer;
import com.gemserk.commons.gdx.math.MathUtils2;

public class TextButton implements Control {

	private String id;
	private float x, y;
	private float cx, cy;
	private float w, h;
	private float offsetX, offsetY;

	private BitmapFont font;
	private String text;
	private Rectangle bounds = new Rectangle();
	private boolean pressed;
	private boolean released;
	private LibgdxPointer libgdxPointer = new LibgdxPointer(0);
	private Color color = new Color(1f, 1f, 1f, 1f);
	private Color overColor = new Color(1f, 1f, 1f, 1f);
	private Color notOverColor = new Color(0.5f, 0.5f, 0.5f, 1f);
	private boolean wasInside;
	private HAlignment alignment = HAlignment.LEFT;
	private ButtonHandler buttonHandler = new ButtonHandler();

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public TextButton setColor(Color color) {
		this.color.set(color);
		return this;
	}

	public TextButton setOverColor(Color c) {
		return this.setOverColor(c.r, c.g, c.b, c.a);
	}
	
	public TextButton setOverColor(float r, float g, float b, float a) {
		this.overColor.set(r, g, b, a);
		if (wasInside)
			setColor(this.overColor);
		return this;
	}

	public TextButton setNotOverColor(Color c) {
		return this.setNotOverColor(c.r, c.g, c.b, c.a);
	}

	public TextButton setNotOverColor(float r, float g, float b, float a) {
		this.notOverColor.set(r, g, b, a);
		if (!wasInside)
			setColor(this.notOverColor);
		return this;
	}

	public TextButton setText(String text) {
		this.text = text;
		recalculateBoundsSize(text);
		recalculateBounds();
		return this;
	}

	private void recalculateBounds() {
		this.bounds.set(x - w * cx - offsetX * 0.5f, y - h * cy - offsetY * 0.5f, w + offsetX, h + offsetY);
	}

	private void recalculateBoundsSize(String text) {
		Rectangle textBounds = SpriteBatchUtils.getBounds(font, text, x, y);
		w = textBounds.getWidth();
		h = textBounds.getHeight();
	}

	public TextButton setButtonHandler(ButtonHandler buttonHandler) {
		this.buttonHandler = buttonHandler;
		return this;
	}

	public ButtonHandler getButtonHandler() {
		return buttonHandler;
	}

	/**
	 * Increment size of the bounds by the specified w,h
	 */
	public TextButton setBoundsOffset(float offsetX, float offsetY) {
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		recalculateBounds();
		return this;
	}

	public void setFont(BitmapFont font) {
		this.font = font;
		recalculateBoundsSize(text);
		recalculateBounds();
	}

	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
		recalculateBounds();
	}

	public void setCenter(float cx, float cy) {
		this.cx = cx;
		this.cy = cy;
		recalculateBounds();
	}

	public Color getOverColor() {
		return overColor;
	}

	public Color getNotOverColor() {
		return notOverColor;
	}

	public TextButton() {
		this.cx = 0.5f;
		this.cy = 0.5f;
		recalculateBounds();
		this.id = "";
	}

	public TextButton(BitmapFont font, String text, float x, float y) {
		this.font = font;
		this.text = text;
		this.x = x;
		this.y = y;
		color.set(notOverColor);
		this.cx = 0.5f;
		this.cy = 0.5f;
		recalculateBounds();
		this.id = "";
	}

	public TextButton setAlignment(HAlignment alignment) {
		this.alignment = alignment;
		return this;
	}

	public void draw(SpriteBatch spriteBatch) {
		font.setColor(color);
		SpriteBatchUtils.drawMultilineTextWithAlignment(spriteBatch, font, text, x, y, cx, cy, alignment);
		// ImmediateModeRendererUtils.drawRectangle(bounds.x, bounds.y, bounds.x + bounds.width, bounds.y + bounds.height, Color.GREEN);
	}

	public boolean isPressed() {
		return pressed;
	}

	public boolean isReleased() {
		return released;
	}

	public void update() {

		libgdxPointer.update();

		pressed = false;
		released = false;

		boolean inside = MathUtils2.inside(bounds, libgdxPointer.getPosition());

		if (wasInside && !inside)
			Synchronizers.transition(color, Transitions.transitionBuilder(color).end(notOverColor).time(150));

		if (!wasInside && inside) {
			Synchronizers.transition(color, Transitions.transitionBuilder(color).end(overColor).time(150));
		}

		wasInside = inside;

		if (libgdxPointer.wasPressed)
			pressed = MathUtils2.inside(bounds, libgdxPointer.getPressedPosition());

		if (libgdxPointer.wasReleased)
			released = MathUtils2.inside(bounds, libgdxPointer.getReleasedPosition());

		if (pressed)
			buttonHandler.onPressed(this);

		if (released)
			buttonHandler.onReleased(this);

		// NOTE: for now the button could be released while it was never pressed before

	}

	@Override
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}