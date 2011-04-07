package com.gemserk.commons.artemis.systems;

import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.gemserk.commons.artemis.components.SpatialComponent;
import com.gemserk.commons.artemis.components.TextComponent;

public class TextRendererSystem extends EntitySystem {

	private SpriteBatch spriteBatch;

	@SuppressWarnings("unchecked")
	public TextRendererSystem() {
		super(TextComponent.class, SpatialComponent.class);
	}

	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		spriteBatch.begin();
		for (int i = 0; i < entities.size(); i++) {

			Entity entity = entities.get(i);
			
			TextComponent textComponent = entity.getComponent(TextComponent.class);
			SpatialComponent spatialComponent = entity.getComponent(SpatialComponent.class);

			BitmapFont font = textComponent.getFont();
			String text = textComponent.getText();
			
			Vector2 position = spatialComponent.getPosition();
			Vector2 size = spatialComponent.getSize();

			// TextBounds bounds = font.getBounds(text);

			font.setScale(size.x, size.y);
			font.setColor(textComponent.getColor());
			font.draw(spriteBatch, text, position.x, position.y);

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