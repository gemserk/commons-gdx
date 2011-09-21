package com.gemserk.componentsengine.properties;

/**
 * Provides an abstraction to get and set values from an object.
 */
public interface Property<T extends Object> {

	/**
	 * Gets the value.
	 */
	T get();

	/**
	 * Sets the value.
	 */
	void set(T value);

}