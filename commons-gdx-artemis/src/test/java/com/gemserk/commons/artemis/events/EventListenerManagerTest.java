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
		EventManagerImpl eventManager = new EventManagerImpl();

		Event event = new Event();
		event.setId("eventId");

		eventManager.register(event.getId(), eventListener);
		eventManager.process(event);

		assertThat(eventListener.wasCalled, IsEqual.equalTo(true));
	}

	@Test
	public void shouldNotCallListenerIfEventIdDontMatch() {
		EventListenerMock eventListener = new EventListenerMock();
		EventManagerImpl eventManager = new EventManagerImpl();

		Event event = new Event();
		event.setId("eventId");

		eventManager.register("anotherEvent", eventListener);
		eventManager.process(event);

		assertThat(eventListener.wasCalled, IsEqual.equalTo(false));
	}

	@Test
	public void shouldNotCallListenerIfItWasUnregisteredForThatEvent() {
		EventListenerMock eventListener = new EventListenerMock();
		EventManagerImpl eventManager = new EventManagerImpl();

		Event event = new Event();
		event.setId("eventId");

		eventManager.register(event.getId(), eventListener);
		eventManager.unregister(event.getId(), eventListener);
		eventManager.process(event);

		assertThat(eventListener.wasCalled, IsEqual.equalTo(false));
	}

	@Test
	public void shouldRegisterOnlyOnceForAnEvent() {
		EventListenerMock eventListener = new EventListenerMock();
		EventManagerImpl eventManager = new EventManagerImpl();

		Event event = new Event();
		event.setId("eventId");

		eventManager.register(event.getId(), eventListener);
		eventManager.register(event.getId(), eventListener);
		eventManager.unregister(event.getId(), eventListener);
		eventManager.process(event);

		assertThat(eventListener.wasCalled, IsEqual.equalTo(false));
	}

	@Test
	public void shouldNotCallListenerIfItWasUnregisteredForAllEvents() {
		EventListenerMock eventListener = new EventListenerMock();
		EventManagerImpl eventManager = new EventManagerImpl();

		Event event = new Event();
		event.setId("eventId");

		eventManager.register(event.getId(), eventListener);
		eventManager.unregister(eventListener);
		eventManager.process(event);

		assertThat(eventListener.wasCalled, IsEqual.equalTo(false));
	}

}
