package com.gemserk.commons.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ReflectionUtils {

	// Could be optimized?

	private static class ClassCache {

		Map<String, Method> cachedMethods = new HashMap<String, Method>();
		Map<String, Field> cachedFields = new HashMap<String, Field>();

	}

	private static Map<Class, ClassCache> classCacheMap = new HashMap<Class, ClassCache>();
	private static Map<String, String> cachedGettersMap = new HashMap<String, String>();
	private static Map<String, String> cachedSettersMap = new HashMap<String, String>();

	public static String getSetterName(String fieldName) {
		if (!cachedSettersMap.containsKey(fieldName))
			cachedSettersMap.put(fieldName, "set" + capitalize(fieldName));
		return cachedSettersMap.get(fieldName);
	}

	public static String getGetterName(String fieldName) {
		if (!cachedGettersMap.containsKey(fieldName))
			cachedGettersMap.put(fieldName, "get" + capitalize(fieldName));
		return cachedGettersMap.get(fieldName);
	}

	public static String capitalize(String name) {
		return name.toUpperCase().substring(0, 1) + name.substring(1);
	}

	public static Method findMethod(Class clazz, String methodName) {
		ClassCache classCacheEntry = getClassCache(clazz);

		Method method = classCacheEntry.cachedMethods.get(methodName);

		if (method != null)
			return method;

		method = internalFindMethod(clazz, methodName);

		if (method == null)
			return null;

		classCacheEntry.cachedMethods.put(methodName, method);

		return method;
	}

	protected static Method internalFindMethod(Class clazz, String methodName) {
		Method[] methods = clazz.getMethods();
		for (Method method : methods)
			if (method.getName().equals(methodName))
				return method;
		return null;
	}

	public static Method getSetter(Class clazz, String fieldName) {
		return findMethod(clazz, getSetterName(fieldName));
	}

	public static Method getGetter(Class clazz, String fieldName) {
		return findMethod(clazz, getGetterName(fieldName));
	}

	private static ClassCache getClassCache(Class clazz) {
		if (!classCacheMap.containsKey(clazz))
			classCacheMap.put(clazz, new ClassCache());
		return classCacheMap.get(clazz);
	}

	public static Object getFieldValue(Object object, String field) {
		String getterName = getGetterName(field);
		Method getterMethod = findMethod(object.getClass(), getterName);

		if (getterMethod == null)
			throw new RuntimeException(getterName + "() method not found in " + object.getClass());

		try {
			return getterMethod.invoke(object, (Object[]) null);
		} catch (Exception e) {
			throw new RuntimeException(getterName + "() method not found in " + object.getClass(), e);
		}
	}

	public static void setField(Object object, String fieldName, Object value) {
		Class<?> clazz = object.getClass();
		Method setter = ReflectionUtils.getSetter(clazz, fieldName);

		String setterName = getSetterName(fieldName);

		if (setter == null)
			throw new RuntimeException(setterName + "() method not found in " + object.getClass());

		try {
			setter.invoke(object, value);
		} catch (Exception e) {
			throw new RuntimeException("failed to set value on " + setterName + "() method from " + object.getClass(), e);
		}
	}

	public static Field getClassField(Class<?> clazz, String fieldName) throws NoSuchFieldException {
		ClassCache classCache = getClassCache(clazz);
		// one problem here is we are caching only declared field on this class, not parents or anything...
		if (!classCache.cachedFields.containsKey(fieldName))
			classCache.cachedFields.put(fieldName, clazz.getDeclaredField(fieldName));
		return classCache.cachedFields.get(fieldName);
	}
	
}
