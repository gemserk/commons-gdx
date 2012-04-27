package com.gemserk.commons.gdx.resources;

import com.badlogic.gdx.files.FileHandle;
import com.gemserk.animation4j.timeline.TimelineConfigurator;
import com.gemserk.commons.gdx.resources.dataloaders.TimelineConfiguratorDataLoader;

public class TimelineConfiguratorResourceBuilder implements ResourceBuilder<TimelineConfigurator> {

	FileHandle fileHandle;
	TimelineConfiguratorDataLoader configuratorDataLoader;

	@Override
	public boolean isVolatile() {
		return true;
	}

	public TimelineConfiguratorResourceBuilder fileHandle(FileHandle fileHandle) {
		this.fileHandle = fileHandle;
		return this;
	}

	@Override
	public TimelineConfigurator build() {
		if (configuratorDataLoader == null)
			configuratorDataLoader = new TimelineConfiguratorDataLoader(fileHandle);
		return configuratorDataLoader.load();
	}

}