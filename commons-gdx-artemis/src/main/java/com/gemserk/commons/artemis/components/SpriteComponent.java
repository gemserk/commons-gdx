package com.gemserk.commons.artemis.components;

import com.artemis.Component;
import com.artemis.ComponentTypeManager;
import com.artemis.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.gemserk.commons.gdx.g2d.SpriteMesh;

public class SpriteComponent extends Component {

	public static final int type = ComponentTypeManager.getTypeFor(SpriteComponent.class).getId();

	public static SpriteComponent get(Entity e) {
		return (SpriteComponent) e.getComponent(type);
	}

	public static interface SpriteRenderable {

		void setRotation(float angle);

		void setOrigin(float ox, float oy);

		void setPosition(float x, float y);

		void setSize(float width, float height);

	}

	public static class SpriteRenderableSpriteImpl implements SpriteRenderable {

		public Sprite sprite;

		public SpriteRenderableSpriteImpl(Sprite sprite) {
			this.sprite = sprite;
		}

		@Override
		public void setRotation(float angle) {
			if (Float.compare(angle, sprite.getRotation()) != 0)
				sprite.setRotation(angle);
		}

		@Override
		public void setOrigin(float ox, float oy) {
			if (Float.compare(ox, sprite.getOriginX()) != 0 || Float.compare(oy, sprite.getOriginY()) != 0)
				sprite.setOrigin(ox, oy);
		}

		@Override
		public void setPosition(float x, float y) {
			float newX = x - sprite.getOriginX();
			float newY = y - sprite.getOriginY();
			if (Float.compare(newX, sprite.getX()) != 0 || Float.compare(newY, sprite.getY()) != 0)
				sprite.setPosition(newX, newY);
		}

		@Override
		public void setSize(float width, float height) {
			if (Float.compare(width, sprite.getWidth()) != 0 || Float.compare(height, sprite.getHeight()) != 0)
				sprite.setSize(width, height);
		}

	}

	public static class SpriteRenderableSpriteMeshImpl implements SpriteRenderable {

		public SpriteMesh spriteMesh;

		public SpriteRenderableSpriteMeshImpl(SpriteMesh spriteMesh) {
			this.spriteMesh = spriteMesh;
		}

		@Override
		public void setRotation(float angle) {
			spriteMesh.setRotation(angle);
		}

		@Override
		public void setOrigin(float ox, float oy) {
			spriteMesh.setOrigin(ox, oy);
		}

		@Override
		public void setPosition(float x, float y) {
			float newX = x - spriteMesh.getOriginX();
			float newY = y - spriteMesh.getOriginY();
			spriteMesh.setPosition(newX, newY);
		}

		@Override
		public void setSize(float width, float height) {
			spriteMesh.setSize(width, height);
		}

	}

	private SpriteRenderableSpriteImpl spriteRenderable;
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

	public Sprite getSprite() {
		return spriteRenderable.sprite;
		// return sprite;
	}

	// Used right now to set an animation frame, another option could be to implement a common interface which returns different sprite on getSprite().
	public void setSprite(Sprite sprite) {
		spriteRenderable.sprite = sprite;
		// this.sprite = sprite;
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
		this(new SpriteRenderableSpriteImpl(sprite), cx, cy, color);
	}

	public SpriteComponent(SpriteRenderableSpriteImpl spriteRenderable, float cx, float cy, Color color) {
		this.spriteRenderable = spriteRenderable;
		this.color = new Color(color);
		this.center = new Vector2(cx, cy);
	}

	public void setSpriteRotation(float angle) {
		if (!isUpdateRotation())
			return;
		spriteRenderable.setRotation(angle);
	}

	public void setSpriteOriginFromSize(float width, float height) {
		float ox = width * center.x;
		float oy = height * center.y;
		spriteRenderable.setOrigin(ox, oy);
	}

	public void setSpriteSize(float width, float height) {
		spriteRenderable.setSize(width, height);
	}

	public void setSpritePosition(float x, float y) {
		spriteRenderable.setPosition(x, y);
	}

}
