package com.gemserk.commons.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.gemserk.componentsengine.utils.RandomAccessMap;
import com.gemserk.componentsengine.utils.RandomAccessWithKey;

public class ReflectionUtils {

	// Could be optimized?

	public static class ClassCache {

		private final Class<?> clazz;
		private final Map<String, Method> cachedMethods = new HashMap<String, Method>();
		private final Map<String, Field> cachedFields = new HashMap<String, Field>();
		private final RandomAccessMap<String, InternalField> cachedInternalFields = new RandomAccessMap<String, InternalField>();

		public ClassCache(Class<?> clazz) {
			this.clazz = clazz;
			Field[] fields = clazz.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				field.setAccessible(true);
				String name = field.getName();
				cachedInternalFields.put(name, getInternalField(name, field));
				cachedFields.put(name, field);
			}
		}

		public Method getSetter(String fieldName) {
			return findMethod(getSetterName(fieldName));
		}

		public Method getGetter(String fieldName) {
			return findMethod(getGetterName(fieldName));
		}

		private InternalField getInternalField(String fieldName, Field field) {
			Method setter = getSetter(fieldName);
			Method getter = getGetter(fieldName);

			if (setter != null && getter != null)
				return new InternalFieldMethodsReflectionImpl(fieldName, getter, setter);
			else
				return new InternalFieldDirectImpl(fieldName, field);
		}

		public RandomAccessWithKey<String, InternalField> getInternalFields() {
			return cachedInternalFields;
		}

		public Method findMethod(String methodName) {
			if (!cachedMethods.containsKey(methodName))
				cachedMethods.put(methodName, internalFindMethod(methodName));
			return cachedMethods.get(methodName);
		}

		protected Method internalFindMethod(String methodName) {
			Method[] methods = clazz.getMethods();
			for (Method method : methods) {
				if (method.getName().equals(methodName)) {
					method.setAccessible(true);
					return method;
				}
			}
			return null;
		}

		public Field getField(String fieldName) {
			// one problem here is we are caching only declared field on this class, not parents or anything...
			if (!cachedFields.containsKey(fieldName)) {
				try {
					cachedFields.put(fieldName, clazz.getDeclaredField(fieldName));
				} catch (SecurityException e) {
					cachedFields.put(fieldName, null);
				} catch (NoSuchFieldException e) {
					cachedFields.put(fieldName, null);
				}
			}
			return cachedFields.get(fieldName);
		}

	}

	private static Map<Class<?>, ClassCache> classCacheMap = new HashMap<Class<?>, ClassCache>();

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

	public static ClassCache getClassCache(Class<?> clazz) {
		if (!classCacheMap.containsKey(clazz))
			classCacheMap.put(clazz, new ClassCache(clazz));
		return classCacheMap.get(clazz);
	}

}
