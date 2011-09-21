package com.gemserk.componentsengine.utils;

public interface RandomAccess<T> extends java.util.RandomAccess {
	
	int size();

	T get(int index);
	
}
