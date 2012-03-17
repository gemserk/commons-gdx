package com.gemserk.commons.gdx.scene2d.ui;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;

public class BackgroundImageButton extends ImageButton {

	Sprite innerImage;

	public BackgroundImageButton(NinePatch patch, Sprite innerImage) {
		super(patch);
		this.innerImage = innerImage;
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		float cx = 0.5f;
		float cy = 0.5f;
		float x = this.x + width * 0.5f * scaleX - innerImage.getWidth() * cx * innerImage.getScaleX();
		float y = this.y + height * 0.5f * scaleY - innerImage.getHeight() * cy * innerImage.getScaleY();
		
		if (x != innerImage.getX() || y != innerImage.getY())
			innerImage.setPosition(x, y);
		
		innerImage.draw(batch, parentAlpha);
	}
}