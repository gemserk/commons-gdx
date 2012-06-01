package com.gemserk.commons.utils;

/**
 * Provides some useful methods to be used with arrays.
 */
public class ArrayUtils {

	/**
	 * Swaps the item in the i position with the item in the j position of the array.
	 * 
	 * @param array
	 *            The array with the elements.
	 * @param i
	 *            The index of the first element.
	 * @param j
	 *            The index of the second element.
	 */
	public static <T> void swap(T[] array, int i, int j) {
		if (i < 0 || i >= array.length)
			throw new ArrayIndexOutOfBoundsException(i);
		if (j < 0 || j >= array.length)
			throw new ArrayIndexOutOfBoundsException(j);
		T tmp = array[i];
		array[i] = array[j];
		array[j] = tmp;
	}

}
