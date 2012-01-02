package com.gemserk.commons.utils;

import java.util.ArrayList;

public class Store<T> {
	
	protected ArrayList<T> created = new ArrayList<T>();
	protected ArrayList<T> free = new ArrayList<T>();
	
	StoreFactory<T> storeFactory;
	
	public Store(StoreFactory<T> storeFactory) {
		this.storeFactory = storeFactory;
	}

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

	public void free(T t) {
		// entities keep being in the world with this.
		free.add(t);
		created.remove(t);
	}

	public int size() {
		return created.size();
	}
	
	public int getTotalSize() {
		return created.size() + free.size();
	}

	public T get(int index) {
		return created.get(index);
	}
	
	public void preCreate(int count) {
		for (int i = 0; i < count; i++)
			free(storeFactory.createObject());
	}

}