package com.gemserk.commons.gdx.scene2d.ui;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;

public class BackgroundImageButton extends ImageButton {

	Image innerImage;

	public BackgroundImageButton(NinePatch patch, Image innerImage) {
		super(patch);
		this.innerImage = innerImage;
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		innerImage.x = x + width * 0.5f - innerImage.width * 0.5f;
		innerImage.y = y + height * 0.5f - innerImage.height * 0.5f;
		innerImage.draw(batch, parentAlpha);
	}
}