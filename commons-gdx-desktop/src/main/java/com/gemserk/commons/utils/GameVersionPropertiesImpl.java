package com.gemserk.commons.utils;

import java.io.IOException;
import java.util.Properties;

import com.badlogic.gdx.Gdx;
import com.gemserk.commons.utils.GameVersion;

/**
 * Provides the game version retrieving it from a Properties file.
 */
public class GameVersionPropertiesImpl implements GameVersion {

	private String version;
	private String versionFile = "version.properties";

	@Override
	public String getVersion() {
		if (version == null)
			version = internalGetVersion();
		return version;
	}

	private String internalGetVersion() {
		try {
			Properties properties = new Properties();
			properties.load(Gdx.files.classpath(versionFile).read());
			return properties.getProperty("version");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
