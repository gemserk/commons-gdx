package com.gemserk.commons.monitors;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class IntMonitorTest {

	@Test
	public void shouldDetectNoChangeWhenSameValue() {
		IntMonitor intMonitor = new IntMonitor(5);
		intMonitor.update(5);
		assertFalse(intMonitor.isChanged());
	}
	
	@Test
	public void shouldDetectChangeWhenDifferentValue() {
		IntMonitor intMonitor = new IntMonitor(2);
		intMonitor.update(5);
		assertTrue(intMonitor.isChanged());
	}
	
	@Test
	public void shouldNotDetectChangeSecondTimeIfSameValue() {
		IntMonitor intMonitor = new IntMonitor(2);
		intMonitor.update(5);
		intMonitor.update(5);
		assertFalse(intMonitor.isChanged());
	}

}
