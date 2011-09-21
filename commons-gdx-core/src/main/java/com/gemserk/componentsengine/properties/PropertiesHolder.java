package com.gemserk.componentsengine.properties;

import java.util.Map;

/**
 * A container of a collection of Property objects.
 */
public interface PropertiesHolder {

	/**
	 * Adds a property to the collection.
	 * 
	 * @param key
	 *            the identifier of the property in the collection
	 * @param value
	 *            the Property to be added.
	 */
	void addProperty(String key, Property<Object> value);

	/**
	 * Returns the contained Property identified by the key.
	 */
	Property<Object> getProperty(String key);

	/**
	 * Returns a map of all properties.
	 */
	Map<String, Property<Object>> getProperties();

}