package com.gemserk.componentsengine.utils;

import java.util.Map;

public interface Parameters {

	/**
	 * Returns the object from the map identified by id auto casting it to specified type.
	 * 
	 * @param <T>
	 *            The type of the returned object.
	 * @param id
	 *            The identifier of the object in the map.
	 */
	<T> T get(String id);

	/**
	 * Returns the object from the map identified by id auto casting it to specified type.
	 * 
	 * @param <T>
	 *            The type of the returned object.
	 * @param id
	 *            The identifier of the object in the map.
	 * @param defaultValue
	 *            The default value of the returned object if the map doesn't contains any object identified by id.
	 */
	<T> T get(String id, T defaultValue);

	/**
	 * Puts a new object in the map.
	 * 
	 * @param id
	 *            The identifier of the object.
	 * @param value
	 *            The object to add to the map.
	 */
	Parameters put(String id, Object value);

	/**
	 * Puts all the values from the specified map.
	 * 
	 * @param values
	 *            A map of values to add to the parameters.
	 */
	Parameters putAll(Map<String, Object> values);
	
	/**
	 * Clears the map removing all the values from it.
	 */
	void clear();

}