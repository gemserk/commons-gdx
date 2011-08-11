package com.gemserk.commons.artemis.events;

import static org.junit.Assert.assertThat;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gemserk.commons.artemis.scripts.ScriptJavaImpl;

public class EventListenerAutoRegisterTest {

	static class EventListenerAutoRegister {

		protected static final Logger logger = LoggerFactory.getLogger(EventListenerAutoRegisterTest.EventListenerAutoRegister.class);

		public static void registerEventListener(String eventId, final Object o, EventListenerManager eventListenerManager) {
			try {
				// On ComponentsEngine Component methods were cached to improve performance when registering for events and more.
				final Method method = o.getClass().getMethod(eventId, Event.class);
				if (method == null)
					return;
				method.setAccessible(true);
				eventListenerManager.register(eventId, new EventListener() {
					@Override
					public void onEvent(Event event) {
						try {
							method.invoke(o, event);
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}
					}
				});
			} catch (SecurityException e) {
				if (logger.isErrorEnabled())
					logger.error("Failed to register EventListener for event " + eventId, e);
			} catch (NoSuchMethodException e) {
				if (logger.isErrorEnabled())
					logger.error("Failed to register EventListener for event " + eventId, e);
			}

		}

	}

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

		EventListenerAutoRegister.registerEventListener("customEvent", myScript, eventListenerManager);

		Event event = new Event();
		event.setId("customEvent");
		eventListenerManager.process(event);

		assertThat(myScript.wasCalled, IsEqual.equalTo(true));
	}

	@Test
	public void shouldNotRegisterEventListenerIfNoMethodForEvent() {
		EventListenerManager eventListenerManager = new EventListenerManagerImpl();

		MyScript myScript = new MyScript();

		EventListenerAutoRegister.registerEventListener("customEvent2", myScript, eventListenerManager);

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

		EventListenerAutoRegister.registerEventListener("customEvent", myScript, eventListenerManager);

		Event event = new Event();
		event.setId("customEvent");
		eventListenerManager.process(event);

		assertThat(anonymousWasCalled, IsEqual.equalTo(true));
	}

}
