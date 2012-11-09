package com.gemserk.commons.artemis.systems;

import com.artemis.Entity;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.gemserk.commons.artemis.components.MeshSpriteComponent;
import com.gemserk.commons.artemis.components.RenderableComponent;
import com.gemserk.commons.artemis.components.SpriteComponent;
import com.gemserk.commons.gdx.camera.Libgdx2dCamera;
import com.gemserk.commons.gdx.g2d.MeshSpriteBatch;
import com.gemserk.commons.gdx.g2d.PolygonDefinition;
import com.gemserk.commons.gdx.g2d.SpriteMesh;
import com.gemserk.componentsengine.utils.RandomAccessMap;

public class RenderlLayerMeshSpriteBatchImpl implements RenderLayer {

	static class EntityComponents {
		public RenderableComponent renderableComponent;
		public SpriteComponent spriteComponent;
		public MeshSpriteComponent meshSpriteComponent;
	}

	static class Factory extends EntityComponentsFactory<EntityComponents> {

		@Override
		public EntityComponents newInstance() {
			return new EntityComponents();
		}

		@Override
		public void free(EntityComponents entityComponent) {
			entityComponent.renderableComponent = null;
			entityComponent.spriteComponent = null;
			entityComponent.meshSpriteComponent = null;
		}

		@Override
		public void load(Entity e, EntityComponents entityComponent) {
			entityComponent.renderableComponent = RenderableComponent.get(e);
			entityComponent.spriteComponent = SpriteComponent.get(e);
			entityComponent.meshSpriteComponent = MeshSpriteComponent.get(e);
		}
	}

	private final MeshSpriteBatch meshSpriteBatch;

	private final OrderedByLayerRenderables orderedByLayerRenderables;
	private final Libgdx2dCamera camera;
	private boolean enabled;

	private Factory factory;

	private boolean blending;

	public RenderlLayerMeshSpriteBatchImpl(int minLayer, int maxLayer, Libgdx2dCamera camera, MeshSpriteBatch meshSpriteBatch, boolean blending) {
		this.camera = camera;
		this.meshSpriteBatch = meshSpriteBatch;
		this.orderedByLayerRenderables = new OrderedByLayerRenderables(minLayer, maxLayer);
		this.enabled = true;
		this.factory = new Factory();
		this.blending = blending;
	}

	@Override
	public void init() {

	}

	@Override
	public void dispose() {

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

	private final float[] spriteVertices = new float[24];

	private float[] fromSprite(float[] vertices, float z) {
		int si = 0;
		for (int i = 0; i < spriteVertices.length; i += MeshSpriteBatch.VERTEX_SIZE) {
			spriteVertices[i + 0] = vertices[si + 0];
			spriteVertices[i + 1] = vertices[si + 1];
			spriteVertices[i + 2] = z;
			spriteVertices[i + 3] = vertices[si + 2];
			spriteVertices[i + 4] = vertices[si + 3];
			spriteVertices[i + 5] = vertices[si + 4];
			si += 5;
		}
		return spriteVertices;
	}

	@Override
	public void render() {
		// meshSpriteBatch.setProjectionMatrix(camera.getCombinedMatrix());

		RandomAccessMap<Entity, EntityComponents> entityComponents = factory.entityComponents;

		// if (blending)
		// meshSpriteBatch.enableBlending();
		// else
		// meshSpriteBatch.disableBlending();
		//
		// meshSpriteBatch.setDepthTestEnabled(false);
		//
		// meshSpriteBatch.begin();

		for (int i = 0; i < orderedByLayerRenderables.size(); i++) {
			Renderable renderable = orderedByLayerRenderables.get(i);

			if (!renderable.isVisible())
				continue;

			EntityComponents components = entityComponents.get(renderable.getEntity());

			SpriteComponent spriteComponent = components.spriteComponent;
			MeshSpriteComponent meshSpriteComponent = components.meshSpriteComponent;

			if (spriteComponent != null) {
				Sprite sprite = spriteComponent.getSprite();
				sprite.setColor(spriteComponent.getColor());
				meshSpriteBatch.draw(sprite.getTexture(), fromSprite(sprite.getVertices(), 0f), PolygonDefinition.spriteIndices);
			}

			if (meshSpriteComponent != null) {
				SpriteMesh spriteMesh = meshSpriteComponent.spriteMesh;
				spriteMesh.setColor(meshSpriteComponent.color);
				meshSpriteBatch.draw(spriteMesh.getTexture(), spriteMesh.getVertices(), spriteMesh.getIndices());
			}
		}

		// meshSpriteBatch.end();
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
