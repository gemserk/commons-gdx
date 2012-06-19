package com.gemserk.commons.utils;

import java.util.ArrayList;

/**
 * Provides an API for a store of free objects and created objects of one kind, used to easily reuse them to avoid generating garbage collection.
 */
/**
 * @author acoppes
 * 
 * @param <T>
 */
public class Store<T> {

	ArrayList<T> free = new ArrayList<T>();

	StoreFactory<T> storeFactory;

	/**
	 * Creates a new Store specifying the StoreFactory to be used when creating new objects.
	 * 
	 * @param storeFactory
	 *            The StoreFactory to be used when creating new objects.
	 */
	public Store(StoreFactory<T> storeFactory) {
		this.storeFactory = storeFactory;
	}

	/**
	 * Returns an object from the Store, it reuses an object from the free collection if there is one or creates a new object otherwise.
	 */
	public T get() {
		if (free.isEmpty())
			return newObject();
		else
			return reuseObject();
	}

	protected T reuseObject() {
		T t = free.remove(free.size()-1);
		return t;
	}

	protected T newObject() {
		T t = storeFactory.createObject();
		return t;
	}

	/**
	 * Frees the object by returning it to the free objects collection.
	 * 
	 * @param t
	 */
	public void free(T t) {
		if (free.contains(t))
			return;
		free.add(t);
	}

	/**
	 * Returns the size of the created objects collection.
	 */
	public int size() {
		return free.size();
	}



	/**
	 * Creates the specified number of objects and adds them to the free collection in the store.
	 * 
	 * @param count
	 *            The number of objects to be created.
	 */
	public void preCreate(int count) {
		for (int i = 0; i < count; i++)
			free(storeFactory.createObject());
	}

}