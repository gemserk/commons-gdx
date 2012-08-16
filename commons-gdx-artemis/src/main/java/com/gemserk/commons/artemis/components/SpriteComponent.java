package com.gemserk.commons.artemis.components;

import com.artemis.Component;
import com.artemis.ComponentTypeManager;
import com.artemis.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class SpriteComponent extends Component {

	public static final int type = ComponentTypeManager.getTypeFor(SpriteComponent.class).getId();

	public static SpriteComponent get(Entity e) {
		return (SpriteComponent) e.getComponent(type);
	}
	
	private Sprite sprite;
	private Color color;

	// this is the hot spot for the transformations and it is relative to the size of the sprite
	private Vector2 center; // x and y values between 0,1
	
	private boolean updateRotation = true;
	
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
	
	/**
	 * Returns the coordinate x of the original center relative to the sprite size.
	 */
	public float getRelativeCenterX() {
		float width = sprite.getWidth();
		return width * 0.5f - width * center.x;
	}

	/**
	 * Returns the coordinate x of the original center relative to the sprite size.
	 */
	public float getRelativeCenterY() {
		float height = sprite.getHeight();
		return height * 0.5f - height * center.y;
	}
	
	public Color getColor() {
		return color;
	}

	public SpriteComponent(Sprite sprite, Vector2 center, Color color) {
		this(sprite, center.x, center.y, color);
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
