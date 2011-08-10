package com.gemserk.commons.artemis.events;

import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

import com.gemserk.componentsengine.utils.RandomAccessSet;

public class EventListenerManagerTest {

	interface EventListener {

		void onEvent(Event event);

	}

	class EventListenerMock implements EventListener {

		boolean wasCalled = false;

		@Override
		public void onEvent(Event event) {
			wasCalled = true;
		}

	}
	
	class EventListenerManagerImpl {

		private final Map<String, RandomAccessSet<EventListener>> eventListeners = new HashMap<String, RandomAccessSet<EventListener>>();

		public void register(String eventId, EventListener listener) {
			RandomAccessSet<EventListener> listeners = getListenersForEvent(eventId);
			listeners.add(listener);
		}

		public void unregister(String eventId, EventListener listener) {
			RandomAccessSet<EventListener> listeners = getListenersForEvent(eventId);
			listeners.remove(listener);
		}
		
		// TODO: use optimized RandomAccessMap to iterate better over the map keys.
		
		public void unregister(EventListener listener) {
			Set<String> keySet = eventListeners.keySet();
			for (String key : keySet)
				eventListeners.get(key).remove(listener);
		}

		private RandomAccessSet<EventListener> getListenersForEvent(String event) {
			if (!eventListeners.containsKey(event))
				eventListeners.put(event, new RandomAccessSet<EventListener>());
			return eventListeners.get(event);
		}

		public void process(Event event) {
			// Don't know exactly if it should create the collection if it wasn't there, could be useful
			// because it is preparing for a probable listener registration.
			// ArrayList<EventListener> listeners = getListenersForEvent(event.getId());
			RandomAccessSet<EventListener> listeners = eventListeners.get(event.getId());
			if (listeners == null)
				return;
			for (int i = 0; i < listeners.size(); i++)
				listeners.get(i).onEvent(event);
		}

	}

	@Test
	public void testRegisterListenerForEventAndProcess() {
		EventListenerMock eventListener = new EventListenerMock();
		EventListenerManagerImpl eventListenerManagerImpl = new EventListenerManagerImpl();

		Event event = new Event();
		event.setId("eventId");

		eventListenerManagerImpl.register(event.getId(), eventListener);
		eventListenerManagerImpl.process(event);

		assertThat(eventListener.wasCalled, IsEqual.equalTo(true));
	}

	@Test
	public void shouldNotCallListenerIfEventIdDontMatch() {
		EventListenerMock eventListener = new EventListenerMock();
		EventListenerManagerImpl eventListenerManagerImpl = new EventListenerManagerImpl();

		Event event = new Event();
		event.setId("eventId");

		eventListenerManagerImpl.register("anotherEvent", eventListener);
		eventListenerManagerImpl.process(event);

		assertThat(eventListener.wasCalled, IsEqual.equalTo(false));
	}

	@Test
	public void shouldNotCallListenerIfItWasUnregisteredForThatEvent() {
		EventListenerMock eventListener = new EventListenerMock();
		EventListenerManagerImpl eventListenerManagerImpl = new EventListenerManagerImpl();

		Event event = new Event();
		event.setId("eventId");

		eventListenerManagerImpl.register(event.getId(), eventListener);
		eventListenerManagerImpl.unregister(event.getId(), eventListener);
		eventListenerManagerImpl.process(event);

		assertThat(eventListener.wasCalled, IsEqual.equalTo(false));
	}
	
	@Test
	public void shouldRegisterOnlyOnceForAnEvent() {
		EventListenerMock eventListener = new EventListenerMock();
		EventListenerManagerImpl eventListenerManagerImpl = new EventListenerManagerImpl();

		Event event = new Event();
		event.setId("eventId");

		eventListenerManagerImpl.register(event.getId(), eventListener);
		eventListenerManagerImpl.register(event.getId(), eventListener);
		eventListenerManagerImpl.unregister(event.getId(), eventListener);
		eventListenerManagerImpl.process(event);

		assertThat(eventListener.wasCalled, IsEqual.equalTo(false));
	}

	@Test
	public void shouldNotCallListenerIfItWasUnregisteredForAllEvents() {
		EventListenerMock eventListener = new EventListenerMock();
		EventListenerManagerImpl eventListenerManagerImpl = new EventListenerManagerImpl();

		Event event = new Event();
		event.setId("eventId");

		eventListenerManagerImpl.register(event.getId(), eventListener);
		eventListenerManagerImpl.unregister(eventListener);
		eventListenerManagerImpl.process(event);

		assertThat(eventListener.wasCalled, IsEqual.equalTo(false));
	}

}
