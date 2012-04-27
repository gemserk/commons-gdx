package com.gemserk.commons.gdx.scene2d.ui;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class SpriteActor extends Actor {

	private final Sprite sprite;
	private final String name;

	public String getName() {
		return name;
	}

	public SpriteActor(Sprite title, String name) {
		this.sprite = title;
		this.name = name;
	}

	@Override
	public Actor hit(float x, float y) {
		return null;
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		sprite.draw(batch);
	}
}