package com.gemserk.commons.gdx.audio;

import com.badlogic.gdx.audio.Sound;

public class SoundPlayer {
	
	private float volume = 1f;
	
	public void setVolume(float volume) {
		this.volume = volume;
	}
	
	public float getVolume() {
		return volume;
	}
	
	public boolean isMuted() {
		return getVolume() <= 0f;
	}
	
	public void mute() {
		setVolume(0f);
	}
	
	public void unmute() {
		setVolume(1f);
	}
	
	public long play(Sound sound) {
		return this.play(sound, 1f);
	}
	
	public long play(Sound sound, float volume) {
		if (this.volume > 0f)
			return sound.play(this.volume * volume);
		return 0;
	}

}
