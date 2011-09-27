package com.gemserk.commons.reflection;

public interface Provider {

	// note: don't like the name because it is not a common Provider, this one does more stuff than expected I believe.
	// should be something like CachedConfiguratedInstances

	/**
	 * Configures the specified instance and caches it.
	 */
	<T> T get(T t);

	/**
	 * Instantiates a new instance of the specified class, configures it and internally caches it.
	 */
	<T> T get(Class<? extends T> clazz);

}