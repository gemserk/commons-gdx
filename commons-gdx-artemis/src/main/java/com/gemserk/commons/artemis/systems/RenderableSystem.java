package com.gemserk.commons.artemis.systems;

import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.utils.Disposable;
import com.gemserk.commons.artemis.components.RenderableComponent;
import com.gemserk.commons.artemis.render.RenderLayers;
import com.gemserk.commons.gdx.camera.Libgdx2dCameraTransformImpl;

public class RenderableSystem extends EntitySystem implements Disposable {
	
	private RenderLayers renderLayers;

	@SuppressWarnings("unchecked")
	public RenderableSystem() {
		super(RenderableComponent.class);
		// default layers
		renderLayers = new RenderLayers();
		renderLayers.add("default", new RenderLayerSpriteBatchImpl(-1000, 1000, new Libgdx2dCameraTransformImpl()));
	}

	@SuppressWarnings("unchecked")
	public RenderableSystem(RenderLayers renderLayers) {
		super(RenderableComponent.class);
		this.renderLayers = renderLayers;
	}

	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		for (int i = 0; i < renderLayers.size(); i++) {
			RenderLayer renderLayer = renderLayers.get(i);
			if (!renderLayer.isEnabled())
				continue;
			renderLayer.render();
		}
	}

	@Override
	protected void enabled(Entity entity) {
		// order the entity in the Layer, probably the same inside the layer
		for (int i = 0; i < renderLayers.size(); i++) {
			RenderLayer renderLayer = renderLayers.get(i);
			if (renderLayer.belongs(entity)) {
				renderLayer.add(entity);
				return;
			}
		}
	}

	@Override
	protected void disabled(Entity entity) {
		// remove the order
		for (int i = 0; i < renderLayers.size(); i++) {
			RenderLayer renderLayer = renderLayers.get(i);
			if (renderLayer.belongs(entity)) {
				renderLayer.remove(entity);
				return;
			}
		}
	}

	@Override
	public void initialize() {
		for (int i = 0; i < renderLayers.size(); i++) {
			RenderLayer renderLayer = renderLayers.get(i);
			renderLayer.init();
		}
	}

	@Override
	protected boolean checkProcessing() {
		return true;
	}

	@Override
	public void dispose() {
		for (int i = 0; i < renderLayers.size(); i++) {
			RenderLayer renderLayerSpriteBatchImpl = renderLayers.get(i);
			renderLayerSpriteBatchImpl.dispose();
		}
	}
}