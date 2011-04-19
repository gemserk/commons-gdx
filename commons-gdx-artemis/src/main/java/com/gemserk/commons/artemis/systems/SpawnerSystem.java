package com.gemserk.commons.artemis.systems;

import java.util.Random;

import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.utils.ImmutableBag;
import com.gemserk.commons.artemis.components.SpawnerComponent;
import com.gemserk.commons.artemis.entities.EntityTemplate;
import com.gemserk.componentsengine.timers.CountDownTimer;
import com.gemserk.componentsengine.timers.Timer;

public class SpawnerSystem extends EntitySystem {
	
	private static Random random = new Random();

	@SuppressWarnings("unchecked")
	public SpawnerSystem() {
		super(SpawnerComponent.class);
	}

	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		for (int i = 0; i < entities.size(); i++) {

			Entity entity = entities.get(i);

			SpawnerComponent spawnerComponent = entity.getComponent(SpawnerComponent.class);

			Timer spawnTimer = spawnerComponent.getSpawnTimer();
			
			EntityTemplate entityTemplate = spawnerComponent.getEntityTemplate();
			
			if (spawnTimer.update(world.getDelta()))  
				entityTemplate.build();
			
			if (!spawnTimer.isRunning()) {
				int spawnTime = random.nextInt(spawnerComponent.getMaxTime() - spawnerComponent.getMinTime()) + spawnerComponent.getMinTime();
				spawnerComponent.setSpawnTimer(new CountDownTimer(spawnTime, true));
			}
			
		}
	}

	@Override
	public void initialize() {

	}

	@Override
	protected boolean checkProcessing() {
		return true;
	}
}