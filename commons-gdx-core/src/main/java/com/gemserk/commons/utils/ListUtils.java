package com.gemserk.commons.utils;

import java.util.List;

/**
 * Provides some useful methdos to be used with lists.
 */
public class ListUtils {

	public static <T> T nextElement(List<T> list, T element) {
		int index = list.indexOf(element);
		if (index == -1)
			throw new IllegalArgumentException("Can't get next element of an element not in the list");
		index = (index + 1) % list.size();
		return list.get(index);
	}
	
}
