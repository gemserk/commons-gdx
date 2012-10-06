package com.gemserk.commons.artemis.systems;

import com.artemis.Entity;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.gemserk.commons.artemis.components.Components;
import com.gemserk.commons.artemis.components.FrustumCullingComponent;
import com.gemserk.commons.artemis.components.ParticleEmitterComponent;
import com.gemserk.commons.artemis.components.RenderableComponent;
import com.gemserk.commons.artemis.components.SpatialComponent;
import com.gemserk.commons.artemis.components.SpriteComponent;
import com.gemserk.commons.artemis.components.TextComponent;
import com.gemserk.commons.gdx.camera.Libgdx2dCamera;
import com.gemserk.commons.gdx.games.Spatial;
import com.gemserk.commons.gdx.graphics.SpriteBatchUtils;
import com.gemserk.componentsengine.utils.RandomAccessMap;

public class RenderLayerSpriteBatchImpl implements RenderLayer {

	class EntityComponents {
		public RenderableComponent renderableComponent;
		public FrustumCullingComponent frustumCullingComponent;
		public SpatialComponent spatialComponent;
		public SpriteComponent spriteComponent;
		public TextComponent textComponent;
		public ParticleEmitterComponent particleEmitterComponent;
	}

	private final SpriteBatch spriteBatch;
	// private final OrderedByLayerEntities orderedByLayerEntities;
	private final OrderedByLayerRenderables orderedByLayerRenderables;
	private final Libgdx2dCamera camera;
	private boolean enabled;

	private final Rectangle frustum = new Rectangle();
	private final Rectangle entityBounds = new Rectangle();
	private Factory factory;

	private boolean ownsSpriteBatch;
	
	private boolean blending;

	public void setBlending(boolean blending) {
		this.blending = blending;
	}
	
	public RenderLayerSpriteBatchImpl(int minLayer, int maxLayer, Libgdx2dCamera camera, SpriteBatch spriteBatch, boolean blending) {
		this.camera = camera;
		this.spriteBatch = spriteBatch;
		// this.orderedByLayerEntities = new OrderedByLayerEntities(minLayer, maxLayer);
		this.orderedByLayerRenderables = new OrderedByLayerRenderables(minLayer, maxLayer);
		this.enabled = true;
		this.factory = new Factory();
		this.ownsSpriteBatch = false;
		this.blending = blending;
	}
	
	public RenderLayerSpriteBatchImpl(int minLayer, int maxLayer, Libgdx2dCamera camera, SpriteBatch spriteBatch) {
		this(minLayer, maxLayer, camera, spriteBatch, true);
	}

	public RenderLayerSpriteBatchImpl(int minLayer, int maxLayer, Libgdx2dCamera camera) {
		this(minLayer, maxLayer, camera, new SpriteBatch());
		this.ownsSpriteBatch = true;
	}

	@Override
	public void init() {

	}

	@Override
	public void dispose() {
		if (ownsSpriteBatch)
			spriteBatch.dispose();
	}

	@Override
	public boolean belongs(Renderable renderable) {
		return orderedByLayerRenderables.belongs(renderable);
	}

	@Override
	public void add(Renderable renderable) {
		factory.add(renderable.entity);
		orderedByLayerRenderables.add(renderable);
	}

	@Override
	public void remove(Renderable renderable) {
		orderedByLayerRenderables.remove(renderable);
		factory.add(renderable.entity);
	}

	@Override
	public void render() {
		camera.getFrustum(frustum);
		camera.apply(spriteBatch);

		RandomAccessMap<Entity, EntityComponents> entityComponents = factory.entityComponents;

		if (blending && !spriteBatch.isBlendingEnabled())
			spriteBatch.enableBlending();
		else if (!blending && spriteBatch.isBlendingEnabled())
			spriteBatch.disableBlending();
		
		spriteBatch.begin();
		for (int i = 0; i < orderedByLayerRenderables.size(); i++) {
			Renderable renderable = orderedByLayerRenderables.get(i);
			
			// RenderableComponent renderableComponent = components.renderableComponent;
			// if (!renderableComponent.isVisible())
			// continue;

			if (!renderable.isVisible())
				continue;

			EntityComponents components = entityComponents.get(renderable.getEntity());
			FrustumCullingComponent frustumCullingComponent = components.frustumCullingComponent;
			if (frustumCullingComponent != null) {

				Spatial spatial = components.spatialComponent.getSpatial();

				entityBounds.set(frustumCullingComponent.bounds);

				entityBounds.setX(entityBounds.getX() + spatial.getX());
				entityBounds.setY(entityBounds.getY() + spatial.getY());

				if (!frustum.overlaps(entityBounds))
					continue;
			}

			SpriteComponent spriteComponent = components.spriteComponent;
			if (spriteComponent != null) {
				Sprite sprite = spriteComponent.getSprite();
				sprite.setColor(spriteComponent.getColor());
				sprite.draw(spriteBatch);
			}
			// don't like it will be asking for components all the time.
			TextComponent textComponent = components.textComponent;
			if (textComponent != null) {
				BitmapFont font = textComponent.font;

				if (font.getScaleX() != textComponent.scale) {
					font.setScale(textComponent.scale);
				}

				font.setColor(textComponent.color);
				SpriteBatchUtils.drawMultilineText(spriteBatch, font, //
						textComponent.text, textComponent.x, textComponent.y, textComponent.cx, textComponent.cy);
			}
			ParticleEmitterComponent particleEmitterComponent = components.particleEmitterComponent;
			if (particleEmitterComponent != null) {
				particleEmitterComponent.particleEmitter.draw(spriteBatch);
			}
		}
		spriteBatch.end();
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	class Factory extends EntityComponentsFactory<EntityComponents> {

		@Override
		public EntityComponents newInstance() {
			return new EntityComponents();
		}

		@Override
		public void free(EntityComponents entityComponent) {
			entityComponent.renderableComponent = null;
			entityComponent.frustumCullingComponent = null;
			entityComponent.spatialComponent = null;
			entityComponent.spriteComponent = null;
			entityComponent.textComponent = null;
			entityComponent.particleEmitterComponent = null;
		}

		@Override
		public void load(Entity e, EntityComponents entityComponent) {
			entityComponent.renderableComponent = Components.getRenderableComponent(e);
			entityComponent.frustumCullingComponent = Components.getFrustumCullingComponent(e);
			entityComponent.spatialComponent = Components.getSpatialComponent(e);
			entityComponent.spriteComponent = Components.getSpriteComponent(e);
			entityComponent.textComponent = Components.getTextComponent(e);
			entityComponent.particleEmitterComponent = Components.getParticleEmitterComponent(e);
		}
	}

}