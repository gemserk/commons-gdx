package com.gemserk.commons.artemis.entities;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.gemserk.commons.artemis.components.Spatial;
import com.gemserk.commons.artemis.components.SpatialComponent;
import com.gemserk.commons.artemis.components.TextComponent;
import com.gemserk.componentsengine.properties.AbstractProperty;
import com.gemserk.componentsengine.properties.SimpleProperty;

public class EntityFactory {

	private World world;
	
	public void setWorld(World world) {
		this.world = world;
	}
	
	public EntityFactory() {
		
	}
	
	public EntityFactory(World world) {
		this.world = world;
	}

	public Entity fpsEntity(Vector2 scale, BitmapFont font, Spatial spatial) {
		Entity entity = world.createEntity();
		
		entity.addComponent(new TextComponent( //
				new AbstractProperty<String>() {
					@Override
					public String get() {
						return "FPS: " + Gdx.graphics.getFramesPerSecond();
					}
				}, //
				new SimpleProperty<BitmapFont>(font), //
				new SimpleProperty<Color>(new Color(1f, 1f, 1f, 1f)) //
		));
		
		// position.x, position.y, scale, new SimpleProperty<FloatValue>(new FloatValue(0f)))
		entity.addComponent(new SpatialComponent(spatial));
		entity.refresh();
		
		return entity;
	}

}
