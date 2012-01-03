package com.gemserk.commons.utils;

/**
 * Holds the logic of how to create an object, used by the Store<T>
 */
public interface StoreFactory<T> {

	/**
	 * Returns a new object of the corresponding type.
	 */
	T createObject();

}