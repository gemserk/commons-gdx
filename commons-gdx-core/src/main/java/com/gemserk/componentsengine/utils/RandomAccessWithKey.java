package com.gemserk.componentsengine.utils;

public interface RandomAccessWithKey<K,V> extends RandomAccess<V> {
	K getKey(int index);
}
