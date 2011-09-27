package com.gemserk.commons.reflection;

import java.util.HashMap;
import java.util.Map;

public class ProviderImpl implements Provider {

	private final ObjectConfigurator objectConfigurator;
	private Map<Class<?>, Object> instances;

	public ProviderImpl(ObjectConfigurator objectConfigurator) {
		this.objectConfigurator = objectConfigurator;
		this.instances = new HashMap<Class<?>, Object>();
	}

	@Override
	public <T> T get(T t) {
		objectConfigurator.configure(t);
		instances.put(t.getClass(), t);
		return t;
	}

	@Override
	public <T> T get(Class<? extends T> clazz) {
		if (instances.containsKey(clazz))
			return clazz.cast(instances.get(clazz));
		try {
			T t = clazz.newInstance();
			instances.put(clazz, t);
			return get(t);
		} catch (Exception e) {
			throw new RuntimeException("Failed to create an instance of " + clazz, e);
		}
	}

}