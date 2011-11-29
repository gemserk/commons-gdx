package com.gemserk.commons.gdx.gui;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.gemserk.commons.gdx.graphics.SpriteBatchUtils;
import com.gemserk.commons.gdx.input.LibgdxPointer;
import com.gemserk.commons.gdx.input.Pointer;
import com.gemserk.commons.gdx.math.MathUtils2;

public class ToggleableImageButton extends ControlImpl {

	public static class ToggleHandler {

		public void onToggle(boolean value) {
		}

	}

	boolean enabled;

	Sprite enabledSprite;
	Sprite disabledSprite;

	Rectangle bounds;
	Pointer pointer;

	float cx, cy;
	float width, height;

	ToggleHandler toggleHandler = new ToggleHandler();

	public void setCenter(float cx, float cy) {
		this.cx = cx;
		this.cy = cy;
		invalidate();
	}

	public void setSize(float width, float height) {
		this.width = width;
		this.height = height;
		invalidate();
	}

	public void setEnabledSprite(Sprite enabledSprite) {
		this.enabledSprite = enabledSprite;
		invalidate();
	}

	public void setDisabledSprite(Sprite disabledSprite) {
		this.disabledSprite = disabledSprite;
		invalidate();
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		invalidate();
	}

	void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

	public void setPointer(Pointer pointer) {
		this.pointer = pointer;
	}

	public ToggleableImageButton setToggleHandler(ToggleHandler toggleHandler) {
		this.toggleHandler = toggleHandler;
		return this;
	}

	public ToggleableImageButton() {
		pointer = new LibgdxPointer(0);
		bounds = new Rectangle();
	}

	private void toggle() {
		enabled = !enabled;
		toggleHandler.onToggle(enabled);
		invalidate();
	}

	public void draw(SpriteBatch spriteBatch) {
		if (!isVisible())
			return;
		if (enabled)
			SpriteBatchUtils.drawCentered(spriteBatch, enabledSprite, getX(), getY(), width, height, 0f, cx, cy);
		else
			SpriteBatchUtils.drawCentered(spriteBatch, disabledSprite, getX(), getY(), width, height, 0f, cx, cy);
	}

	@Override
	public void update() {

		if (!isValid()) {
			recalculateBounds();
			validate();
		}

		pointer.update();

		if (!pointer.wasReleased())
			return;

		if (!MathUtils2.inside(bounds, pointer.getReleasedPosition()))
			return;

		toggle();

	}

	protected void recalculateBounds() {
		this.bounds.set(getX() - width * cx, getY() - height * cy, width, height);
	}

}
