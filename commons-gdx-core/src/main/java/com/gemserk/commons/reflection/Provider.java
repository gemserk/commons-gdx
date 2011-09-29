package com.gemserk.commons.reflection;

public interface Provider {

	/**
	 * Instantiates a new instance of the specified class, configures it and internally caches it.
	 */
	<T> T getInstance(Class<? extends T> clazz);

}