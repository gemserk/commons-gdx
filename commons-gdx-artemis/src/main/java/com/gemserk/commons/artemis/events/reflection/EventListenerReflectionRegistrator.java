package com.gemserk.commons.artemis.events.reflection;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gemserk.commons.artemis.events.Event;
import com.gemserk.commons.artemis.events.EventListenerManager;
import com.gemserk.componentsengine.utils.Pool;
import com.gemserk.componentsengine.utils.Pool.PoolObjectFactory;

public class EventListenerReflectionRegistrator {

	protected static final Logger logger = LoggerFactory.getLogger(EventListenerReflectionRegistrator.class);

	private static final Class<Handles> handlesClass = Handles.class;
	private static final Class<Event> eventClass = Event.class;

	private static final Map<Class<?>, Map<String, Method>> cachedMethodsPerClass = new HashMap<Class<?>, Map<String, Method>>();

	private static final PoolObjectFactory<InvokeMethodEventListener> invokeMethodEventListenerFactory = new PoolObjectFactory<InvokeMethodEventListener>() {

		@Override
		public InvokeMethodEventListener createObject() {
			return new InvokeMethodEventListener();
		}
	};

	private static final Pool<InvokeMethodEventListener> invokeMethodEventListenerPool = new Pool<InvokeMethodEventListener>(invokeMethodEventListenerFactory, 64);

	// On another class and with cache and stuff

	// this doesn't allows multiple event listeners per method
	private static final Map<Object, Map<Method, InvokeMethodEventListener>> createdMethodEventListeners = new HashMap<Object, Map<Method, InvokeMethodEventListener>>();

	private static Map<String, Method> getClassCachedMethods(Class<?> clazz) {
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
		InvokeMethodEventListener invokeMethodEventListener = invokeMethodEventListenerPool.newObject();
		invokeMethodEventListener.setOwner(o);
		invokeMethodEventListener.setMethod(method);
		eventListenerManager.register(eventId, invokeMethodEventListener);

		// used to be returned to the pool later.
		Map<Method, InvokeMethodEventListener> cachedMethodEventListeners = getCachedMethodEventListenersForObject(o);
		cachedMethodEventListeners.put(method, invokeMethodEventListener);
	}

	private static Map<Method, InvokeMethodEventListener> getCachedMethodEventListenersForObject(final Object o) {
		if (!createdMethodEventListeners.containsKey(o))
			createdMethodEventListeners.put(o, new HashMap<Method, InvokeMethodEventListener>());
		return createdMethodEventListeners.get(o);
	}

	private static void unregisterEventListenerForMethod(String eventId, final Object o, final Method method, EventListenerManager eventListenerManager) {
		Map<Method, InvokeMethodEventListener> cachedMethodEventListeners = getCachedMethodEventListenersForObject(o);
		InvokeMethodEventListener invokeMethodEventListener = cachedMethodEventListeners.get(method);
		eventListenerManager.unregister(eventId, invokeMethodEventListener);
		invokeMethodEventListenerPool.free(invokeMethodEventListener);
		cachedMethodEventListeners.remove(method);
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

	/**
	 * Unregisters all registered methods from object with @Handles annotation
	 * 
	 * @param o
	 *            The object
	 * @param eventListenerManager
	 */
	public static void unregisterEventListeners(Object o, EventListenerManager eventListenerManager) {
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
				unregisterEventListenerForMethod(eventId, o, method, eventListenerManager);
			}
		}
		createdMethodEventListeners.remove(o);
	}

}