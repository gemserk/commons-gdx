package com.gemserk.componentsengine.input;

import static org.junit.Assert.*;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

public class AnalogInputMonitorTest {
	
	class AnalogInputMonitorMock extends AnalogInputMonitor {
		
		float newValue;
		
		public void setNewValue(float newValue) {
			this.newValue = newValue;
		}
		
		@Override
		protected float newValue() {
			return newValue;
		}

	}

	@Test
	public void shouldNotBeChangedWhenNewValueSameLastValue() {
		
		AnalogInputMonitorMock monitor = new AnalogInputMonitorMock();
		monitor.setNewValue(monitor.value);
		
		assertFalse(monitor.hasChanged());
		monitor.update();
		assertFalse(monitor.hasChanged());
		assertThat(monitor.getValue(), IsEqual.equalTo(0f));
		
	}
	
	@Test
	public void shouldChangeWhenNewValueDifferentFromLastValue() {
		
		AnalogInputMonitorMock monitor = new AnalogInputMonitorMock();
		monitor.setNewValue(10f);
		
		assertFalse(monitor.hasChanged());
		monitor.update();
		assertTrue(monitor.hasChanged());
		assertThat(monitor.getValue(), IsEqual.equalTo(10f));
		
	}
	
	@Test
	public void oldValueShouldBeValueBeforeNewValueSet() {
		
		AnalogInputMonitorMock monitor = new AnalogInputMonitorMock();
		monitor.setNewValue(10f);
		
		monitor.update();
		assertThat(monitor.getValue(), IsEqual.equalTo(10f));
		assertThat(monitor.getOldValue(), IsEqual.equalTo(0f));
		
		monitor.setNewValue(20f);
		monitor.update();

		assertThat(monitor.getValue(), IsEqual.equalTo(20f));
		assertThat(monitor.getOldValue(), IsEqual.equalTo(10f));
		
	}

}
