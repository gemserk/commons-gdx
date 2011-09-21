package com.gemserk.commons.gdx.resources.dataloaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;

public class SoundDataLoader extends DisposableDataLoader<Sound> {

	public SoundDataLoader(FileHandle fileHandle) {
		super(fileHandle);
	}

	@Override
	public Sound load() {
		return Gdx.audio.newSound(fileHandle);
	}

}