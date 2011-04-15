package com.gemserk.commons.artemis.components;

import com.artemis.Component;
import com.gemserk.commons.values.IntValue;
import com.gemserk.commons.values.ValueBuilder;
import com.gemserk.componentsengine.properties.Property;
import com.gemserk.componentsengine.properties.PropertyBuilder;

public class AliveComponent extends Component {
	
	private Property<IntValue> aliveTime;
	
	public int getAliveTime() {
		return aliveTime.get().value;
	}
	
	public void setAliveTime(int aliveTime) {
		this.aliveTime.get().value = aliveTime;
	}

	public AliveComponent(int time) {
		aliveTime = PropertyBuilder.property(ValueBuilder.intValue(time));
	}

}
