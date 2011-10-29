package com.gemserk.commons.artemis.systems;

import com.artemis.Entity;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gemserk.commons.artemis.components.Components;
import com.gemserk.commons.artemis.components.RenderableComponent;
import com.gemserk.commons.artemis.components.SpriteComponent;
import com.gemserk.commons.artemis.components.TextComponent;
import com.gemserk.commons.gdx.camera.Libgdx2dCamera;
import com.gemserk.commons.gdx.graphics.SpriteBatchUtils;

public class RenderLayerSpriteBatchImpl implements RenderLayer {

	private final SpriteBatch spriteBatch;
	private final OrderedByLayerEntities orderedByLayerEntities;
	private final Libgdx2dCamera camera;
	private boolean enabled;

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
		camera.apply(spriteBatch);
		spriteBatch.begin();
		for (int i = 0; i < orderedByLayerEntities.size(); i++) {
			Entity e = orderedByLayerEntities.get(i);
			RenderableComponent renderableComponent = Components.getRenderableComponent(e);
			if (!renderableComponent.isVisible())
				continue;
			SpriteComponent spriteComponent = Components.spriteComponent(e);
			if (spriteComponent != null) {
				Sprite sprite = spriteComponent.getSprite();
				sprite.setColor(spriteComponent.getColor());
				sprite.draw(spriteBatch);
			}
			TextComponent textComponent = Components.getTextComponent(e);
			if (textComponent != null) {
				textComponent.font.setColor(textComponent.color);
				SpriteBatchUtils.drawMultilineText(spriteBatch, textComponent.font, //
						textComponent.text, textComponent.x, textComponent.y, textComponent.cx, textComponent.cy);
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