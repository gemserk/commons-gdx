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

	static class EntityComponents {
		public RenderableComponent renderableComponent;
		public FrustumCullingComponent frustumCullingComponent;
		public SpatialComponent spatialComponent;
		public SpriteComponent spriteComponent;
		public TextComponent textComponent;
		public ParticleEmitterComponent particleEmitterComponent;
	}

	public static class OptimizationParameters {
		public boolean beginBatch;
		public boolean endBatch;
		public boolean updateCamera;

		public static OptimizationParameters optimizationsDisabled() {
			OptimizationParameters parameters = new OptimizationParameters();
			parameters.beginBatch = parameters.endBatch = parameters.updateCamera = true;
			return parameters;
		}

		public static OptimizationParameters optimizationsEnabled() {
			OptimizationParameters parameters = new OptimizationParameters();
			parameters.beginBatch = parameters.endBatch = parameters.updateCamera = false;
			return parameters;
		}

		public OptimizationParameters setBeginBatch(boolean beginBatch) {
			this.beginBatch = beginBatch;
			return this;
		}

		public OptimizationParameters setEndBatch(boolean endBatch) {
			this.endBatch = endBatch;
			return this;
		}

		public OptimizationParameters setUpdateCamera(boolean updateCamera) {
			this.updateCamera = updateCamera;
			return this;
		}
	}

	private final SpriteBatch spriteBatch;
	private final OrderedByLayerRenderables orderedByLayerRenderables;
	private final Libgdx2dCamera camera;
	private boolean enabled;

	private final Rectangle frustum = new Rectangle();
	private final Rectangle entityBounds = new Rectangle();
	private Factory factory;

	private boolean ownsSpriteBatch;

	private boolean blending;
	private OptimizationParameters optimizationParameters;

	public void setBlending(boolean blending) {
		this.blending = blending;
	}

	public RenderLayerSpriteBatchImpl(int minLayer, int maxLayer, Libgdx2dCamera camera, SpriteBatch spriteBatch, boolean blending, OptimizationParameters optimizationParameters) {
		this.camera = camera;
		this.spriteBatch = spriteBatch;
		this.optimizationParameters = optimizationParameters;
		this.orderedByLayerRenderables = new OrderedByLayerRenderables(minLayer, maxLayer);
		this.enabled = true;
		this.factory = new Factory();
		this.ownsSpriteBatch = false;
		this.blending = blending;
	}

	public RenderLayerSpriteBatchImpl(int minLayer, int maxLayer, Libgdx2dCamera camera2, SpriteBatch spriteBatch2, boolean blending) {
		this(minLayer, maxLayer, camera2, spriteBatch2, blending, OptimizationParameters.optimizationsDisabled());
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

		if (optimizationParameters.updateCamera)
			camera.apply(spriteBatch);

		RandomAccessMap<Entity, EntityComponents> entityComponents = factory.entityComponents;

		if (blending && !spriteBatch.isBlendingEnabled())
			spriteBatch.enableBlending();
		else if (!blending && spriteBatch.isBlendingEnabled())
			spriteBatch.disableBlending();

		if (optimizationParameters.beginBatch)
			spriteBatch.begin();
		for (int i = 0; i < orderedByLayerRenderables.size(); i++) {
			Renderable renderable = orderedByLayerRenderables.get(i);

			// RenderableComponent renderableComponent =
			// components.renderableComponent;
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
		if (optimizationParameters.endBatch)
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
