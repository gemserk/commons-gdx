package com.gemserk.commons.artemis.components;

import com.artemis.Component;
import com.badlogic.gdx.audio.Sound;
import com.gemserk.commons.artemis.events.EventListener;

public class SoundSpawnerComponent extends Component {

	public String eventId;
	public Sound sound;
	public EventListener listener;

	public SoundSpawnerComponent(String eventId, Sound sound) {
		this.eventId = eventId;
		this.sound = sound;
	}

}