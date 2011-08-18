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
		EventManager eventListenerManager = new EventListenerManagerImpl();

		MyScript myScript = new MyScript();

		EventListenerReflectionRegistrator eventListenerReflectionRegistrator = new EventListenerReflectionRegistrator(eventListenerManager);
		eventListenerReflectionRegistrator.registerEventListener("customEvent", myScript);

		Event event = new Event();
		event.setId("customEvent");
		eventListenerManager.process(event);

		assertThat(myScript.wasCalled, IsEqual.equalTo(true));
	}

	@Test
	public void shouldNotRegisterEventListenerIfNoMethodForEvent() {
		EventManager eventListenerManager = new EventListenerManagerImpl();

		MyScript myScript = new MyScript();

		EventListenerReflectionRegistrator eventListenerReflectionRegistrator = new EventListenerReflectionRegistrator(eventListenerManager);
		eventListenerReflectionRegistrator.registerEventListener("customEvent2", myScript);

		Event event = new Event();
		event.setId("customEvent");
		eventListenerManager.process(event);

		assertThat(myScript.wasCalled, IsEqual.equalTo(false));
	}

	boolean anonymousWasCalled;

	@Test
	public void shouldRegisterEventListenerForMethodWithEventNameOnAnonymousClass() {
		EventManager eventListenerManager = new EventListenerManagerImpl();

		anonymousWasCalled = false;

		Object myScript = new ScriptJavaImpl() {
			public void customEvent(Event e) {
				anonymousWasCalled = true;
			}
		};

		EventListenerReflectionRegistrator eventListenerReflectionRegistrator = new EventListenerReflectionRegistrator(eventListenerManager);
		eventListenerReflectionRegistrator.registerEventListener("customEvent", myScript);

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
		EventManager eventListenerManager = new EventListenerManagerImpl();
		MyScript2 myScript2 = new MyScript2();
		
		EventListenerReflectionRegistrator eventListenerReflectionRegistrator = new EventListenerReflectionRegistrator(eventListenerManager);
		eventListenerReflectionRegistrator.registerEventListeners(myScript2);
		
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
		EventManager eventListenerManager = new EventListenerManagerImpl();
		MyScript2 myScript2 = new MyScript2();
		
		EventListenerReflectionRegistrator eventListenerReflectionRegistrator = new EventListenerReflectionRegistrator(eventListenerManager);
		
		eventListenerReflectionRegistrator.registerEventListeners(myScript2);
		eventListenerReflectionRegistrator.unregisterEventListeners(myScript2);
		
		Event event = new Event();
		event.setId("anotherEvent");
		eventListenerManager.process(event);
		
		assertThat(myScript2.wasCalled, IsEqual.equalTo(false));
		assertThat(myScript2.wasCalled2, IsEqual.equalTo(false));
	}
	
	class MyScript3 extends ScriptJavaImpl {

		boolean wasCalled = false;

		@Handles
		public void customEvent(Event e) {
			wasCalled = true;
		}

	}
	
	@Test
	public void shouldRegisterMethodWithAnnotationForEventWithSameName() {
		EventManager eventListenerManager = new EventListenerManagerImpl();
		MyScript3 o = new MyScript3();
		
		EventListenerReflectionRegistrator eventListenerReflectionRegistrator = new EventListenerReflectionRegistrator(eventListenerManager);
		
		eventListenerReflectionRegistrator.registerEventListeners(o);
		
		Event event = new Event();
		event.setId("customEvent");
		eventListenerManager.process(event);
		
		assertThat(o.wasCalled, IsEqual.equalTo(true));
	}
	
	@Test
	public void shouldUnregisterMethodWithAnnotationForEventWithSameName() {
		EventManager eventListenerManager = new EventListenerManagerImpl();
		MyScript3 o = new MyScript3();
		
		EventListenerReflectionRegistrator eventListenerReflectionRegistrator = new EventListenerReflectionRegistrator(eventListenerManager);
		
		eventListenerReflectionRegistrator.registerEventListeners(o);
		eventListenerReflectionRegistrator.unregisterEventListeners(o);
		
		Event event = new Event();
		event.setId("customEvent");
		eventListenerManager.process(event);
		
		assertThat(o.wasCalled, IsEqual.equalTo(false));
	}

}
