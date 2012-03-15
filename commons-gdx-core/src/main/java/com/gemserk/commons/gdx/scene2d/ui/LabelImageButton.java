package com.gemserk.commons.gdx.scene2d.ui;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class LabelImageButton extends ImageButton {

	private Label label;

	public LabelImageButton(NinePatch patch, Label label) {
		super(patch);
		this.label = label;
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		label.x = x + width * 0.5f - label.width * 0.5f;
		label.y = y + height * 0.5f - label.height * 0.5f;
		label.draw(batch, parentAlpha);
	}
}