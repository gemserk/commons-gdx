package com.gemserk.commons.gdx.gui;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.gemserk.commons.gdx.graphics.SpriteBatchUtils;
import com.gemserk.commons.gdx.input.LibgdxPointer;
import com.gemserk.commons.gdx.math.MathUtils2;

public class ToggleableImageButton {
	
	public static class ToggleHandler {
		
		public void onToggle(boolean value) {}
		
	}
	
	float x,y;
	
	boolean enabled;
	
	Sprite enabledSprite;
	
	Sprite disabledSprite;
	
	Rectangle bounds;
	
	LibgdxPointer libgdxPointer = new LibgdxPointer(0);
	
	ToggleHandler toggleHandler = new ToggleHandler();

	public ToggleableImageButton setPosition(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}
	
	public ToggleableImageButton setEnabledSprite(Sprite enabledSprite) {
		this.enabledSprite = enabledSprite;
		return this;
	}
	
	public ToggleableImageButton setDisabledSprite(Sprite disabledSprite) {
		this.disabledSprite = disabledSprite;
		return this;
	}
	
	public ToggleableImageButton setEnabled(boolean enabled) {
		this.enabled = enabled;
		return this;
	}
	
	public ToggleableImageButton setBounds(Rectangle bounds) {
		this.bounds = bounds;
		return this;
	}
	
	public ToggleableImageButton setToggleHandler(ToggleHandler toggleHandler) {
		this.toggleHandler = toggleHandler;
		return this;
	}
	
	public ToggleableImageButton() {

	}
	
	private void toggle() {
		enabled = !enabled;
		toggleHandler.onToggle(enabled);
	}
	
	public void draw(SpriteBatch spriteBatch) {
		if (enabled) 
			SpriteBatchUtils.drawCentered(spriteBatch, enabledSprite, x, y, 0f);
		else
			SpriteBatchUtils.drawCentered(spriteBatch, disabledSprite, x, y, 0f);
	}
	
	public void udpate(int delta) {
		
		libgdxPointer.update();
		
		if (!libgdxPointer.wasPressed)
			return;
		
		Vector2 pressedPosition = libgdxPointer.getPressedPosition();
		
		if (!MathUtils2.inside(bounds, pressedPosition.x - x, pressedPosition.y - y)) 
			return;
		
		toggle();
		
	}

}
