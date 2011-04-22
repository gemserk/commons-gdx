package com.gemserk.commons.artemis.components;

import com.artemis.Component;
import com.badlogic.gdx.math.Rectangle;
import com.gemserk.componentsengine.properties.Property;
import com.gemserk.componentsengine.properties.PropertyBuilder;

public class AliveAreaComponent extends Component {
	
	private Property<Rectangle> aliveArea;
	
	public Rectangle getAliveArea() {
		return aliveArea.get();
	}
	
	public AliveAreaComponent(Rectangle aliveArea) {
		this.aliveArea = PropertyBuilder.property(aliveArea);
	}

}
