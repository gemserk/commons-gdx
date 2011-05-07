package com.gemserk.commons.gdx.resources;

public interface ResourceBuilder<T> {

	/**
	 * Returns true if a new Resource should be created each time the resourceManager is called, false if the same Resource should be returned.
	 */
	boolean isCached();

	T build();

}