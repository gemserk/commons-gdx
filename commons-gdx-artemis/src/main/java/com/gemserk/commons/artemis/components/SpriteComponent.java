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

	// public static interface SpriteRenderable {
	//
	// void setRotation(float angle);
	//
	// void setOrigin(float ox, float oy);
	//
	// void setPosition(float x, float y);
	//
	// void setSize(float width, float height);
	//
	// void flip(boolean flip, boolean flop);
	//
	// }
	//
	// public static class SpriteRenderableSpriteImpl implements SpriteRenderable {
	//
	// public Sprite sprite;
	//
	// public SpriteRenderableSpriteImpl(Sprite sprite) {
	// this.sprite = sprite;
	// }
	//
	// @Override
	// public void setRotation(float angle) {
	// if (Float.compare(angle, sprite.getRotation()) != 0)
	// sprite.setRotation(angle);
	// }
	//
	// @Override
	// public void setOrigin(float ox, float oy) {
	// if (Float.compare(ox, sprite.getOriginX()) != 0 || Float.compare(oy, sprite.getOriginY()) != 0)
	// sprite.setOrigin(ox, oy);
	// }
	//
	// @Override
	// public void setPosition(float x, float y) {
	// float newX = x - sprite.getOriginX();
	// float newY = y - sprite.getOriginY();
	// if (Float.compare(newX, sprite.getX()) != 0 || Float.compare(newY, sprite.getY()) != 0)
	// sprite.setPosition(newX, newY);
	// }
	//
	// @Override
	// public void setSize(float width, float height) {
	// if (Float.compare(width, sprite.getWidth()) != 0 || Float.compare(height, sprite.getHeight()) != 0)
	// sprite.setSize(width, height);
	// }
	//
	// @Override
	// public void flip(boolean flip, boolean flop) {
	// sprite.flip(flip, flop);
	// }
	//
	// }
	//
	// public static class SpriteRenderableSpriteMeshImpl implements SpriteRenderable {
	//
	// public SpriteMesh spriteMesh;
	//
	// public SpriteRenderableSpriteMeshImpl(SpriteMesh spriteMesh) {
	// this.spriteMesh = spriteMesh;
	// }
	//
	// @Override
	// public void setRotation(float angle) {
	// spriteMesh.setRotation(angle);
	// }
	//
	// @Override
	// public void setOrigin(float ox, float oy) {
	// spriteMesh.setOrigin(ox, oy);
	// }
	//
	// @Override
	// public void setPosition(float x, float y) {
	// float newX = x - spriteMesh.getOriginX();
	// float newY = y - spriteMesh.getOriginY();
	// spriteMesh.setPosition(newX, newY);
	// }
	//
	// @Override
	// public void setSize(float width, float height) {
	// spriteMesh.setSize(width, height);
	// }
	//
	// @Override
	// public void flip(boolean flip, boolean flop) {
	// spriteMesh.flip(flop, flip);
	// }
	//
	// }

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

	// /**
	// * Returns the coordinate x of the original center relative to the sprite size.
	// */
	// public float getRelativeCenterX() {
	// float width = sprite.getWidth();
	// return width * 0.5f - width * center.x;
	// }

	// /**
	// * Returns the coordinate x of the original center relative to the sprite size.
	// */
	// public float getRelativeCenterY() {
	// float height = sprite.getHeight();
	// return height * 0.5f - height * center.y;
	// }

	// Used right now to set an animation frame, another option could be to implement a common interface which returns different sprite on getSprite().
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
	
	public Sprite getSprite() {
		return sprite;
	}

	public Vector2 getCenter() {
		return center;
	}

	public Color getColor() {
		return color;
	}

	public SpriteComponent(Sprite sprite, Color color) {
		this(sprite, new Vector2(0.5f, 0.5f), color);
	}

	public SpriteComponent(Sprite sprite) {
		this(sprite, Color.WHITE);
	}

	public SpriteComponent(Sprite sprite, Vector2 center, Color color) {
		this(sprite, center.x, center.y, color);
	}

	public SpriteComponent(Sprite sprite, float cx, float cy, Color color) {
		this.sprite = sprite;
		this.color = new Color(color);
		this.center = new Vector2(cx, cy);
	}

	public void update(float x, float y, float angle, float width, float height) {
		
		if (isUpdateRotation()) {
			if (Float.compare(angle, sprite.getRotation()) != 0)
				sprite.setRotation(angle);
		}
		
		float ox = width * center.x;
		float oy = height * center.y;
		
		if (Float.compare(ox, sprite.getOriginX()) != 0 || Float.compare(oy, sprite.getOriginY()) != 0)
			sprite.setOrigin(ox, oy);
		
		if (Float.compare(width, sprite.getWidth()) != 0 || Float.compare(height, sprite.getHeight()) != 0)
			sprite.setSize(width, height);
		
		float newX = x - sprite.getOriginX();
		float newY = y - sprite.getOriginY();
		
		if (Float.compare(newX, sprite.getX()) != 0 || Float.compare(newY, sprite.getY()) != 0)
			sprite.setPosition(newX, newY);
	}
}
