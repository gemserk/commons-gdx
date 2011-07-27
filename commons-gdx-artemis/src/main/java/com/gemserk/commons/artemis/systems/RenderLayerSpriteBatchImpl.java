package com.gemserk.commons.artemis.systems;

import com.artemis.Entity;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.gemserk.commons.artemis.components.SpriteComponent;
import com.gemserk.commons.gdx.camera.Libgdx2dCamera;

public class RenderLayerSpriteBatchImpl implements RenderLayer {

	private static final SpriteComponentComparator spriteComponentComparator = new SpriteComponentComparator();

	private final int minLayer, maxLayer;
	private final SpriteBatch spriteBatch;

	Array<Entity> orderedByLayerEntities = new Array<Entity>();

	Libgdx2dCamera camera;

	public RenderLayerSpriteBatchImpl(int minLayer, int maxLayer, Libgdx2dCamera camera, SpriteBatch spriteBatch) {
		this.minLayer = minLayer;
		this.maxLayer = maxLayer;
		this.camera = camera;
		this.spriteBatch = spriteBatch;
	}
	
	public RenderLayerSpriteBatchImpl(int minLayer, int maxLayer, Libgdx2dCamera camera) {
		this.minLayer = minLayer;
		this.maxLayer = maxLayer;
		this.camera = camera;
		this.spriteBatch = new SpriteBatch();
	}
	
	@Override
	public void init() {
		
	}
	
	@Override
	public void dispose() {
		spriteBatch.dispose();
	}

	@Override
	public boolean belongs(Entity entity) {
		SpriteComponent spriteComponent = entity.getComponent(SpriteComponent.class);
		if (spriteComponent == null)
			return false;
		return spriteComponent.getLayer() >= minLayer && spriteComponent.getLayer() < maxLayer;
	}

	@Override
	public void add(Entity entity) {
		orderedByLayerEntities.add(entity);
		orderedByLayerEntities.sort(spriteComponentComparator);
	}

	@Override
	public void remove(Entity entity) {
		orderedByLayerEntities.removeValue(entity, true);
	}

	@Override
	public void render() {
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