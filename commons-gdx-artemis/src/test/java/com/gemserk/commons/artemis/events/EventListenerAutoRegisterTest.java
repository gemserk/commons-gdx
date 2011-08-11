package com.gemserk.commons.artemis.events;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.junit.Assert.assertThat;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gemserk.commons.artemis.scripts.ScriptJavaImpl;

public class EventListenerAutoRegisterTest {

	// someone caches classes's methods

	// someone registers methods for event id, using reflection or not.

	@Target({ METHOD })
	@Retention(RUNTIME)
	@interface Handles {

		public String[] ids() default {};

	}

	static class MethodEventListener extends EventListener {

		private Object owner;
		private Method method;

		public void setMethod(Method method) {
			this.method = method;
		}

		public void setOwner(Object owner) {
			this.owner = owner;
		}

		@Override
		public void onEvent(Event event) {
			try {
				method.invoke(owner, event);
			} catch (Exception e) {
				throw new RuntimeException("Failed to invoke method " + method.getName() + " for event " + event.getId(), e);
			}
		}

	}

	static class EventListenerAutoRegister {

		protected static final Logger logger = LoggerFactory.getLogger(EventListenerAutoRegisterTest.EventListenerAutoRegister.class);

		// use a Pool of MethodEventListeners

		private static MethodEventListener getMethodEventListener() {
			return new MethodEventListener();
		}

		// On another class and with cache and stuff
		
		private static Method getMethod(String name, Class<?> clazz) {
			try {
				return clazz.getMethod(name, Event.class);
			} catch (SecurityException e) {
				if (logger.isErrorEnabled())
					logger.error("Failed to get method " + name, e);
			} catch (NoSuchMethodException e) {
				if (logger.isErrorEnabled())
					logger.error("Failed to get method " + name, e);
			}
			return null;
		}

		public static void registerEventListener(String eventId, final Object o, EventListenerManager eventListenerManager) {
			// On ComponentsEngine Component methods were cached to improve performance when registering for events and more.
			final Method method = getMethod(eventId, o.getClass());
			if (method == null) {
				if (logger.isErrorEnabled())
					logger.error("Failed to register EventListener for event " + eventId);
				return;
			}
			method.setAccessible(true);
			MethodEventListener methodEventListener = getMethodEventListener();
			methodEventListener.setOwner(o);
			methodEventListener.setMethod(method);
			eventListenerManager.register(eventId, methodEventListener);
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
