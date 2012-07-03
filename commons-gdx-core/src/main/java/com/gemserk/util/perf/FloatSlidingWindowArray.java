package com.gemserk.util.perf;

public class FloatSlidingWindowArray {

	float[] array;
	int origin = 0;
	int size = 0;

	public FloatSlidingWindowArray(int size) {
		array = new float[size];

	}

	public void add(float value) {
		int index = (origin + size) % array.length;
		array[index] = value;
		if (size >= array.length)
			origin = (origin + 1 % array.length);
		else
			size++;
	}

	public int getWindowSize() {
		return array.length;
	}

	public int size() {
		return size;
	}

	public float get(int index) {
		if (index < 0 || index >= size)
			throw new IndexOutOfBoundsException("Index: " + index + " - size: " + size);

		return array[(origin + index) % array.length];

	}

	public void clear() {
		origin = 0;
		size = 0;
	}

}
