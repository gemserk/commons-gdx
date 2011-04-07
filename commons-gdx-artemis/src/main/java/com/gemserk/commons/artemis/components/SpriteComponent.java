package com.gemserk.commons.artemis.components;

import com.artemis.Component;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.gemserk.commons.values.IntValue;
import com.gemserk.componentsengine.properties.Property;

public class SpriteComponent extends Component {
	
	private final Property<Sprite> sprite;
	
	private final Property<IntValue> layer;
	
	public Sprite getSprite() {
		return sprite.get();
	}
	
	public int getLayer() {
		return layer.get().value;
	}

	public SpriteComponent(Property<Sprite> sprite, Property<IntValue> layer) {
		this.sprite = sprite;
		this.layer = layer;
	}

}
