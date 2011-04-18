package com.gemserk.commons.artemis.systems;

import java.util.ArrayList;

import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.gemserk.commons.artemis.components.SpriteComponent;
import com.gemserk.commons.gdx.Libgdx2dCamera;
import com.gemserk.commons.gdx.Libgdx2dCameraTransformImpl;

public class SpriteRendererSystem extends EntitySystem {
	
	private SpriteBatch spriteBatch;

	public SpriteRendererSystem() {
		this(new Libgdx2dCameraTransformImpl());
	}

	@SuppressWarnings("unchecked")
	public SpriteRendererSystem(Libgdx2dCamera camera) {
		super(SpriteComponent.class);
		
		// TODO: move outside
		layers = new ArrayList<Layer>();
		layers.add(new Layer(-1000, -5, new Libgdx2dCameraTransformImpl()));
		layers.add(new Layer(-5, 10, camera));
		layers.add(new Layer(10, 1000, new Libgdx2dCameraTransformImpl()));
	}

	Array<Entity> orderedByLayerEntities = new Array<Entity>();
	
	ArrayList<Layer> layers;
	
	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		
		for (int i = 0; i < layers.size(); i++) {
			Layer layer = layers.get(i);
			layer.draw(spriteBatch);
		}

	}
	
	@Override
	protected void added(Entity entity) {
		// order the entity in the Layer, probably the same inside the layer
		
		for (int i = 0; i < layers.size(); i++) {
			Layer layer = layers.get(i);
			if (layer.belongs(entity)) {
				layer.add(entity);
				return;
			}
		}
		
	}
	
	@Override
	protected void removed(Entity entity) {
		// remove the order
		
		for (int i = 0; i < layers.size(); i++) {
			Layer layer = layers.get(i);
			if (layer.belongs(entity)) {
				layer.remove(entity);
				return;
			}
		}
		
	}

	@Override
	public void initialize() {
		spriteBatch = new SpriteBatch();
	}

	@Override
	protected boolean checkProcessing() {
		return true;
	}
}