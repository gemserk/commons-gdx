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
import com.gemserk.commons.artemis.components.SpriteComponent;
import com.gemserk.commons.artemis.components.TextComponent;
import com.gemserk.commons.gdx.camera.Libgdx2dCamera;
import com.gemserk.commons.gdx.games.Spatial;
import com.gemserk.commons.gdx.graphics.SpriteBatchUtils;

public class RenderLayerSpriteBatchImpl implements RenderLayer {

	private final SpriteBatch spriteBatch;
	private final OrderedByLayerEntities orderedByLayerEntities;
	private final Libgdx2dCamera camera;
	private boolean enabled;

	private final Rectangle frustum = new Rectangle();
	private final Rectangle entityBounds = new Rectangle();
	
	public RenderLayerSpriteBatchImpl(int minLayer, int maxLayer, Libgdx2dCamera camera, SpriteBatch spriteBatch) {
		this.camera = camera;
		this.spriteBatch = spriteBatch;
		this.orderedByLayerEntities = new OrderedByLayerEntities(minLayer, maxLayer);
		this.enabled = true;
	}

	public RenderLayerSpriteBatchImpl(int minLayer, int maxLayer, Libgdx2dCamera camera) {
		this(minLayer, maxLayer, camera, new SpriteBatch());
	}

	@Override
	public void init() {

	}

	@Override
	public void dispose() {
		spriteBatch.dispose();
	}

	@Override
	public boolean belongs(Entity e) {
		RenderableComponent renderableComponent = Components.getRenderableComponent(e);
		return orderedByLayerEntities.belongs(renderableComponent.getLayer());
	}

	@Override
	public void add(Entity entity) {
		orderedByLayerEntities.add(entity);
	}

	@Override
	public void remove(Entity entity) {
		orderedByLayerEntities.remove(entity);
	}

	@Override
	public void render() {
		camera.getFrustum(frustum);
		camera.apply(spriteBatch);
		spriteBatch.begin();
		for (int i = 0; i < orderedByLayerEntities.size(); i++) {
			Entity e = orderedByLayerEntities.get(i);
			RenderableComponent renderableComponent = Components.getRenderableComponent(e);
			if (!renderableComponent.isVisible())
				continue;
			

			FrustumCullingComponent frustumCullingComponent = Components.getFrustumCullingComponent(e);
			if (frustumCullingComponent != null) {

				Spatial spatial = Components.getSpatialComponent(e).getSpatial();
				
				entityBounds.set(frustumCullingComponent.bounds);
				entityBounds.setX(spatial.getX());
				entityBounds.setY(spatial.getY());
				
				if (!frustum.overlaps(entityBounds))
					continue;
			}
			
			SpriteComponent spriteComponent = Components.getSpriteComponent(e);
			if (spriteComponent != null) {
				Sprite sprite = spriteComponent.getSprite();
				sprite.setColor(spriteComponent.getColor());
				sprite.draw(spriteBatch);
			}
			// don't like it will be asking for components all the time.
			TextComponent textComponent = Components.getTextComponent(e);
			if (textComponent != null) {
				BitmapFont font = textComponent.font;
				
				if (font.getScaleX() != textComponent.scale) {
					font.setScale(textComponent.scale);
				}
				
				font.setColor(textComponent.color);
				SpriteBatchUtils.drawMultilineText(spriteBatch, font, //
						textComponent.text, textComponent.x, textComponent.y, textComponent.cx, textComponent.cy);
			}
			ParticleEmitterComponent particleEmitterComponent = Components.getParticleEmitterComponent(e);
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

}