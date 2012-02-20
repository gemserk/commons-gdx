package com.gemserk.commons.versions;

import com.gemserk.commons.versions.ApplicationVersion;

public class ApplicationVersionImpl implements ApplicationVersion {

	String versionNumber;
	String[] changeLog;

	@Override
	public String getVersionNumber() {
		return versionNumber;
	}

	@Override
	public String[] getChangeLog() {
		return changeLog;
	}

	public ApplicationVersionImpl(String versionNumber) {
		this(versionNumber, new String[] {});
	}

	public ApplicationVersionImpl(String versionNumber, String[] changeLog) {
		this.versionNumber = versionNumber;
		this.changeLog = changeLog;
	}

}