package com.gemserk.commons.artemis.systems;

import java.util.Comparator;

import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.gemserk.commons.artemis.components.SpriteComponent;
import com.gemserk.commons.gdx.CameraTransformImpl;
import com.gemserk.commons.gdx.Libgdx2dCamera;

public class SpriteRendererSystem extends EntitySystem {

	private final class LayerComparator implements Comparator<Entity> {
		@Override
		public int compare(Entity o1, Entity o2) {
			SpriteComponent spriteComponent1 = o1.getComponent(SpriteComponent.class);
			SpriteComponent spriteComponent2 = o2.getComponent(SpriteComponent.class);
			return spriteComponent1.getLayer() - spriteComponent2.getLayer();
		}
	}

	LayerComparator layerComparator = new LayerComparator();

	private SpriteBatch spriteBatch;

	private final Libgdx2dCamera cameraTransformImpl;

	@SuppressWarnings("unchecked")
	public SpriteRendererSystem() {
		super(SpriteComponent.class);
		this.cameraTransformImpl = new CameraTransformImpl();
	}

	@SuppressWarnings("unchecked")
	public SpriteRendererSystem(Libgdx2dCamera cameraTransformImpl) {
		super(SpriteComponent.class);
		this.cameraTransformImpl = cameraTransformImpl;
	}

	Array<Entity> orderedByLayerEntities = new Array<Entity>();

	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {

		orderedByLayerEntities.clear();

		for (int i = 0; i < entities.size(); i++) {
			Entity entity = entities.get(i);
			orderedByLayerEntities.add(entity);
		}

		orderedByLayerEntities.sort(layerComparator);

		cameraTransformImpl.apply(spriteBatch);

		// if (camera != null)
		// spriteBatch.setProjectionMatrix(camera.combined);
		//
		// Matrix4 transform = new Matrix4();
		// transform.scl(new Vector3(2f, 2f, 1f));
		// transform.trn(1f, 1f, 0f);
		// spriteBatch.setTransformMatrix(transform);

		spriteBatch.begin();

		for (int i = 0; i < orderedByLayerEntities.size; i++) {
			Entity entity = orderedByLayerEntities.get(i);
			SpriteComponent spriteComponent = entity.getComponent(SpriteComponent.class);
			Sprite sprite = spriteComponent.getSprite();
			sprite.draw(spriteBatch);
		}

		spriteBatch.end();

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