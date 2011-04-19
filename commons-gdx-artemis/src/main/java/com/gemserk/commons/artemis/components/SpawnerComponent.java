package com.gemserk.commons.artemis.components;

import com.artemis.Component;
import com.gemserk.commons.artemis.entities.EntityTemplate;
import com.gemserk.commons.values.IntValue;
import com.gemserk.commons.values.ValueBuilder;
import com.gemserk.componentsengine.properties.Property;
import com.gemserk.componentsengine.properties.PropertyBuilder;
import com.gemserk.componentsengine.timers.Timer;

public class SpawnerComponent extends Component {
	
	private final Property<Timer> spawnTimer;
	
	private final Property<EntityTemplate> entityTemplate;

	private final Property<IntValue> minTime;

	private final Property<IntValue> maxTime;
	
	public int getMinTime() {
		return minTime.get().value;
	}

	public int getMaxTime() {
		return maxTime.get().value;
	}

	public Timer getSpawnTimer() {
		return spawnTimer.get();
	}
	
	public void setSpawnTimer(Timer timer) {
		spawnTimer.set(timer);
	}
	
	public EntityTemplate getEntityTemplate() {
		return entityTemplate.get();
	}

	public SpawnerComponent(Property<Timer> spawnTimer, Property<IntValue> minTime, Property<IntValue> maxTime, Property<EntityTemplate> entityTemplate) {
		this.spawnTimer = spawnTimer;
		this.minTime = minTime;
		this.maxTime = maxTime;
		this.entityTemplate = entityTemplate;
	}

	
	public SpawnerComponent(Timer spawnTimer, int minTime, int maxTime, EntityTemplate entityTemplate) {
		this(PropertyBuilder.property(spawnTimer), PropertyBuilder.property(ValueBuilder.intValue(minTime)), 
				PropertyBuilder.property(ValueBuilder.intValue(maxTime)), PropertyBuilder.property(entityTemplate));
	}
}
