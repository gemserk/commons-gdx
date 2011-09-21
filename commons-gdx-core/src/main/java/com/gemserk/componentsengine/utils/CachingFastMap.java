/*
 * Copyright 2002-2004 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

package com.gemserk.componentsengine.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * A hash map using primitive ints as keys rather than objects.
 * @author Justin Couch
 * @author Alex Chaffee (alex@apache.org)
 * @author Stephen Colebourne
 * @author Nathan Sweet
 */
public class CachingFastMap<K, V> implements Map<K,V>, Iterable<CachingFastMap.Entry<K, V>> {
	Entry[] table;
	private final float loadFactor;
	private int size, mask, capacity, threshold;
	private ArrayList<Entry> freeEntries;

	/**
	 * Same as: FastMap(16, 0.75f);
	 */
	public CachingFastMap () {
		this(16, 0.75f);
	}

	/**
	 * Same as: FastMap(initialCapacity, 0.75f);
	 */
	public CachingFastMap (int initialCapacity) {
		this(initialCapacity, 0.75f);
	}

	public CachingFastMap (int initialCapacity, float loadFactor) {
		if (initialCapacity > 1 << 30) throw new IllegalArgumentException("initialCapacity is too large.");
		if (initialCapacity < 0) throw new IllegalArgumentException("initialCapacity must be greater than zero.");
		if (loadFactor <= 0) throw new IllegalArgumentException("initialCapacity must be greater than zero.");
		capacity = 1;
		while (capacity < initialCapacity)
			capacity <<= 1;
		this.loadFactor = loadFactor;
		this.threshold = (int)(capacity * loadFactor);
		this.table = new Entry[capacity];
		this.mask = capacity - 1;
		freeEntries = new ArrayList(capacity);
	}

	public void clearCache () {
		freeEntries.clear();
	}

	public void cache (int count) {
		for (int i = freeEntries.size(); i < count; i++)
			freeEntries.add(new Entry());
	}

	@Override
	public V put (K key, V value) {
		int hash = key.hashCode();
		Entry[] table = this.table;
		int index = hash & mask;
		// Check if key already exists.
		for (Entry e = table[index]; e != null; e = e.next) {
			if (!e.key.equals(key)) continue;
			Object oldValue = e.value;
			e.value = value;
			return (V)oldValue;
		}
		int freeEntriesSize = freeEntries.size();
		Entry entry = freeEntriesSize > 0 ? freeEntries.remove(freeEntriesSize - 1) : new Entry();
		entry.hash = hash;
		entry.key = key;
		entry.value = value;
		entry.next = table[index];
		table[index] = entry;
		if (size++ >= threshold) {
			// Rehash.
			int newCapacity = 2 * capacity;
			Entry[] newTable = new Entry[newCapacity];
			int newMask = newCapacity - 1;
			for (int i = 0; i < table.length; i++) {
				Entry e = table[i];
				if (e == null) continue;
				do {
					Entry next = e.next;
					index = e.hash & newMask;
					e.next = newTable[index];
					newTable[index] = e;
					e = next;
				} while (e != null);
			}
			this.table = newTable;
			capacity = newCapacity;
			threshold *= 2;
			mask = capacity - 1;
		}
		return null;
	}

	@Override
	public V get (Object key) {
		int index = key.hashCode() & mask;
		for (Entry e = table[index]; e != null; e = e.next)
			if (e.key.equals(key)) return (V)e.value;
		return null;
	}

	
	@Override
	public boolean containsValue (Object value) {
		Entry[] table = this.table;
		for (int i = table.length - 1; i >= 0; i--)
			for (Entry e = table[i]; e != null; e = e.next)
				if (e.value.equals(value)) return true;
		return false;
	}

	@Override
	public boolean containsKey (Object key) {
		int index = key.hashCode() & mask;
		for (Entry e = table[index]; e != null; e = e.next)
			if (e.key.equals(key)) return true;
		return false;
	}

	@Override
	public V remove (Object key) {
		int index = key.hashCode() & mask;
		Entry prev = table[index];
		Entry e = prev;
		while (e != null) {
			Entry next = e.next;
			if (e.key.equals(key)) {
				size--;
				if (prev == e)
					table[index] = next;
				else
					prev.next = next;
				freeEntries.add(e);
				return (V)e.value;
			}
			prev = e;
			e = next;
		}
		return null;
	}

	@Override
	public int size () {
		return size;
	}

	@Override
	public boolean isEmpty () {
		return size == 0;
	}

	
	@Override
	public void clear () {
		Entry[] table = this.table;
		for (int index = table.length - 1; index >= 0; index--) {
			for (Entry e = table[index]; e != null; e = e.next)
				freeEntries.add(e);
			table[index] = null;
		}
		size = 0;
	}

	@Override
	public EntryIterator iterator () {
		return new EntryIterator();
	}

	public class EntryIterator implements Iterator<Entry<K, V>> {
		private int nextIndex;
		private Entry<K, V> current;

		EntryIterator () {
			reset();
		}

		public void reset () {
			current = null;
			// Find first bucket.
			Entry[] table = CachingFastMap.this.table;
			int i;
			for (i = table.length - 1; i >= 0; i--)
				if (table[i] != null) break;
			nextIndex = i;
		}

		public boolean hasNext () {
			if (nextIndex >= 0) return true;
			Entry e = current;
			return e != null && e.next != null;
		}

		public Entry<K, V> next () {
			// Next entry in current bucket.
			Entry e = current;
			if (e != null) {
				e = e.next;
				if (e != null) {
					current = e;
					return e;
				}
			}
			// Use the bucket at nextIndex and find the next nextIndex.
			Entry[] table = CachingFastMap.this.table;
			int i = nextIndex;
			e = current = table[i];
			while (--i >= 0)
				if (table[i] != null) break;
			nextIndex = i;
			return e;
		}

		public void remove () {
			CachingFastMap.this.remove(current.key);
		}
	}

	static public class Entry<K, V> {
		int hash;
		K key;
		V value;
		Entry next;

		public K getKey () {
			return key;
		}

		public V getValue () {
			return value;
		}
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		throw new UnsupportedOperationException("Not implemented");
	}

	@Override
	public Set<K> keySet() {
		throw new UnsupportedOperationException("Not implemented");
	}

	@Override
	public Collection<V> values() {
		throw new UnsupportedOperationException("Not implemented");
	}

	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		throw new UnsupportedOperationException("Not implemented");
	}
}