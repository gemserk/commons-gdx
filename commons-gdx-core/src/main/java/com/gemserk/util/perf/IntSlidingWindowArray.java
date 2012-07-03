package com.gemserk.util.perf;

public class IntSlidingWindowArray {

	int[] array;
	int origin = 0;
	int size = 0;

	public IntSlidingWindowArray(int size) {
		array = new int[size];

	}

	public void add(int value) {
		int index = (origin + size) % array.length;
		array[index] = value;
		if(size>=array.length)
			origin++;
		else
			size++;
	}

	public int getWindowSize() {
		return array.length;
	}

	public int size() {
		return size;
	}

	public int get(int index) {
		if(index<0 || index >=size)
			throw new IndexOutOfBoundsException("Index: " + index + " - size: " + size);
		
		return array[(origin + index) % array.length];
	
	}
	
	public void clear(){
		origin = 0;
		size = 0;
	}

}
