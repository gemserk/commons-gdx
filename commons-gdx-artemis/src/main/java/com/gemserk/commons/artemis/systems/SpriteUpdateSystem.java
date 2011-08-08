package com.gemserk.commons.artemis.systems;

import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.gemserk.commons.artemis.EntityDebugger;
import com.gemserk.commons.artemis.components.SpatialComponent;
import com.gemserk.commons.artemis.components.SpriteComponent;
import com.gemserk.commons.gdx.games.Spatial;

public class SpriteUpdateSystem extends EntitySystem {

	@SuppressWarnings("unchecked")
	public SpriteUpdateSystem() {
		super(SpatialComponent.class, SpriteComponent.class);
	}

	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		for (int i = 0; i < entities.size(); i++) {

			Entity entity = entities.get(i);

			SpatialComponent spatialComponent = entity.getComponent(SpatialComponent.class);
			SpriteComponent spriteComponent = entity.getComponent(SpriteComponent.class);

			if (spatialComponent == null) {
				EntityDebugger.debug("spatial component missing in drawable entity", entity);
				continue;
			}

			if (spriteComponent == null) {
				EntityDebugger.debug("sprite component missing in drawable entity", entity);
				continue;
			}

			Spatial spatial = spatialComponent.getSpatial();

			Sprite sprite = spriteComponent.getSprite();
			Vector2 center = spriteComponent.getCenter();

			if (spriteComponent.isUpdateRotation())
				sprite.setRotation(spatial.getAngle());
			sprite.setOrigin(spatial.getWidth() * center.x, spatial.getHeight() * center.y);

			sprite.setSize(spatial.getWidth(), spatial.getHeight());
			sprite.setPosition(spatial.getX() - sprite.getOriginX(), spatial.getY() - sprite.getOriginY());

		}
	}

	@Override
	public void initialize() {

	}

	@Override
	protected boolean checkProcessing() {
		return true;
	}
}