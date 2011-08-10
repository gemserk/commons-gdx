package com.gemserk.commons.artemis.events;

import static org.junit.Assert.assertThat;


import org.hamcrest.core.IsEqual;
import org.junit.Test;


public class EventListenerManagerTest {

	class EventListenerMock extends EventListener {

		boolean wasCalled = false;

		@Override
		public void onEvent(Event event) {
			wasCalled = true;
		}

	}

	@Test
	public void testRegisterListenerForEventAndProcess() {
		EventListenerMock eventListener = new EventListenerMock();
		EventListenerManager eventListenerManagerImpl = new EventListenerManagerImpl();

		Event event = new Event();
		event.setId("eventId");

		eventListenerManagerImpl.register(event.getId(), eventListener);
		eventListenerManagerImpl.process(event);

		assertThat(eventListener.wasCalled, IsEqual.equalTo(true));
	}

	@Test
	public void shouldNotCallListenerIfEventIdDontMatch() {
		EventListenerMock eventListener = new EventListenerMock();
		EventListenerManager eventListenerManager = new EventListenerManagerImpl();

		Event event = new Event();
		event.setId("eventId");

		eventListenerManager.register("anotherEvent", eventListener);
		eventListenerManager.process(event);

		assertThat(eventListener.wasCalled, IsEqual.equalTo(false));
	}

	@Test
	public void shouldNotCallListenerIfItWasUnregisteredForThatEvent() {
		EventListenerMock eventListener = new EventListenerMock();
		EventListenerManager eventListenerManager = new EventListenerManagerImpl();

		Event event = new Event();
		event.setId("eventId");

		eventListenerManager.register(event.getId(), eventListener);
		eventListenerManager.unregister(event.getId(), eventListener);
		eventListenerManager.process(event);

		assertThat(eventListener.wasCalled, IsEqual.equalTo(false));
	}

	@Test
	public void shouldRegisterOnlyOnceForAnEvent() {
		EventListenerMock eventListener = new EventListenerMock();
		EventListenerManager eventListenerManager = new EventListenerManagerImpl();

		Event event = new Event();
		event.setId("eventId");

		eventListenerManager.register(event.getId(), eventListener);
		eventListenerManager.register(event.getId(), eventListener);
		eventListenerManager.unregister(event.getId(), eventListener);
		eventListenerManager.process(event);

		assertThat(eventListener.wasCalled, IsEqual.equalTo(false));
	}

	@Test
	public void shouldNotCallListenerIfItWasUnregisteredForAllEvents() {
		EventListenerMock eventListener = new EventListenerMock();
		EventListenerManager eventListenerManager = new EventListenerManagerImpl();

		Event event = new Event();
		event.setId("eventId");

		eventListenerManager.register(event.getId(), eventListener);
		eventListenerManager.unregister(eventListener);
		eventListenerManager.process(event);

		assertThat(eventListener.wasCalled, IsEqual.equalTo(false));
	}

}
