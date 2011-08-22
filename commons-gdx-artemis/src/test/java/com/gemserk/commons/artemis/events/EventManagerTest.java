package com.gemserk.commons.artemis.events;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNull;
import org.junit.Test;

public class EventManagerTest {

	@Test
	public void shouldReturnAllRegisteredEventsCount() {
		Object o = new Float(5f);
		InternalEventManager eventManager = new InternalEventManager();

		eventManager.registerEvent("cameraReachedTarget", o);
		eventManager.registerEvent("cameraReachedTarget", o);
		eventManager.registerEvent("cameraReachedTarget", o);

		assertThat(eventManager.getEventCount(), IsEqual.equalTo(3));
	}

	@Test
	public void shouldReturnNullForEventOutsideCount() {
		Object o = new Float(5f);
		InternalEventManager eventManager = new InternalEventManager();
		eventManager.registerEvent("cameraReachedTarget", o);
		assertThat(eventManager.getEvent(-1), IsNull.nullValue());
		assertThat(eventManager.getEvent(1), IsNull.nullValue());
	}

	@Test
	public void shouldReturnEventRegisteredForIndexInOrder() {
		Object o = new Float(5f);
		InternalEventManager eventManager = new InternalEventManager();

		eventManager.registerEvent("event1", o);
		eventManager.registerEvent("event2", o);

		Event event1 = eventManager.getEvent(0);
		assertThat(event1, IsNull.notNullValue());
		assertThat(event1.getId(), IsEqual.equalTo("event1"));

		Event event2 = eventManager.getEvent(1);
		assertThat(event2, IsNull.notNullValue());
		assertThat(event2.getId(), IsEqual.equalTo("event2"));
	}

	@Test
	public void testClearEvents() {
		Object o = new Float(5f);
		InternalEventManager eventManager = new InternalEventManager();

		eventManager.registerEvent("event1", o);
		eventManager.registerEvent("event2", o);
		eventManager.registerEvent("event3", o);

		eventManager.clear();

		assertThat(eventManager.getEventCount(), IsEqual.equalTo(0));
	}

}
