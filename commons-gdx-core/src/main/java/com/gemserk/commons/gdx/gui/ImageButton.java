package com.gemserk.commons.gdx.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.gemserk.commons.gdx.graphics.SpriteBatchUtils;
import com.gemserk.commons.gdx.gui.TextButton.ButtonHandler;
import com.gemserk.commons.gdx.input.LibgdxPointer;
import com.gemserk.commons.gdx.math.MathUtils2;

public class ImageButton implements Control {
	
	ButtonHandler buttonHandler;
	Sprite sprite;
	LibgdxPointer libgdxPointer;
	Color color;
	float x,y;
	float w,h;
	Rectangle bounds;
	
	public void setColor(Color color) {
		this.color.set(color);
	}

	public void setColor(float r, float g, float b, float a) {
		this.color.set(r, g, b, a);
	}

	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
		float cx = 0.5f;
		float cy = 0.5f;
		this.bounds.set(x - w * cx, y - h * cy, w, h);
	}
	
	public void setSize(float w, float h) {
		this.w = w;
		this.h = h;
		float cx = 0.5f;
		float cy = 0.5f;
		this.bounds.set(x - w * cx, y - h * cy, w, h);
	}
	
	public void setButtonHandler(ButtonHandler buttonHandler) {
		this.buttonHandler = buttonHandler;
	}
	
	public ImageButton(Sprite sprite) {
		this.sprite = sprite;
		this.buttonHandler = new ButtonHandler();
		this.color = new Color(1f, 1f, 1f, 1f);
		this.libgdxPointer = new LibgdxPointer(0);
		this.w = sprite.getWidth();
		this.h = sprite.getHeight();
		this.bounds = new Rectangle(0, 0, w, h);
	}
	
	public void draw(SpriteBatch spriteBatch) {
		sprite.setColor(color);
		sprite.setSize(w, h);
		SpriteBatchUtils.drawCentered(spriteBatch, sprite, x, y, 0f);
	}
	
	public void update() {
		libgdxPointer.update();

		boolean pressed = false;
		boolean released = false;

		if (libgdxPointer.wasPressed)
			pressed = MathUtils2.inside(bounds, libgdxPointer.getPressedPosition());

		if (libgdxPointer.wasReleased)
			released = MathUtils2.inside(bounds, libgdxPointer.getReleasedPosition());
		
		if (pressed)
			buttonHandler.onPressed();
		
		if (released)
			buttonHandler.onReleased();
		
	}
	
}