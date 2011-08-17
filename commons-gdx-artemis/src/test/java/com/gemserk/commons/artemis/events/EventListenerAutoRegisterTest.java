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

	static class EventListenerReflectionRegistrator {

		protected static final Logger logger = LoggerFactory.getLogger(EventListenerAutoRegisterTest.EventListenerReflectionRegistrator.class);
		
		private static final Class<Handles> handlesClass = Handles.class;
		private static final Class<Event> eventClass = Event.class;

		// use a Pool of MethodEventListeners

		private static MethodEventListener getMethodEventListener() {
			return new MethodEventListener();
		}

		// On another class and with cache and stuff

		private static Method getMethod(String name, Class<?> clazz) {
			try {
				return clazz.getMethod(name, eventClass);
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
			registerEventListenerForMethod(eventId, o, method, eventListenerManager);
		}

		private static void registerEventListenerForMethod(String eventId, final Object o, final Method method, EventListenerManager eventListenerManager) {
			method.setAccessible(true);
			MethodEventListener methodEventListener = getMethodEventListener();
			methodEventListener.setOwner(o);
			methodEventListener.setMethod(method);
			eventListenerManager.register(eventId, methodEventListener);
		}

		/**
		 * Registers all methods from object o with @Handles annotation.
		 * 
		 * @param o
		 *            The object to register the methods as event listeners.
		 * @param eventListenerManager
		 *            The EventListenerManager to register the event listeners to.
		 */
		public static void registerEventListeners(Object o, EventListenerManager eventListenerManager) {
			Class<?> clazz = o.getClass();
			Method[] methods = clazz.getMethods();
			for (int i = 0; i < methods.length; i++) {
				Method method = methods[i];
				Handles handlesAnnotation = method.getAnnotation(handlesClass);
				if (handlesAnnotation == null)
					continue;
				String[] eventIds = handlesAnnotation.ids();
				for (int j = 0; j < eventIds.length; j++) {
					String eventId = eventIds[i];
					registerEventListenerForMethod(eventId, o, method, eventListenerManager);
				}
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

}
