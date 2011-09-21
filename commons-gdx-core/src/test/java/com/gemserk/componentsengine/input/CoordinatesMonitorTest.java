package com.gemserk.componentsengine.input;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.hamcrest.core.IsEqual;
import org.junit.Before;
import org.junit.Test;

import com.gemserk.componentsengine.input.CoordinatesMonitor;

public class CoordinatesMonitorTest {
	
	private CoordinatesMonitorMock monitor;

	@Before
	public void setUp(){
		monitor = new CoordinatesMonitorMock();
	}
	
	@Test 
	public void shouldNotChangeIfCoordinatesAreTheSame(){
		float testX = 123;
		float testY = 987;
		monitor.x = testX;
		monitor.y = testY;
		
		monitor.mockX = testX;
		monitor.mockY = testY;
		
		monitor.update();
		assertFalse(monitor.hasChanged());
		assertThat(monitor.getX(), IsEqual.equalTo(testX));
		assertThat(monitor.getY(), IsEqual.equalTo(testY));
	}
	
	
	@Test 
	public void shouldChangeIfCoordinatesAreDifferent(){
		float testX = 123;
		float testY = 987;
		monitor.x = testX*2;
		monitor.y = testY;
		
		monitor.mockX = testX;
		monitor.mockY = testY;
		
		monitor.update();
		assertTrue(monitor.hasChanged());
		assertThat(monitor.getX(), IsEqual.equalTo(testX));
		assertThat(monitor.getY(), IsEqual.equalTo(testY));
	}
	
	@Test 
	public void shouldChangeIfCoordinatesAreDifferent2(){
		float testX = 123;
		float testY = 987;
		monitor.x = testX;
		monitor.y = testY*2;
		
		monitor.mockX = testX;
		monitor.mockY = testY;
		
		monitor.update();
		assertTrue(monitor.hasChanged());
		assertThat(monitor.getX(), IsEqual.equalTo(testX));
		assertThat(monitor.getY(), IsEqual.equalTo(testY));
	}
	
	
	class CoordinatesMonitorMock extends CoordinatesMonitor{
		
		float mockX = 0;
		float mockY = 0;
		
		@Override
		protected float readX() {
			return mockX;
		}

		@Override
		protected float readY() {
			return mockY;
		}
		
	}
	
	
}