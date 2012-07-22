package com.gemserk.commons.reflection;

/**
 * Provides an abstraction to access a field of a class.
 */
public interface InternalField {

	/**
	 * Returns the field name.
	 */
	String getFieldName();

	/**
	 * Returns the value of the field given an instance.
	 * 
	 * @param instance
	 *            The instance to get the value from.
	 */
	Object getValue(Object instance);

	/**
	 * Sets the value of the field given an instance.
	 * 
	 * @param instance
	 *            The instance to set the value to.
	 * @param value
	 *            The value to set.
	 */
	void setValue(Object instance, Object value);

}
