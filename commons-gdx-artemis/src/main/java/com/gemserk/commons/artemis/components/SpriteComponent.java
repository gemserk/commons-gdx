package com.gemserk.commons.artemis.components;

import com.artemis.Component;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class SpriteComponent extends Component {

	private Sprite sprite;
	private Color color;
	private Vector2 center; // x and y values between 0,1
	private boolean updateRotation = true;
	
	public boolean shouldUpdate = true;
	
	public void setUpdateRotation(boolean updateRotation) {
		this.updateRotation = updateRotation;
	}
	
	public boolean isUpdateRotation() {
		return updateRotation;
	}

	public Sprite getSprite() {
		return sprite;
	}

	// Used right now to set an animation frame, another option could be to implement a common interface which returns different sprite on getSprite().
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

	public Vector2 getCenter() {
		return center;
	}

	public Color getColor() {
		return color;
	}

	public SpriteComponent(Sprite sprite, Vector2 center, Color color) {
		this.sprite = sprite;
		this.color = new Color(color);
		this.center = center;
	}

	public SpriteComponent(Sprite sprite, float cx, float cy, Color color) {
		this.sprite = sprite;
		this.color = new Color(color);
		this.center = new Vector2(cx, cy);
	}

	public SpriteComponent(Sprite sprite, Color color) {
		this(sprite, new Vector2(0.5f, 0.5f), color);
	}

	public SpriteComponent(Sprite sprite) {
		this(sprite, Color.WHITE);
	}

}
