package com.gemserk.commons.artemis.events;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

import com.gemserk.commons.artemis.events.reflection.EventListenerReflectionRegistrator;
import com.gemserk.commons.artemis.events.reflection.Handles;
import com.gemserk.commons.artemis.scripts.ScriptJavaImpl;

public class EventListenerReflectionRegistratorTest {

	// someone caches classes's methods

	// someone registers methods for event id, using reflection or not.

	class MyScript extends ScriptJavaImpl {

		boolean wasCalled = false;

		public void customEvent(Event e) {
			wasCalled = true;
		}

	}

	@Test
	public void shouldRegisterEventListenerForMethodWithEventName() {
		EventListenerManager eventListenerManager = new EventListenerManagerImpl();

		MyScript myScript = new MyScript();

		EventListenerReflectionRegistrator.registerEventListener("customEvent", myScript, eventListenerManager);

		Event event = new Event();
		event.setId("customEvent");
		eventListenerManager.process(event);

		assertThat(myScript.wasCalled, IsEqual.equalTo(true));
	}

	@Test
	public void shouldNotRegisterEventListenerIfNoMethodForEvent() {
		EventListenerManager eventListenerManager = new EventListenerManagerImpl();

		MyScript myScript = new MyScript();

		EventListenerReflectionRegistrator.registerEventListener("customEvent2", myScript, eventListenerManager);

		Event event = new Event();
		event.setId("customEvent");
		eventListenerManager.process(event);

		assertThat(myScript.wasCalled, IsEqual.equalTo(false));
	}

	boolean anonymousWasCalled;

	@Test
	public void shouldRegisterEventListenerForMethodWithEventNameOnAnonymousClass() {
		EventListenerManager eventListenerManager = new EventListenerManagerImpl();

		anonymousWasCalled = false;

		Object myScript = new ScriptJavaImpl() {
			public void customEvent(Event e) {
				anonymousWasCalled = true;
			}
		};

		EventListenerReflectionRegistrator.registerEventListener("customEvent", myScript, eventListenerManager);

		Event event = new Event();
		event.setId("customEvent");
		eventListenerManager.process(event);

		assertThat(anonymousWasCalled, IsEqual.equalTo(true));
	}
	
	class MyScript2 extends ScriptJavaImpl {

		boolean wasCalled = false;
		boolean wasCalled2 = false;

		@Handles(ids={"anotherEvent"})
		public void customEvent(Event e) {
			wasCalled = true;
		}

		public void myEvent(Event e) {
			wasCalled2 = true;
		}

	}
	
	@Test
	public void shouldRegisterMethodWithAnnotation() {
		EventListenerManager eventListenerManager = new EventListenerManagerImpl();
		MyScript2 myScript2 = new MyScript2();
		
		EventListenerReflectionRegistrator.registerEventListeners(myScript2, eventListenerManager);
		
		Event event = new Event();
		event.setId("customEvent");
		eventListenerManager.process(event);
		
		assertThat(myScript2.wasCalled, IsEqual.equalTo(false));
		assertThat(myScript2.wasCalled2, IsEqual.equalTo(false));
		
		event = new Event();
		event.setId("anotherEvent");
		eventListenerManager.process(event);
		
		assertThat(myScript2.wasCalled, IsEqual.equalTo(true));
		assertThat(myScript2.wasCalled2, IsEqual.equalTo(false));
	}
	
	@Test
	public void shouldUnregisterMethodWithAnnotation() {
		EventListenerManager eventListenerManager = new EventListenerManagerImpl();
		MyScript2 myScript2 = new MyScript2();
		
		EventListenerReflectionRegistrator.registerEventListeners(myScript2, eventListenerManager);
		EventListenerReflectionRegistrator.unregisterEventListeners(myScript2, eventListenerManager);
		
		Event event = new Event();
		event.setId("anotherEvent");
		eventListenerManager.process(event);
		
		assertThat(myScript2.wasCalled, IsEqual.equalTo(false));
		assertThat(myScript2.wasCalled2, IsEqual.equalTo(false));
	}
	
	

}
