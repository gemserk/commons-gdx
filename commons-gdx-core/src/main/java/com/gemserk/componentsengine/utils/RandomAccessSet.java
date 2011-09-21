package com.gemserk.componentsengine.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class RandomAccessSet<T> implements Set<T>, RandomAccess<T> {

	ArrayList<T> items = new ArrayList<T>();
	CachingFastMapIntValue<T> positions = new CachingFastMapIntValue<T>();

	@Override
	public int size() {
		return items.size();
	}

	@Override
	public boolean isEmpty() {
		return items.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return positions.containsKey(o);
	}

	@Override
	public Iterator<T> iterator() {
		final Iterator<T> arrayListIterator = items.iterator();
		return new Iterator<T>() {

			@Override
			public boolean hasNext() {
				return arrayListIterator.hasNext();
			}

			@Override
			public T next() {
				return arrayListIterator.next();
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();

			}
		};
	}

	@Override
	public Object[] toArray() {
		return items.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return items.toArray(a);
	}

	@Override
	public boolean add(T e) {
		int position = positions.get(e);
		if (position == CachingFastMapIntValue.NOT_PRESENT_VALUE) {
			items.add(e);
			positions.put(e, items.size() - 1);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean remove(Object e) {
		int position = positions.get(e);
		if (position == CachingFastMapIntValue.NOT_PRESENT_VALUE)
			return false;

		int lastPosition = items.size() - 1;
		T lastItem = items.get(lastPosition);
		positions.remove(e);
		items.remove(lastPosition);
		if (position != lastPosition) {
			items.set(position, lastItem);
			positions.put(lastItem, position);
		}
		return true;

	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return items.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		boolean changed = false;
		for (T t : c) {
			changed |= this.add(t);
		}
		return changed;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		boolean changed = false;
		for (Object t : c) {
			changed |= this.remove(t);
		}
		return changed;
	}

	@Override
	public void clear() {
		items.clear();
		positions.clear();
	}

	public T get(int index) {
		return items.get(index);
	}

}
