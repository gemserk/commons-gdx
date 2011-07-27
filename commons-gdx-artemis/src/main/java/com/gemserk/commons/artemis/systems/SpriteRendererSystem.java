package com.gemserk.commons.artemis.systems;

import java.util.ArrayList;

import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.utils.Disposable;
import com.gemserk.commons.artemis.components.RenderableComponent;
import com.gemserk.commons.gdx.camera.Libgdx2dCamera;
import com.gemserk.commons.gdx.camera.Libgdx2dCameraTransformImpl;

public class SpriteRendererSystem extends EntitySystem implements Disposable {

	private ArrayList<RenderLayer> renderLayerSpriteBatchImpls;

	public SpriteRendererSystem() {
		this(new Libgdx2dCameraTransformImpl());
	}

	@SuppressWarnings("unchecked")
	public SpriteRendererSystem(Libgdx2dCamera camera) {
		super(RenderableComponent.class);
		// default layers
		this.renderLayerSpriteBatchImpls = new ArrayList<RenderLayer>();
		renderLayerSpriteBatchImpls.add(new RenderLayerSpriteBatchImpl(-1000, 1000, camera));
	}

	@SuppressWarnings("unchecked")
	public SpriteRendererSystem(ArrayList<RenderLayer> renderLayerSpriteBatchImpls) {
		super(RenderableComponent.class);
		this.renderLayerSpriteBatchImpls = renderLayerSpriteBatchImpls;
	}

	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		for (int i = 0; i < renderLayerSpriteBatchImpls.size(); i++) {
			RenderLayer renderLayerSpriteBatchImpl = renderLayerSpriteBatchImpls.get(i);
			renderLayerSpriteBatchImpl.render();
		}
	}

	@Override
	protected void added(Entity entity) {
		// order the entity in the Layer, probably the same inside the layer
		for (int i = 0; i < renderLayerSpriteBatchImpls.size(); i++) {
			RenderLayer renderLayerSpriteBatchImpl = renderLayerSpriteBatchImpls.get(i);
			if (renderLayerSpriteBatchImpl.belongs(entity)) {
				renderLayerSpriteBatchImpl.add(entity);
				return;
			}
		}
	}

	@Override
	protected void removed(Entity entity) {
		// remove the order
		for (int i = 0; i < renderLayerSpriteBatchImpls.size(); i++) {
			RenderLayer renderLayerSpriteBatchImpl = renderLayerSpriteBatchImpls.get(i);
			if (renderLayerSpriteBatchImpl.belongs(entity)) {
				renderLayerSpriteBatchImpl.remove(entity);
				return;
			}
		}
	}

	@Override
	public void initialize() {
		for (int i = 0; i < renderLayerSpriteBatchImpls.size(); i++) {
			RenderLayer renderLayerSpriteBatchImpl = renderLayerSpriteBatchImpls.get(i);
			renderLayerSpriteBatchImpl.init();
		}
	}

	@Override
	protected boolean checkProcessing() {
		return true;
	}

	@Override
	public void dispose() {
		for (int i = 0; i < renderLayerSpriteBatchImpls.size(); i++) {
			RenderLayer renderLayerSpriteBatchImpl = renderLayerSpriteBatchImpls.get(i);
			renderLayerSpriteBatchImpl.dispose();
		}
	}
}