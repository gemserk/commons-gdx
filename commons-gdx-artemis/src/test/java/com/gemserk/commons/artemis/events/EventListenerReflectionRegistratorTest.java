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
		EventManagerImpl eventManager = new EventManagerImpl();

		MyScript myScript = new MyScript();

		EventListenerReflectionRegistrator eventListenerReflectionRegistrator = new EventListenerReflectionRegistrator(eventManager);
		eventListenerReflectionRegistrator.registerEventListener("customEvent", myScript);

		Event event = new Event();
		event.setId("customEvent");
		eventManager.process(event);

		assertThat(myScript.wasCalled, IsEqual.equalTo(true));
	}

	@Test
	public void shouldNotRegisterEventListenerIfNoMethodForEvent() {
		EventManagerImpl eventManager = new EventManagerImpl();

		MyScript myScript = new MyScript();

		EventListenerReflectionRegistrator eventListenerReflectionRegistrator = new EventListenerReflectionRegistrator(eventManager);
		eventListenerReflectionRegistrator.registerEventListener("customEvent2", myScript);

		Event event = new Event();
		event.setId("customEvent");
		eventManager.process(event);

		assertThat(myScript.wasCalled, IsEqual.equalTo(false));
	}

	boolean anonymousWasCalled;

	@Test
	public void shouldRegisterEventListenerForMethodWithEventNameOnAnonymousClass() {
		EventManagerImpl eventManager = new EventManagerImpl();

		anonymousWasCalled = false;

		Object myScript = new ScriptJavaImpl() {
			@SuppressWarnings("unused")
			public void customEvent(Event e) {
				anonymousWasCalled = true;
			}
		};

		EventListenerReflectionRegistrator eventListenerReflectionRegistrator = new EventListenerReflectionRegistrator(eventManager);
		eventListenerReflectionRegistrator.registerEventListener("customEvent", myScript);

		Event event = new Event();
		event.setId("customEvent");
		eventManager.process(event);

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
		EventManagerImpl eventManager = new EventManagerImpl();
		MyScript2 myScript2 = new MyScript2();
		
		EventListenerReflectionRegistrator eventListenerReflectionRegistrator = new EventListenerReflectionRegistrator(eventManager);
		eventListenerReflectionRegistrator.registerEventListeners(myScript2);
		
		Event event = new Event();
		event.setId("customEvent");
		eventManager.process(event);
		
		assertThat(myScript2.wasCalled, IsEqual.equalTo(false));
		assertThat(myScript2.wasCalled2, IsEqual.equalTo(false));
		
		event = new Event();
		event.setId("anotherEvent");
		eventManager.process(event);
		
		assertThat(myScript2.wasCalled, IsEqual.equalTo(true));
		assertThat(myScript2.wasCalled2, IsEqual.equalTo(false));
	}
	
	@Test
	public void shouldUnregisterMethodWithAnnotation() {
		EventManagerImpl eventManager = new EventManagerImpl();
		MyScript2 myScript2 = new MyScript2();
		
		EventListenerReflectionRegistrator eventListenerReflectionRegistrator = new EventListenerReflectionRegistrator(eventManager);
		
		eventListenerReflectionRegistrator.registerEventListeners(myScript2);
		eventListenerReflectionRegistrator.unregisterEventListeners(myScript2);
		
		Event event = new Event();
		event.setId("anotherEvent");
		eventManager.process(event);
		
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
		EventManagerImpl eventManager = new EventManagerImpl();
		MyScript3 o = new MyScript3();
		
		EventListenerReflectionRegistrator eventListenerReflectionRegistrator = new EventListenerReflectionRegistrator(eventManager);
		
		eventListenerReflectionRegistrator.registerEventListeners(o);
		
		Event event = new Event();
		event.setId("customEvent");
		eventManager.process(event);
		
		assertThat(o.wasCalled, IsEqual.equalTo(true));
	}
	
	@Test
	public void shouldUnregisterMethodWithAnnotationForEventWithSameName() {
		EventManagerImpl eventManager = new EventManagerImpl();
		MyScript3 o = new MyScript3();
		
		EventListenerReflectionRegistrator eventListenerReflectionRegistrator = new EventListenerReflectionRegistrator(eventManager);
		
		eventListenerReflectionRegistrator.registerEventListeners(o);
		eventListenerReflectionRegistrator.unregisterEventListeners(o);
		
		Event event = new Event();
		event.setId("customEvent");
		eventManager.process(event);
		
		assertThat(o.wasCalled, IsEqual.equalTo(false));
	}
	
	class MyScript4 extends ScriptJavaImpl {

		@Handles(ids="a")
		public void customEvent(Event e) {
			
		}

		@Handles(ids="b")
		public void customEvent2(Event e) {
			
		}

	}
	
	@Test
	public void bugArrayOutOfBoundsWhenRegisterOneIdsButMultipleMethodsWithHandes() {
		EventManager eventManager = new EventManagerImpl();
		MyScript4 o = new MyScript4();
		EventListenerReflectionRegistrator eventListenerReflectionRegistrator = new EventListenerReflectionRegistrator(eventManager);
		eventListenerReflectionRegistrator.registerEventListeners(o);
	}
	
	@Test
	public void bugArrayOutOfBoundsWhenUnregisterOneIdsButMultipleMethodsWithHandes() {
		EventManager eventManager = new EventManagerImpl();
		MyScript4 o = new MyScript4();
		EventListenerReflectionRegistrator eventListenerReflectionRegistrator = new EventListenerReflectionRegistrator(eventManager);
		eventListenerReflectionRegistrator.registerEventListeners(o);
		eventListenerReflectionRegistrator.unregisterEventListeners(o);
	}

}
