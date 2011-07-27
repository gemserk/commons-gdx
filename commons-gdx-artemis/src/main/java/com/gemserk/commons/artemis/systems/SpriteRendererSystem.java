package com.gemserk.commons.artemis.systems;

import java.util.ArrayList;

import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.utils.Disposable;
import com.gemserk.commons.artemis.components.SpriteComponent;
import com.gemserk.commons.gdx.camera.Libgdx2dCamera;
import com.gemserk.commons.gdx.camera.Libgdx2dCameraTransformImpl;

public class SpriteRendererSystem extends EntitySystem implements Disposable {

	private ArrayList<RenderLayer> renderLayers;

	public SpriteRendererSystem() {
		this(new Libgdx2dCameraTransformImpl());
	}

	@SuppressWarnings("unchecked")
	public SpriteRendererSystem(Libgdx2dCamera camera) {
		super(SpriteComponent.class);
		// default layers
		this.renderLayers = new ArrayList<RenderLayer>();
		renderLayers.add(new RenderLayer(-1000, 1000, camera));
	}

	@SuppressWarnings("unchecked")
	public SpriteRendererSystem(ArrayList<RenderLayer> renderLayers) {
		super(SpriteComponent.class);
		this.renderLayers = renderLayers;
	}

	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		for (int i = 0; i < renderLayers.size(); i++) {
			RenderLayer renderLayer = renderLayers.get(i);
			renderLayer.draw();
		}
	}

	@Override
	protected void added(Entity entity) {
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
	protected void removed(Entity entity) {
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
			RenderLayer renderLayer = renderLayers.get(i);
			renderLayer.dispose();
		}
	}
}