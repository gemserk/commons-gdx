package com.gemserk.commons.gdx.resources;

import java.io.IOException;
import java.util.Properties;

import com.badlogic.gdx.files.FileHandle;

public class PropertiesResourceBuilder implements ResourceBuilder<Properties>{
	
	private final FileHandle fileHandle;

	@Override
	public boolean isVolatile() {
		return false;
	}
	
	public PropertiesResourceBuilder(FileHandle fileHandle) {
		this.fileHandle = fileHandle;
	}

	@Override
	public Properties build() {
		try {
			Properties properties = new Properties();
			properties.load(fileHandle.read());
			return properties;
		} catch (IOException e) {
			throw new RuntimeException("Error while loading a properties file", e);
		}
	}

}
