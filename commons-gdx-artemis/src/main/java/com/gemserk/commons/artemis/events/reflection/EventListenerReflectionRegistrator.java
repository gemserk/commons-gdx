package com.gemserk.commons.artemis.events.reflection;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gemserk.commons.artemis.events.Event;
import com.gemserk.commons.artemis.events.EventListenerManager;

public class EventListenerReflectionRegistrator {

	protected static final Logger logger = LoggerFactory.getLogger(EventListenerReflectionRegistrator.class);
	
	private static final Class<Handles> handlesClass = Handles.class;
	private static final Class<Event> eventClass = Event.class;
	
	private static final Map<Class<?>, Map<String, Method>> cachedMethodsPerClass = new HashMap<Class<?>, Map<String, Method>>();

	// use a Pool of MethodEventListeners

	private static InvokeMethodEventListener getMethodEventListener() {
		return new InvokeMethodEventListener();
	}

	// On another class and with cache and stuff
	
	private static Map<String, Method> getClassCachedMethods(Class<?> clazz){
		if (!cachedMethodsPerClass.containsKey(clazz))
			cachedMethodsPerClass.put(clazz, new HashMap<String, Method>());
		return cachedMethodsPerClass.get(clazz);
	}

	private static Method getMethod(String name, Class<?> clazz) {
		Map<String, Method> cachedMethods = getClassCachedMethods(clazz);
		if (cachedMethods.containsKey(name))
			return cachedMethods.get(name);
		try {
			Method method = clazz.getMethod(name, eventClass);
			cachedMethods.put(name, method);
			return method;
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
		InvokeMethodEventListener invokeMethodEventListener = getMethodEventListener();
		invokeMethodEventListener.setOwner(o);
		invokeMethodEventListener.setMethod(method);
		eventListenerManager.register(eventId, invokeMethodEventListener);
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