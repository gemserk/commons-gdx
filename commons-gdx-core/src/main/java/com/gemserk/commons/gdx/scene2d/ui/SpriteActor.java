package com.gemserk.commons.gdx.scene2d.ui;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.gemserk.commons.gdx.graphics.SpriteUtils;

public class SpriteActor extends Actor {

	private final Sprite sprite;
	
	float cx, cy;
	
	public void setCenter(float cx, float cy) {
		this.cx = cx;
		this.cy = cy;
	}
	
	public Sprite getSprite() {
		return sprite;
	}

	public SpriteActor(Sprite sprite, String name) {
		this.sprite = sprite;
		setName(name);
	}

	@Override
	public Actor hit(float x, float y, boolean touchable) {
		return null;
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		sprite.setSize(this.getWidth(), this.getHeight());
		SpriteUtils.centerOn(sprite, this.getX(), this.getY(), cx, cy);
		sprite.setRotation(this.getRotation());
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		sprite.draw(batch, getColor().a * parentAlpha);
	}
	
}