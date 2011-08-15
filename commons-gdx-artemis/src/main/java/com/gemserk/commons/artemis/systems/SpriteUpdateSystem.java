package com.gemserk.commons.artemis.systems;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.gemserk.commons.artemis.components.SpatialComponent;
import com.gemserk.commons.artemis.components.SpriteComponent;
import com.gemserk.commons.gdx.games.Spatial;

public class SpriteUpdateSystem extends EntityProcessingSystem {

	ComponentMapper<SpatialComponent> spatialComponentMapper;
	ComponentMapper<SpriteComponent> spriteComponentMapper;

	@SuppressWarnings("unchecked")
	public SpriteUpdateSystem() {
		super(SpatialComponent.class, SpriteComponent.class);
	}

	@Override
	public void initialize() {
		spatialComponentMapper = new ComponentMapper<SpatialComponent>(SpatialComponent.class, world.getEntityManager());
		spriteComponentMapper = new ComponentMapper<SpriteComponent>(SpriteComponent.class, world.getEntityManager());
	}

	@Override
	protected void process(Entity e) {
		SpatialComponent spatialComponent = spatialComponentMapper.get(e);
		SpriteComponent spriteComponent = spriteComponentMapper.get(e);

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