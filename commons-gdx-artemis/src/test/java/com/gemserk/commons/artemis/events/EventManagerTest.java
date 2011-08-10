package com.gemserk.commons.artemis.events;

import static org.junit.Assert.assertThat;


import org.hamcrest.core.IsNull;
import org.junit.Test;

import com.gemserk.commons.artemis.events.Event;
import com.gemserk.commons.artemis.events.EventManagerImpl;

public class EventManagerTest {

	@Test
	public void shouldNotReturnEventIfNoEvent() {
		EventManagerImpl eventManager = new EventManagerImpl();
		Event event = eventManager.getEvent("cameraReachedTarget");
		assertThat(event, IsNull.nullValue());
	}

	@Test
	public void shouldReturnEventIfEventRegisteredWithName() {
		Object o = new Float(5f);
		EventManagerImpl eventManager = new EventManagerImpl();
		eventManager.registerEvent("cameraReachedTarget", o);
		Event event = eventManager.getEvent("cameraReachedTarget");
		assertThat(event, IsNull.notNullValue());
	}

	@Test
	public void shouldReturnNullIfEventRegisteredWithOtherName() {
		Object o = new Float(5f);
		EventManagerImpl eventManager = new EventManagerImpl();
		eventManager.registerEvent("shipDied", o);
		Event event = eventManager.getEvent("cameraReachedTarget");
		assertThat(event, IsNull.nullValue());
	}
	
	@Test
	public void shouldRemoveWhenHandled() {
		Object o = new Float(5f);
		EventManagerImpl eventManager = new EventManagerImpl();
		eventManager.registerEvent("cameraReachedTarget", o);
		Event event = eventManager.getEvent("cameraReachedTarget");
		eventManager.handled(event);
		event = eventManager.getEvent("cameraReachedTarget");
		assertThat(event, IsNull.nullValue());
	}

}
