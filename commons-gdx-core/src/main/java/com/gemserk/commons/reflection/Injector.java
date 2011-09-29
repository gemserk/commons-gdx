package com.gemserk.commons.reflection;

/**
 * Provides a very basic dependency injection by calling setter methods over the fields of an object.
 * 
 * @author acoppes
 */
public interface Injector {

	/**
	 * For each field of the object, configures its value using the registered values with the add() method. 
	 * 
	 * @param instance
	 *            The object to be injected.
	 */
	void injectMembers(Object instance);

}