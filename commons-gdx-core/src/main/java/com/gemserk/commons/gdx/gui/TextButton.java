package com.gemserk.commons.gdx.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.gemserk.animation4j.transitions.Transitions;
import com.gemserk.animation4j.transitions.sync.Synchronizers;
import com.gemserk.commons.gdx.graphics.SpriteBatchUtils;
import com.gemserk.commons.gdx.input.LibgdxPointer;
import com.gemserk.commons.gdx.math.MathUtils2;

public class TextButton {

	private float x, y;

	private BitmapFont font;

	private String text;

	private Rectangle bounds;

	private boolean pressed;

	private boolean released;

	private LibgdxPointer libgdxPointer = new LibgdxPointer(0);

	private Color color = new Color(1f, 1f, 1f, 1f);

	private Color overColor = new Color(1f, 1f, 1f, 1f);

	private Color notOverColor = new Color(0.5f, 0.5f, 0.5f, 1f);

	private boolean wasInside;

	public void setColor(Color color) {
		this.color.set(color);
	}

	public void setOverColor(Color overColor) {
		this.overColor.set(overColor);
	}

	public void setNotOverColor(Color notOverColor) {
		this.notOverColor.set(notOverColor);
	}

	public TextButton(BitmapFont font, String text, float x, float y) {
		this.font = font;
		this.text = text;
		this.x = x;
		this.y = y;

		TextBounds bounds = font.getBounds(text);

		float w = bounds.width;
		float h = bounds.height;

		this.bounds = new Rectangle(x - w * 0.5f, y - h * 0.5f, w, h);

		color.set(notOverColor);
	}

	public void draw(SpriteBatch spriteBatch) {
		font.setColor(color);
		SpriteBatchUtils.drawCentered(spriteBatch, font, text, x, y);
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

		// NOTE: for now the button could be released without never being pressed before

	}

}