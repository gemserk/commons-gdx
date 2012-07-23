package com.gemserk.commons.reflection;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gemserk.commons.reflection.ReflectionUtils.ClassCache;
import com.gemserk.componentsengine.utils.RandomAccessMap;
import com.gemserk.componentsengine.utils.RandomAccessWithKey;

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
	public <T> T injectMembers(T object) {

		// For now, it will only work for the declared fields of the object class, it will not try to set fields from its super classes.

		Class<?> clazz = object.getClass();

		ClassCache classCache = ReflectionUtils.getClassCache(clazz);
		RandomAccessWithKey<String, InternalField> fields = classCache.getInternalFields();

		for (int i = 0; i < fields.size(); i++) {
			// Field field = fields[i];
			InternalField field = fields.get(i);
			String fieldName = field.getFieldName();

			Object value = configurationMap.get(fieldName);

			if (value == null)
				// if (logger.isDebugEnabled())
				// logger.debug("Couldn't find value for field " + fieldName + " from " + clazz);
				continue;
			// }

			field.setValue(object, value);
			// setField(object, fieldName, value);
		}

		return object;
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