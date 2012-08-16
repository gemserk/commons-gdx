package com.gemserk.commons.artemis.systems;

import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;
import com.gemserk.commons.artemis.components.Components;
import com.gemserk.commons.artemis.components.SoundSpawnerComponent;
import com.gemserk.commons.artemis.events.Event;
import com.gemserk.commons.artemis.events.EventListener;
import com.gemserk.commons.artemis.events.EventManager;
import com.gemserk.commons.gdx.audio.SoundPlayer;

/**
 * Spawns sounds based on the entities with SpawnerSoundComponent and using the EventManager to detect the events to spawn the sounds.
 */
public class SoundSpawnerSystem extends EntityProcessingSystem {

	private class SoundSpawnerEventListener extends EventListener {

		Entity e;

		public SoundSpawnerEventListener(Entity e) {
			this.e = e;
		}

		@Override
		public void onEvent(Event event) {
			SoundSpawnerComponent soundSpawnerComponent = Components.getSoundSpawnerComponent(e);
			soundPlayer.play(soundSpawnerComponent.sound);
		}

	}

	SoundPlayer soundPlayer;
	EventManager eventManager;
	
	public void setSoundPlayer(SoundPlayer soundPlayer) {
		this.soundPlayer = soundPlayer;
	}
	
	public void setEventManager(EventManager eventManager) {
		this.eventManager = eventManager;
	}

	@SuppressWarnings("unchecked")
	public SoundSpawnerSystem() {
		super(Components.soundSpawnerComponentClass);
	}

	@Override
	protected void added(Entity e) {
		super.added(e);
		SoundSpawnerComponent soundSpawnerComponent = Components.getSoundSpawnerComponent(e);
		soundSpawnerComponent.listener = new SoundSpawnerEventListener(e);
		eventManager.register(soundSpawnerComponent.eventId, soundSpawnerComponent.listener);
	}

	@Override
	protected void removed(Entity e) {
		super.removed(e);
		SoundSpawnerComponent soundSpawnerComponent = Components.getSoundSpawnerComponent(e);
		eventManager.unregister(soundSpawnerComponent.listener);
	}

	@Override
	protected void process(Entity e) {
	}

}
