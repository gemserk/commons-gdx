package com.gemserk.commons.artemis.entities;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.gemserk.commons.artemis.components.SpatialComponent;
import com.gemserk.commons.artemis.components.TextComponent;
import com.gemserk.commons.values.FloatValue;
import com.gemserk.componentsengine.properties.AbstractProperty;
import com.gemserk.componentsengine.properties.Property;
import com.gemserk.componentsengine.properties.SimpleProperty;

public class EntityFactory {

	private final World world;
	
	public EntityFactory(World world) {
		this.world = world;
	}

	public Entity fpsEntity(Property<Vector2> scale, Property<BitmapFont> font, SimpleProperty<Vector2> position) {
		Entity entity = world.createEntity();
		
		entity.addComponent(new TextComponent( //
				new AbstractProperty<String>() {
					@Override
					public String get() {
						return "FPS: " + Gdx.graphics.getFramesPerSecond();
					}
				}, //
				font, //
				new SimpleProperty<Color>(new Color(1f, 1f, 1f, 1f)) //
		));
		
		entity.addComponent(new SpatialComponent(position, scale, new SimpleProperty<FloatValue>(new FloatValue(0f))));
		entity.refresh();
		
		return entity;
	}

}
