package com.gemserk.commons.artemis.components;

import com.artemis.Component;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.gemserk.commons.values.IntValue;
import com.gemserk.componentsengine.properties.Property;
import com.gemserk.componentsengine.properties.SimpleProperty;

public class SpriteComponent extends Component {
	
	private Property<Sprite> sprite;
	
	private Property<IntValue> layer;
	
	private Property<Vector2> center; // x and y values between 0,1
	
	public Sprite getSprite() {
		return sprite.get();
	}
	
	public int getLayer() {
		return layer.get().value;
	}
	
	public Vector2 getCenter() {
		return center.get();
	}

	public SpriteComponent(Property<Sprite> sprite, Property<IntValue> layer) {
		this(sprite, layer, new SimpleProperty<Vector2>(new Vector2(0.5f, 0.5f)));
	}

	public SpriteComponent(Property<Sprite> sprite, Property<IntValue> layer, Property<Vector2> center) {
		this.sprite = sprite;
		this.layer = layer;
		this.center = center;
	}

}
