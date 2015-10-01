package com.gemserk.commons.gdx.audio;

import com.badlogic.gdx.audio.Sound;

public class SuperSound implements Sound {

	Sound sound;
	long duration;
	final String soundName;
	
	public SuperSound(Sound originalSound, long duration, String soundName) {
		sound = originalSound;
		this.duration = duration;
		this.soundName = soundName;
	}

	public long play() {
		return sound.play();
	}

	public long play(float volume) {
		return sound.play(volume);
	}

	public long play(float volume, float pitch, float pan) {
		return sound.play(volume, pitch, pan);
	}

	public long loop() {
		return sound.loop();
	}

	public long loop(float volume) {
		return sound.loop(volume);
	}

	public long loop(float volume, float pitch, float pan) {
		return sound.loop(volume, pitch, pan);
	}

	public void stop() {
		sound.stop();
	}

	@Override
	public void pause() {
		sound.pause();
	}

	@Override
	public void resume() {
		sound.resume();
	}

	public void dispose() {
		sound.dispose();
	}

	public void stop(long soundId) {
		sound.stop(soundId);
	}

	@Override
	public void pause(long soundId) {
		sound.pause(soundId);
	}

	@Override
	public void resume(long soundId) {
		sound.resume(soundId);
	}

	public void setLooping(long soundId, boolean looping) {
		sound.setLooping(soundId, looping);
	}

	public void setPitch(long soundId, float pitch) {
		sound.setPitch(soundId, pitch);
	}

	public void setVolume(long soundId, float volume) {
		sound.setVolume(soundId, volume);
	}

	public void setPan(long soundId, float pan, float volume) {
		sound.setPan(soundId, pan, volume);
	}
	
	public void setDuration(long duration) {
		this.duration = duration;
	}
	
	public long getDuration() {
		return duration;
	}
	
	public String getSoundName() {
		return soundName;
	}

	@Override
	public void setPriority(long soundId, int priority) {
		sound.setPriority(soundId, priority);
	}
}
