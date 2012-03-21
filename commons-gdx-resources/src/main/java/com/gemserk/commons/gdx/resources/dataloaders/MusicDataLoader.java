package com.gemserk.commons.gdx.resources.dataloaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;

public class MusicDataLoader extends DisposableDataLoader<Music> {

	public MusicDataLoader(FileHandle fileHandle) {
		super(fileHandle);
	}

	public Music load() {
		return Gdx.audio.newMusic(fileHandle);
	}

	public void unload(Music t) {
		if (t.isPlaying())
			t.stop();
		super.unload(t);
	}
}