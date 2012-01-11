package com.gemserk.commons.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

import com.gemserk.commons.utils.GameVersion;

/**
 * Provides the version of the game based on the Android PackageInfo.
 */
public class GameVersionAndroidImpl implements GameVersion {

	private final Context context;
	private String version;

	public GameVersionAndroidImpl(Context context) {
		this.context = context;
	}

	@Override
	public String getVersion() {
		if (version == null)
			version = internalGetVersion();
		return version;
	}

	private String internalGetVersion() {
		PackageManager packageManager = context.getPackageManager();
		try {
			PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionName;
		} catch (NameNotFoundException e) {
			throw new RuntimeException("Couldn't find package version", e);
		}
	}

}
