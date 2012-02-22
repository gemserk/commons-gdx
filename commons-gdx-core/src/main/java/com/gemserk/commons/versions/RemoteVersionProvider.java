package com.gemserk.commons.versions;

public interface RemoteVersionProvider {

	ApplicationVersion getLatestVersion(String currentVersionNumber);
	
	ApplicationVersion getLatestVersion();

}