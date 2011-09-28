package com.gemserk.commons.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gemserk.animation4j.reflection.ReflectionUtils;
import com.gemserk.componentsengine.utils.RandomAccessMap;

/**
 * Provides a very basic dependency injection by calling setter methods over the fields of an object.
 * 
 * @author acoppes
 */
public class ObjectConfigurator {

	protected static final Logger logger = LoggerFactory.getLogger(ObjectConfigurator.class);

	private RandomAccessMap<String, Object> configurationMap;

	public ObjectConfigurator() {
		configurationMap = new RandomAccessMap<String, Object>();
	}

	/**
	 * For each field of the object, configures its value using the registered values with the add() method. 
	 * For now, it will only work for the declared fields of the object class, it will not try to set fields from its super classes.
	 * 
	 * @param object
	 *            The object to be configured (or injected).
	 */
	public void configure(Object object) {

		Class<?> clazz = object.getClass();
		Field[] fields = clazz.getDeclaredFields();

		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			String fieldName = field.getName();
			Object value = configurationMap.get(fieldName);

			if (value == null) {
				logger.debug("Couldn't find value for field " + fieldName + " from " + clazz);
				continue;
			}

			setField(object, fieldName, value);
		}

	}

	protected void setField(Object object, String fieldName, Object value) {
		Class<?> clazz = object.getClass();
		Method setter = ReflectionUtils.getSetter(clazz, fieldName);

		String setterName = ReflectionUtils.getSetterName(fieldName);

		if (setter == null) {

			// try to access the field directly....

			logger.debug(setterName + "() method not found in " + object.getClass());
			logger.debug("trying access field...");

			try {
				Field field = clazz.getDeclaredField(fieldName);
				field.setAccessible(true);
				field.set(object, value);
			} catch (Exception e) {
				throw new RuntimeException("Failed to set value to field " + fieldName, e);
			}

		} else {
			try {
				setter.setAccessible(true);
				setter.invoke(object, value);
			} catch (Exception e) {
				throw new RuntimeException("failed to set value on " + setterName + "() method from " + object.getClass(), e);
			}
		}
	}

	/**
	 * Registers a new value to be set when calling configure() method.
	 * 
	 * @param name
	 *            The name of the value.
	 * @param value
	 *            The value.
	 */
	public void add(String name, Object value) {
		configurationMap.put(name, value);
	}

}