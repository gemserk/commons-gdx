package com.gemserk.componentsengine.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.badlogic.gdx.utils.ObjectIntMap;

public class RandomAccessMap<K, V> implements Map<K, V>, RandomAccessWithKey<K, V> {

	ArrayList<V> items;
	ArrayList<K> keys;
	ObjectIntMap<Object> positions;
	private final int NOT_PRESENT = -1;

	public RandomAccessMap() {
		this(10);
	}

	public RandomAccessMap(int initialSize) {
		items = new ArrayList<V>(initialSize);
		keys = new ArrayList<K>(initialSize);
		positions = new ObjectIntMap<Object>((int) (1.5f * initialSize));
	}

	@Override
	public int size() {
		return items.size();
	}

	@Override
	public boolean isEmpty() {
		return items.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return positions.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return items.contains(value);
	}

	@Override
	public V get(Object key) {
		int position = positions.get(key, NOT_PRESENT);
		if (position == NOT_PRESENT)
			return null;

		return items.get(position);
	}

	@Override
	public V put(K key, V value) {
		int position = positions.get(key, NOT_PRESENT);
		if (position == NOT_PRESENT) {
			items.add(value);
			keys.add(key);
			positions.put(key, items.size() - 1);
			return null;
		}

		return items.set(position, value);
	}

	@Override
	public V remove(Object key) {
		int position = positions.get(key, NOT_PRESENT);
		if (position == NOT_PRESENT)
			return null;

		int lastPosition = items.size() - 1;

		positions.remove(key, NOT_PRESENT);

		V lastItem = items.remove(lastPosition);
		K lastKey = keys.remove(lastPosition);

		if (position != lastPosition) {
			V removedItem = items.get(position);
			items.set(position, lastItem);
			keys.set(position, lastKey);
			positions.put(lastKey, position);
			return removedItem;
		} else {
			return lastItem;
		}
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		for (java.util.Map.Entry<? extends K, ? extends V> entry : m.entrySet()) {
			put(entry.getKey(), entry.getValue());
		}

	}

	public V get(int index) {
		return items.get(index);
	}

	@Override
	public K getKey(int index) {
		return keys.get(index);
	}

	@Override
	public void clear() {
		items.clear();
		positions.clear();
		keys.clear();
	}

	@Override
	public Set<K> keySet() {
		return new HashSet<K>(keys);
	}

	@Override
	public Collection<V> values() {
		return new ArrayList<V>(items);
	}

	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		Set<java.util.Map.Entry<K, V>> result = new HashSet<Map.Entry<K, V>>();
		for (int i = 0; i < size(); i++) {
			result.add(new Entry(keys.get(i), items.get(i)));
		}
		return result;
	}

	class Entry implements Map.Entry<K, V> {

		K key;
		V value;

		public Entry(K key, V value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public K getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return value;
		}

		@Override
		public V setValue(V value) {
			throw new UnsupportedOperationException("not implemented");
		}

	}

}
