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

	ArrayList<T> created = new ArrayList<T>();
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
		T t = free.get(0);
		free.remove(0);
		created.add(t);
		return t;
	}

	protected T newObject() {
		T t = storeFactory.createObject();
		created.add(t);
		return t;
	}

	/**
	 * Frees the object by returning it to the free objects collection.
	 * 
	 * @param t
	 */
	public void free(T t) {
		free.add(t);
		created.remove(t);
	}

	/**
	 * Returns the size of the created objects collection.
	 */
	public int size() {
		return created.size();
	}

	/**
	 * Returns the total size of objects between free and created objects.
	 */
	public int getTotalSize() {
		return created.size() + free.size();
	}

	/**
	 * Returns an object of the created collection.
	 * 
	 * @param index
	 *            The index of the object to be returned.
	 */
	public T get(int index) {
		return created.get(index);
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