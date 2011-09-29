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
	
	/**
	 * Instantiates a new instance of the specified class, configures it and internally caches it.
	 */
	<T> T getInstance(Class<? extends T> clazz);

	/**
	 * Registers a new object to be injected in the fields named with specified name.
	 * 
	 * @param name
	 *            The name of the field to inject the object to.
	 * @param instance
	 *            The instance to be injected.
	 */
	void configureField(String name, Object instance);

}