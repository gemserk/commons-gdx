package com.gemserk.commons.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gemserk.componentsengine.utils.RandomAccessMap;

/**
 * Basic implementation of our Injector interface, some limitations are that it works with singleton instances only and it can't inject superclass fields.
 * 
 * @author acoppes
 */
public class InjectorImpl implements Injector {

	protected static final Logger logger = LoggerFactory.getLogger(InjectorImpl.class);

	private Map<Class<?>, Object> instances;
	private RandomAccessMap<String, Object> configurationMap;

	public InjectorImpl() {
		this.instances = new HashMap<Class<?>, Object>();
		this.configurationMap = new RandomAccessMap<String, Object>();
		configurationMap.put("injector", this);
	}

	@Override
	public void injectMembers(Object object) {

		// For now, it will only work for the declared fields of the object class, it will not try to set fields from its super classes.

		Class<?> clazz = object.getClass();
		Field[] fields = clazz.getDeclaredFields();

		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			String fieldName = field.getName();
			Object value = configurationMap.get(fieldName);

			if (value == null)
				// if (logger.isDebugEnabled())
				// logger.debug("Couldn't find value for field " + fieldName + " from " + clazz);
				continue;
			// }

			setField(object, fieldName, value);
		}

	}

	protected void setField(Object object, String fieldName, Object value) {
		Class<?> clazz = object.getClass();
		Method setter = ReflectionUtils.getSetter(clazz, fieldName);

		String setterName = ReflectionUtils.getSetterName(fieldName);

		if (setter == null) {

			// try to access the field directly....

			try {
				Field field = clazz.getDeclaredField(fieldName);

				if (!field.isAccessible()) {
					if (logger.isDebugEnabled()) {
						logger.debug(setterName + "() method not found in " + object.getClass());
						logger.debug("making field public");
					}
					field.setAccessible(true);
				}

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

	@Override
	public void bind(String name, Object value) {
		configurationMap.put(name, value);
	}

	private <T> T configure(T t) {
		injectMembers(t);
		instances.put(t.getClass(), t);
		return t;
	}

	@Override
	public <T> T getInstance(Class<? extends T> clazz) {
		if (instances.containsKey(clazz))
			return clazz.cast(instances.get(clazz));
		try {
			T t = clazz.newInstance();
			return configure(t);
		} catch (Exception e) {
			throw new RuntimeException("Failed to create an instance of " + clazz, e);
		}
	}

	@Override
	public Injector createChildInjector() {

		// for now, child injector is not related any more with parent after creation.

		InjectorImpl childInjector = new InjectorImpl();
		childInjector.configurationMap.putAll(configurationMap);
		childInjector.instances.putAll(instances);

		childInjector.configurationMap.put("injector", childInjector);

		return childInjector;
	}

}