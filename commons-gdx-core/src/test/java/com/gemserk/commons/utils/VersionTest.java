package com.gemserk.commons.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class VersionTest {

	@Test
	public void testGreaterOrEqualThan() {
		Version requiredVersion = new Version("1.6.0.10");

		Version version15 = new Version("1.5.0.0");
		Version lesserVersion = new Version("1.6.0.0");
		Version equalVersion = new Version("1.6.0.10");
		Version version1620 = new Version("1.6.0.20");
		Version greaterVersion = new Version("1.7.0.0");
		
		assertFalse(version15.greaterOrEqualThan(requiredVersion));
		assertFalse(lesserVersion.greaterOrEqualThan(requiredVersion));
		assertFalse(new Version("1.5.0.20").greaterOrEqualThan(requiredVersion));
		
		assertTrue(equalVersion.greaterOrEqualThan(requiredVersion));
		assertTrue(version1620.greaterOrEqualThan(requiredVersion));
		assertTrue(greaterVersion.greaterOrEqualThan(requiredVersion));
	}

}
