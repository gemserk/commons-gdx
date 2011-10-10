package com.gemserk.commons.gdx.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.gemserk.commons.gdx.graphics.SpriteBatchUtils;
import com.gemserk.commons.gdx.input.LibgdxPointer;
import com.gemserk.commons.gdx.math.MathUtils2;

public class ImageButton extends ControlImpl {

	ButtonHandler buttonHandler;
	Sprite sprite;
	LibgdxPointer libgdxPointer;
	Color color;

	float w, h;
	float cx, cy;

	Rectangle bounds;
	
	public void setColor(Color color) {
		this.color.set(color);
	}

	public void setColor(float r, float g, float b, float a) {
		this.color.set(r, g, b, a);
	}
	
	public Color getColor() {
		return color;
	}

	public void setCenter(float cx, float cy) {
		this.cx = cx;
		this.cy = cy;
		invalidate();
	}

	public void setSize(float w, float h) {
		this.w = w;
		this.h = h;
		invalidate();
	}

	public void setButtonHandler(ButtonHandler buttonHandler) {
		this.buttonHandler = buttonHandler;
	}

	public ImageButton(Sprite sprite) {
		this("", sprite);
	}

	public ImageButton(String id, Sprite sprite) {
		this.id = id;
		this.sprite = sprite;
		this.buttonHandler = new ButtonHandler();
		this.color = new Color(1f, 1f, 1f, 1f);
		this.libgdxPointer = new LibgdxPointer(0);
		this.w = sprite.getWidth();
		this.h = sprite.getHeight();
		this.bounds = new Rectangle(0, 0, w, h);
		this.cx = 0.5f;
		this.cy = 0.5f;
	}

	public void draw(SpriteBatch spriteBatch) {
		sprite.setColor(color);
		sprite.setSize(w, h);
		SpriteBatchUtils.drawCentered(spriteBatch, sprite, getX(), getY(), w, h, 0f, cx, cy);
	}
	
	boolean wasInside;

	public void update() {

		if (!isValid()) {
			recalculateBounds();
			validate();
		}

		libgdxPointer.update();

		boolean pressed = false;
		boolean released = false;
		
		boolean inside = MathUtils2.inside(bounds, libgdxPointer.getPosition());
		
		if (inside && !wasInside) {
			buttonHandler.onOver(this);
			wasInside = true;
		} else if (!inside && wasInside) {
			buttonHandler.onLeave(this);
			wasInside = false;
		}

		if (libgdxPointer.wasPressed)
			pressed = MathUtils2.inside(bounds, libgdxPointer.getPressedPosition());

		if (libgdxPointer.wasReleased)
			released = MathUtils2.inside(bounds, libgdxPointer.getReleasedPosition());

		if (pressed)
			buttonHandler.onPressed(this);

		if (released)
			buttonHandler.onReleased(this);

	}

	public void recalculateBounds() {
		this.bounds.set(getX() - w * cx, getY() - h * cy, w, h);
	}

}