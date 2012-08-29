package com.gemserk.commons.gdx.resources.dataloaders;

import java.util.Properties;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.gemserk.commons.gdx.audio.SuperSound;
import com.gemserk.resources.ResourceManager;

public class SoundDataLoader extends DisposableDataLoader<Sound> {

	private String durationMetadataId;
	private Long duration;
	private final ResourceManager<String> resourceManager;

	public SoundDataLoader(FileHandle fileHandle, ResourceManager<String> resourceManager) {
		super(fileHandle);
		this.resourceManager = resourceManager;
	}

	public SoundDataLoader durationMetadata(String durationMetadataId) {
		this.durationMetadataId = durationMetadataId;
		return this;
	}

	public SoundDataLoader duration(long duration) {
		this.duration = duration;
		return this;
	}

	@Override
	public Sound load() {
		Sound newSound = Gdx.audio.newSound(fileHandle);
		if (durationMetadataId == null && duration == null) {
			return newSound;
		} else if (duration != null) {
			return new SuperSound(newSound, duration, fileHandle.path());
		} else {
			Properties durationMetadata = resourceManager.getResourceValue(durationMetadataId);
			if (durationMetadata == null)
				throw new RuntimeException("Error while loading metadata (" + durationMetadataId + ") for sound " + fileHandle);

			String durationString = durationMetadata.getProperty(fileHandle.path());
			if (durationString == null)
				throw new RuntimeException("Error while loading the duration from the metadata (" + durationMetadataId + ") for sound " + fileHandle);

			try {
				long duration = Long.parseLong(durationString);
				return new SuperSound(newSound, duration, fileHandle.path());

			} catch (NumberFormatException e) {
				throw new RuntimeException("Error while parsing the duration from the metadata (" + durationMetadataId + ") for sound " + fileHandle + " it was " + durationString);

			}
		}
	}

}