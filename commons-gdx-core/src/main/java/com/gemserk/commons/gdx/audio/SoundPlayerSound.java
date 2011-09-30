package com.gemserk.commons.gdx.audio;

import com.badlogic.gdx.audio.Sound;

public class SoundPlayerSound implements Sound {
	
	private final SoundPlayer soundPlayer;
	private final Sound sound;

	public SoundPlayerSound(SoundPlayer soundPlayer, Sound sound) {
		this.soundPlayer = soundPlayer;
		this.sound = sound;
	}

	@Override
	public void play() {
		soundPlayer.play(sound);
	}

	@Override
	public void play(float volume) {
		soundPlayer.play(sound, volume);
	}

	@Override
	public void stop() {
		sound.stop();
	}

	@Override
	public void dispose() {
		sound.dispose();
	}

}
