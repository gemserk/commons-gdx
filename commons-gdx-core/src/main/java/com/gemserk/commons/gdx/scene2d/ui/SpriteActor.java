package com.gemserk.commons.gdx.scene2d.ui;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.gemserk.commons.gdx.graphics.SpriteUtils;

public class SpriteActor extends Actor {

	private final Sprite sprite;
	private final String name;
	
	float cx, cy;
	
	public void setCenter(float cx, float cy) {
		this.cx = cx;
		this.cy = cy;
	}
	
	public Sprite getSprite() {
		return sprite;
	}

	public String getName() {
		return name;
	}

	public SpriteActor(Sprite sprite, String name) {
		this.sprite = sprite;
		this.name = name;
	}

	@Override
	public Actor hit(float x, float y) {
		return null;
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		sprite.setSize(this.width, this.height);
		SpriteUtils.centerOn(sprite, this.x, this.y, cx, cy);
		sprite.setRotation(this.rotation);
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		sprite.draw(batch,color.a * parentAlpha);
	}
	
}