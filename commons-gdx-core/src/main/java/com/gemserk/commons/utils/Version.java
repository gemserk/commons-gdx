package com.gemserk.commons.utils;

/**
 * Helper class to provide an easy way to check java versions.
 * 
 * @author rgarat
 * @author acoppes
 */
public class Version {

	int[] numbers;

	public Version(String version) {
		String[] versionData = version.split("[_\\.]");
		numbers = new int[versionData.length];
		for (int i = 0; i < versionData.length; i++)
			numbers[i] = Integer.parseInt(versionData[i]);
	}

	public boolean greaterOrEqualThan(Version version) {
		for (int i = 0; i < numbers.length; i++) {
			if (numbers[i] > version.numbers[i])
				return true;
			if (numbers[i] < version.numbers[i])
				return false;
		}
		return true;
	}

	public String toString() {
		String version = "";
		for (int i = 0; i < numbers.length; i++)
			version += "" + numbers[i];
		return version;
	}

}