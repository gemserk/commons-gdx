package com.gemserk.commons.gdx.resources;

/**
 * Defines an API for Resource builders.
 * 
 * @author acoppes
 * 
 */
public interface ResourceBuilder<T> {

	/**
	 * Returns true if the Resource should be considered volatile and not cached, false otherwise.
	 */
	boolean isVolatile();

	/**
	 * Defines how to build the data to be used in the Resource.
	 */
	T build();

}