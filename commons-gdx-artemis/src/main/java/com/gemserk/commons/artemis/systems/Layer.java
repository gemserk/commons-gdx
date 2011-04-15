package com.gemserk.commons.artemis.systems;

import com.artemis.Entity;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.gemserk.commons.artemis.components.SpriteComponent;
import com.gemserk.commons.gdx.Libgdx2dCamera;

public class Layer {
	
	private static final SpriteComponentComparator spriteComponentComparator = new SpriteComponentComparator();
	
	Array<Entity> orderedByLayerEntities = new Array<Entity>();
	
	Libgdx2dCamera camera;

	private final int minLayer;

	private final int maxLayer;
	
	public Layer(int minLayer, int maxLayer, Libgdx2dCamera camera) {
		this.minLayer = minLayer;
		this.maxLayer = maxLayer;
		this.camera = camera;
	}
	
	public boolean belongs(Entity entity) {
		SpriteComponent spriteComponent = entity.getComponent(SpriteComponent.class);
		if (spriteComponent == null)
			return false;
		return spriteComponent.getLayer() >= minLayer && spriteComponent.getLayer() < maxLayer;
	}
	
	public void add(Entity entity) {
		orderedByLayerEntities.add(entity);
		orderedByLayerEntities.sort(spriteComponentComparator);
	}
	
	public void remove(Entity entity) {
		orderedByLayerEntities.removeValue(entity, true);
	}
	
	public void draw(SpriteBatch spriteBatch) {
		camera.apply(spriteBatch);
		spriteBatch.begin();
		for (int i = 0; i < orderedByLayerEntities.size; i++) {
			Entity entity = orderedByLayerEntities.get(i);
			SpriteComponent spriteComponent = entity.getComponent(SpriteComponent.class);
			Sprite sprite = spriteComponent.getSprite();
			sprite.setColor(spriteComponent.getColor());
			sprite.draw(spriteBatch);
		}
		spriteBatch.end();
	}
	
}