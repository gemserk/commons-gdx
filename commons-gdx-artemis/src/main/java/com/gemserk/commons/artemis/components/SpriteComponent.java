package com.gemserk.commons.artemis.components;

import com.artemis.Component;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.gemserk.commons.values.IntValue;
import com.gemserk.commons.values.ValueBuilder;
import com.gemserk.componentsengine.properties.Property;
import com.gemserk.componentsengine.properties.PropertyBuilder;

public class SpriteComponent extends Component {
	
	private Property<Sprite> sprite;
	
	private Property<IntValue> layer;
	
	private Property<Vector2> center; // x and y values between 0,1

	private Property<Color> color;
	
	public Sprite getSprite() {
		return sprite.get();
	}
	
	public int getLayer() {
		return layer.get().value;
	}
	
	public Vector2 getCenter() {
		return center.get();
	}
	
	public Color getColor() {
		return color.get();
	}

	public SpriteComponent(Property<Sprite> sprite, Property<IntValue> layer) {
		this(sprite, layer, PropertyBuilder.property(new Vector2(0.5f, 0.5f)));
	}
	
	public SpriteComponent(Property<Sprite> sprite, Property<IntValue> layer, Property<Vector2> center) {
		this(sprite, layer, center, PropertyBuilder.property(new Color(1f,1f,1f,1f)));
	}
	
	public SpriteComponent(Property<Sprite> sprite, Property<IntValue> layer, Property<Vector2> center, Property<Color> color) {
		this.sprite = sprite;
		this.layer = layer;
		this.center = center;
		this.color = color;
	}
	
	public SpriteComponent(Sprite sprite, int layer, Vector2 center, Color color) {
		this(PropertyBuilder.property(sprite), 
				PropertyBuilder.property(ValueBuilder.intValue(layer)), 
				PropertyBuilder.vector2(center), 
				PropertyBuilder.property(color));
	}

}
