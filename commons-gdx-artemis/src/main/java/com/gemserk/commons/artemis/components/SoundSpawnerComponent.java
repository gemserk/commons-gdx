package com.gemserk.commons.artemis.components;

import com.artemis.Component;
import com.artemis.ComponentTypeManager;
import com.artemis.Entity;
import com.badlogic.gdx.audio.Sound;
import com.gemserk.commons.artemis.events.EventListener;

public class SoundSpawnerComponent extends Component {
	
	public static final int type = ComponentTypeManager.getTypeFor(SoundSpawnerComponent.class).getId();

	public static SoundSpawnerComponent get(Entity e) {
		return (SoundSpawnerComponent) e.getComponent(type);
	}

	public String eventId;
	public Sound sound;
	public EventListener listener;

	public SoundSpawnerComponent(String eventId, Sound sound) {
		this.eventId = eventId;
		this.sound = sound;
	}

}